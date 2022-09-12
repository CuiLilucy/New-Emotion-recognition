package com.example.myapp5;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.pytorch.IValue;

import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;;
import org.pytorch.MemoryFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity1 extends AppCompatActivity {

    private SQLiteWriteActivity ImagePath;
    private int maxScoreIdx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bitmap bitmap = null;
        Module module = null;
        try {
            // creating bitmap from packaged into app android asset 'image.jpg',
            // app/src/main/assets/image.jpg
            ImagePath = new SQLiteWriteActivity();
            String filename = ImagePath.getChooseImagePath();
            bitmap = BitmapFactory.decodeStream(getAssets().open(filename));
            bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
            // loading serialized torchscript module from packaged into app android asset model.pt,
            // app/src/model/assets/model.pt
            module = Module.load(assetFilePath(this, "Test_model_third.pt"));
        } catch (IOException e) {
            Log.e("PytorchHelloWorld", "Error reading assets", e);
            finish();
        }

        // showing image on UI
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

        // preparing input tensor
        final Tensor inputTensor = TensorImageUtils.bitmapToFloat32Tensor(bitmap,
                TensorImageUtils.TORCHVISION_NORM_MEAN_RGB, TensorImageUtils.TORCHVISION_NORM_STD_RGB, MemoryFormat.CHANNELS_LAST);

        // running the model
        final Tensor outputTensor = module.forward(IValue.from(inputTensor)).toTensor();
        System.out.print(outputTensor + "\n");
        // getting tensor content as java array of floats
        final float[] scores = outputTensor.getDataAsFloatArray();
        for (int i = 0; i < scores.length; i++) {
            System.out.print(i + ":" + scores[i] + " \n");
        }
        // searching for the index with maximum score
        float maxScore = -Float.MAX_VALUE;
        maxScoreIdx = -1;
        for (int i = 0; i < scores.length; i++) {
            if (scores[i] > maxScore) {
                maxScore = scores[i];
                maxScoreIdx = i;
            }
        }
        System.out.println(maxScoreIdx);
        String className = ImageNetClasses.IMAGENET_CLASSES[maxScoreIdx];
        // showing className on UI
        TextView textView = findViewById(R.id.text);
        textView.setText(className);

    }

    public int runImageanalysis() {
        return maxScoreIdx;
    }

    /**
     * Copies specified asset to the file in /files app directory and returns this file absolute path.
     *
     * @return absolute file path
     */
    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
}
