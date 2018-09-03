package com.tangguna.imageswitcher.library.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.tangguna.imageswitcher.library.callback.DataCallback;
import com.tangguna.imageswitcher.library.entry.Image;

import java.util.ArrayList;

public class ImageModel {

    /**
     * 从SD卡加载图片
     * @param context
     * @param callback
     */
    public static void loadImageForSDCard(final Context context, final DataCallback callback){
        //开启线程扫描图片，执行耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = context.getContentResolver();

                Cursor cursor = contentResolver.query(mImageUri, new String[]{
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED,
                        MediaStore.Images.Media._ID},null,null,null);
                ArrayList<Image> images = new ArrayList<>();

                //读取扫描到图片
                //读取扫描到的图片
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        // 获取图片的路径
                        String path = cursor.getString(
                                cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取图片名称
                        String name = cursor.getString(
                                cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //获取图片时间
                        long time = cursor.getLong(
                                cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        if (!".downloading".equals(path)) { //过滤未下载完成的文件
                            images.add(new Image(path, time, name));
                        }
                    }
                    cursor.close();
                }
            }
        }).start();
    }
}
