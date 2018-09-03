package com.tangguna.imageswitcher.library.callback;

import android.view.View;


/**
 * 打开选择拍照或者图片界面接口
 */
public interface PopupWindowCallBack {
    /**
     * 展示界面
     * @param view
     *         activity根界面id
     * @param layout
     *        布局页面id
     */
    PopupWindowCallBack showPop(View view, int layout);

    /**
     * 调用选择
     * @param onItemClickListener
     */
    PopupWindowCallBack setOnItemClickListener(OnItemClickListener onItemClickListener);
}
