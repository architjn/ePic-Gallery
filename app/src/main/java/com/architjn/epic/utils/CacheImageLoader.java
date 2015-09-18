package com.architjn.epic.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by architjn on 18/09/15.
 */
public class CacheImageLoader {

    private Integer[] randomNumbers;
    private MySQLCacheHelper mySQLCacheHelper;
    private int pos = 0;

    public CacheImageLoader(Context context, int totalImages) {
        randomNumbers = randomNumbers(totalImages);
        mySQLCacheHelper = new MySQLCacheHelper(context);
    }

    public String getImage(String originalUri, int width, int height) {
        String newUrl = mySQLCacheHelper.checkIfAlready(originalUri);
        if (newUrl != null) {
            if ((new File(newUrl)).exists())
                return newUrl;
            else
                mySQLCacheHelper.removeAImg(originalUri);
        }
        Bitmap bitmap = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(originalUri),
                width, height);
        StringBuilder fileName = new StringBuilder();
        fileName.append("cache-img-");
        Calendar c = Calendar.getInstance();
        fileName.append(c.get(Calendar.DATE) + "-");
        fileName.append(c.get(Calendar.MONTH) + "-");
        fileName.append(c.get(Calendar.YEAR) + "-");
        fileName.append(c.get(Calendar.HOUR) + "-");
        fileName.append(c.get(Calendar.MINUTE) + "-");
        fileName.append(c.get(Calendar.SECOND) + "-");
        fileName.append(randomNumbers[pos++]);
        fileName.append(".png");
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        String filePath = sdCardDirectory + "/ePic/cache/";
        (new File(filePath)).mkdirs();
        File image = new File(filePath, fileName.toString());
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);

            outStream.flush();
            outStream.close();
            mySQLCacheHelper.addCacheImage(originalUri, image.getPath());
            return image.getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer[] randomNumbers(int range) {
        Integer[] arr = new Integer[range];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Collections.shuffle(Arrays.asList(arr));
        return arr;
    }

}
