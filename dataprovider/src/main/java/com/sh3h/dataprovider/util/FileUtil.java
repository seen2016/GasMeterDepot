package com.sh3h.dataprovider.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class FileUtil {
    /**
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }

                for (File f : childFile) {
                    deleteFile(f);
                }

                file.delete();
            }
        }
    }

    public static Bitmap readBitmap(String path){
        File file = new File(path);
        Bitmap bitmap = null;
        if(file.exists()){
            bitmap = BitmapFactory.decodeFile(path);
        }
        return bitmap;
    }
}
