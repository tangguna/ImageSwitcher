package com.tangguna.imageswitcher.library.set;

import android.content.Context;

import com.tangguna.imageswitcher.library.view.PhotoPopupWindowView;

public class PopupWindowBuilder {

    private static PhotoPopupWindowView photoPopupWindowView;

    /**
     * 返回PhotoPopupWindowView实例
     * @param context
     * @return
     */
    public static PhotoPopupWindowView getInstance(Context context){
        if (photoPopupWindowView == null){
            photoPopupWindowView = new PhotoPopupWindowView(context);
        }
        return photoPopupWindowView;
    }

}
