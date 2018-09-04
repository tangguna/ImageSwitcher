package com.tangguna.imageswitcher.library.permission;

/**
 * 权限请求结果回调 适配到Android O
 */
public interface IPermissionsCallback {

    /**
     * 请求成功
     */
    void onPermissionSuccess(int requsetCode,String success);

    /**
     * 请求失败
     */
    void onPermissionFailure(int requsetCode,String failure);

}
