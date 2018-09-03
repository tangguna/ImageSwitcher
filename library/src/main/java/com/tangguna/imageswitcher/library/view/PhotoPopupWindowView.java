package com.tangguna.imageswitcher.library.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tangguna.imageswitcher.library.R;
import com.tangguna.imageswitcher.library.callback.OnItemClickListener;
import com.tangguna.imageswitcher.library.callback.PopupWindowCallBack;
import com.tangguna.imageswitcher.library.utils.CommonUtils;

/**
 * 弹出选择框
 */
public class PhotoPopupWindowView  implements PopupWindowCallBack{
    private Context context;
    private PopupWindow popupWindow;

    public PhotoPopupWindowView(Context context) {
        this.context = context;
    }

    /**
     * 显示popupWindow
     *
     * @param view
     */
    @Override
    public PopupWindowCallBack showPop(View view,int layout) {

        View popView = LayoutInflater.from(context).inflate(
                R.layout.camera_pop_menu, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView btnCamera = popView.findViewById(R.id.btn_camera_pop_camera);
        TextView btnAlbum = popView.findViewById(R.id.btn_camera_pop_album);
        TextView btnCancel = popView.findViewById(R.id.btn_camera_pop_cancel);

        // 设置popupWindow背景半透明
        CommonUtils.setBackgroundAlpha((Activity) context, 0.5f);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // popupWindow隐藏时恢复屏幕正常透明度
                CommonUtils.setBackgroundAlpha((Activity) context, 1.0f);
            }
        });

        // 取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });

        // 拍照
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
                if (onItemClickListener != null) {
                    onItemClickListener.openCamera();
                }
            }
        });

        // 相册
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
                if (onItemClickListener != null) {
                    onItemClickListener.openPhoto();
                }
            }
        });
        return this;
    }

    /**
     * 消失popupWindow
     */
    private void dismissPop() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    private OnItemClickListener onItemClickListener;

    @Override
    public PopupWindowCallBack setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

}
