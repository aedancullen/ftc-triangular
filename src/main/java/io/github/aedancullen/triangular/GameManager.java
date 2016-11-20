package io.github.aedancullen.triangular;

import android.content.Context;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.opencv.highgui.Highgui.CV_LOAD_IMAGE_UNCHANGED;
import static org.opencv.highgui.Highgui.imdecode;

public class GameManager {

    Properties gameProperties;
    List<Mat> images;
    List<String> names;


    public GameManager(String gameName, Context appContext) throws IOException {
        gameProperties.load(appContext.getAssets().open(gameName + ".properties"));
        String[] imageFiles = gameProperties.getProperty("images").split(",");
        names = new ArrayList<String>(Arrays.asList(gameProperties.getProperty("names").split(",")));
        for (String file : imageFiles) {
            InputStream stream = appContext.getAssets().open(file);
            int nRead;
            byte[] data = new byte[16 * 1024];
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            byte[] bytes = buffer.toByteArray();
            Mat mat = imdecode(new MatOfByte(bytes), CV_LOAD_IMAGE_UNCHANGED);
            images.add(mat);
        }
    }

}