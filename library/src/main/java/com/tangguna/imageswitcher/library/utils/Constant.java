package com.tangguna.imageswitcher.library.utils;

import android.os.Environment;

public class Constant {

    /**
     * 文件根路径
     */
    public static final String sd_Path = Environment.getExternalStorageDirectory().getPath();

    /**
     * 文件缓存目录
     */
    public static final String ImagesSwitcher = sd_Path + "/imageSwitcher/";
    /**
     * 拍照缓存文件夹
     */
    public static final String IMG_DIR = ImagesSwitcher + "photo";
    /**
     * 图片压缩 缓存文件夹
     */
    public static final String COM_IMG = IMG_DIR + "/img";
}
