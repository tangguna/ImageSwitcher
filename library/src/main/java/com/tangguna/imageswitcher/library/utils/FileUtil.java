package com.tangguna.imageswitcher.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * 描述：文件工具类
 */

public class FileUtil {


    /**
     * 拍照
     *
     * @param activity
     * @param requestCode 请求码
     * @param dir         目录名称
     * @param fileName    文件名称
     */
    public static void startCamera(Activity activity, int requestCode, String dir, String fileName) {

        File fileDir = new File(dir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String picPath = dir + fileName;
        File file = new File(picPath);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = null;
        // 适配7.0获取文件uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, "com.cqcyhc.he_recycle_android.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开系统相册
     *
     * @param activity
     * @param requestCode 请求码
     */
    public static void openPhoto(Activity activity, int requestCode) {
        //在这里跳转到手机系统相册里面
        try {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
        }
    }

    /**
     * 获取相册图片路径
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPhotoUrl(Context context, Intent data) {
        try {
            Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 递归删除文件和文件夹
     *
     * @param filePath 要删除的根目录路径
     */
    public static void recursionDeleteFile(String filePath) {
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f.getPath());
            }
            file.delete();
        }
    }

}
