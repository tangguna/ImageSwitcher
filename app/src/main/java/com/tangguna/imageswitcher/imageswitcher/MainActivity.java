package com.tangguna.imageswitcher.imageswitcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tangguna.imageswitcher.library.bean.PhonePhoto;
import com.tangguna.imageswitcher.library.callback.OnItemClickListener;
import com.tangguna.imageswitcher.library.compress.ImageCompress;
import com.tangguna.imageswitcher.library.set.PopupWindowBuilder;
import com.tangguna.imageswitcher.library.utils.Constant;
import com.tangguna.imageswitcher.library.utils.FileUtil;
import com.tangguna.imageswitcher.library.utils.ImageSelectorUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //图片缓存路径
    private static String picPath;
    private static String fileName;
    //拍照标识
    private final int TAKE_PHOTO = 100;
    //相册标识
    private final int PHOTO = 101;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show);
        imageView = (ImageView) findViewById(R.id.img);

       imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose();
            }
        });

       findViewById(R.id.yasuo).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               uploadImg(picPath);
           }
       });
    }

    /**
     * 压缩上传
     * @param imgUrl
     */
    private void uploadImg(String imgUrl) {
        //图片压缩
        String temp = Constant.COM_IMG;
        if (TextUtils.isEmpty(imgUrl)) {

            return;
        }
        File tempFile = new File(imgUrl);
        if (!tempFile.exists()) {
            return;
        }
        //  图片压缩后的路径
        String compressImagePath = ImageCompress.compressImage(MainActivity.this, imgUrl, temp);
        //构建要上传的文件
        /**
         * //对文件进行操作，上传
         * //删除缓存文件夹  FileUtil.recursionDeleteFile(Constant.IMG_DIR);
         */
        File file = new File(compressImagePath);

    }


    private void choose() {
        long millis = System.currentTimeMillis();
        //拍照图片名称
        fileName = String.valueOf(millis) + "_temp.png";
        // 拍照图片路径 此处可以自定义路径
        picPath = Constant.IMG_DIR + fileName;
        //弹出面板
        PopupWindowBuilder.getInstance(this).showPop(findViewById(R.id.main),R.layout.camera_pop_menu).setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void openCamera() {
                FileUtil.startCamera(MainActivity.this, TAKE_PHOTO, Constant.IMG_DIR, fileName);
            }

            @Override
            public void openPhoto() {
                FileUtil.openPhoto(MainActivity.this, PHOTO);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 拍照返回处理
            if (requestCode == TAKE_PHOTO) {
                try {
                    Glide.with(getApplicationContext()).load(picPath).centerCrop().into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 选中系统相册返回处理
            } else if (requestCode == PHOTO) {
                picPath = FileUtil.getPhotoUrl(MainActivity.this, data);
                Glide.with(getApplicationContext()).load(picPath).centerCrop().into(imageView);
            }
        }
    }
}
