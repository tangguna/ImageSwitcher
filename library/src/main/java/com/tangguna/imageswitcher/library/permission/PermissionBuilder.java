package com.tangguna.imageswitcher.library.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.tangguna.imageswitcher.library.permission.PermissionManager.getActivity;

public class PermissionBuilder {

    private String[] mPermissions;
    private int mRequestCode;
    private Object object;

    private static IPermissionsCallback iPermissionsCallback;

    private PermissionBuilder(Object object){
        this.object = object;
    }

    public static PermissionBuilder with(Activity activity){
        return new PermissionBuilder(activity);
    }

    public static PermissionBuilder with(Fragment fragment){
        return new PermissionBuilder(fragment);
    }

    public static PermissionBuilder with(Context context){
        return new PermissionBuilder(context);
    }

    public PermissionBuilder permissions(String ... mPermission){
        this.mPermissions = mPermission;
        return this;
    }

    public PermissionBuilder addRequestCode(int mRequestCode){
        this.mRequestCode = mRequestCode;
        return this;
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request(){
        iPermissionsCallback = null;
        requestPermissions(object, mRequestCode, mPermissions);
    }

    @TargetApi(value = Build.VERSION_CODES.M)
    public void request(IPermissionsCallback callback){
        if (callback != null){
            iPermissionsCallback = callback;
        }
    }


    public static void needPermission(Activity activity,int requestCode,String[] permissions){
        iPermissionsCallback = null;
        requestPermissions(activity, requestCode, permissions);
    }

    public static void needPermission(Fragment fragment, int requestCode, String[] permissions){
        iPermissionsCallback = null;
        requestPermissions(fragment, requestCode, permissions);
    }

    public static void needPermission(Activity activity, int requestCode, String[] permissions
            ,IPermissionsCallback callback) {
        if (callback != null) {
            iPermissionsCallback = callback;
        }
        requestPermissions(activity, requestCode, permissions);
    }

    public static void needPermission(Fragment fragment, int requestCode, String[] permissions
            ,IPermissionsCallback callback) {
        if (callback != null) {
            iPermissionsCallback = callback;
        }
        requestPermissions(fragment, requestCode, permissions);
    }

    public static void needPermission(Activity activity, int requestCode, String permission){
        iPermissionsCallback = null;
        needPermission(activity, requestCode, new String[] { permission });
    }

    public static void needPermission(Fragment fragment, int requestCode, String permission){
        iPermissionsCallback = null;
        needPermission(fragment, requestCode, new String[] { permission });
    }

    public static void needPermission(Activity activity, int requestCode, String permission,IPermissionsCallback callback){
        if (callback != null) {
            iPermissionsCallback = callback;
        }
        needPermission(activity, requestCode, new String[] { permission });
    }

    public static void needPermission(Fragment fragment, int requestCode, String permission,IPermissionsCallback callback){
        if (callback != null) {
            iPermissionsCallback = callback;
        }
        needPermission(fragment, requestCode, new String[] { permission });
    }


    /**
     * 请求权限
     * @param object
     * @param requestCode
     * @param permissions
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    private static void requestPermissions(Object object, int requestCode, String[] permissions){
        if(!PermissionManager.isMoreThanSix()) {
            if (iPermissionsCallback != null) {
                iPermissionsCallback.onPermissionSuccess(requestCode,"授权成功");
            }else {
                doExecuteSuccess(object, requestCode);
            }
            return;
        }
        List<String> deniedPermissions = PermissionManager.findDeniedPermissions(getActivity(object), permissions);

        /**
         * 先检查是否有没有授予的权限，有的话请求，没有的话就直接执行权限授予成功的接口/注解方法
         */
        if(deniedPermissions.size() > 0){
            if(object instanceof Activity){
                ((Activity)object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else if(object instanceof Fragment){
                ((Fragment)object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
            } else {
                throw new IllegalArgumentException(object.getClass().getName() + " is not supported");
            }
        } else {
            if (iPermissionsCallback != null) {
                iPermissionsCallback.onPermissionSuccess(requestCode,"授权成功");
            }else {
                doExecuteSuccess(object, requestCode);
            }
        }
    }

    private static void doExecuteSuccess(Object activity, int requestCode) {
        Method executeMethod = PermissionManager.findMethodWithRequestCode(activity.getClass(),
                PermissionSuccess.class, requestCode);

        executeMethod(activity, executeMethod);
    }

    private static void doExecuteFail(Object activity, int requestCode) {
        Method executeMethod = PermissionManager.findMethodWithRequestCode(activity.getClass(),
                PermissionFailure.class, requestCode);

        executeMethod(activity, executeMethod);
    }

    private static void executeMethod(Object activity, Method executeMethod) {
        if(executeMethod != null){
            try {
                if(!executeMethod.isAccessible()) executeMethod.setAccessible(true);
                executeMethod.invoke(activity, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions,
                                                  int[] grantResults) {
        requestResult(fragment, requestCode, permissions, grantResults);
    }

    /**
     * 有回调接口的话(即回调接口不为空的话)先执行回调接口的方法，若为空，则寻找响应的注解方法。
     * @param obj
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    private static void requestResult(Object obj, int requestCode, String[] permissions,
                                      int[] grantResults){
        List<String> deniedPermissions = new ArrayList<>();
        for(int i=0; i<grantResults.length; i++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                deniedPermissions.add(permissions[i]);
            }
        }

        if(deniedPermissions.size() > 0){
            if(iPermissionsCallback!=null){
                iPermissionsCallback.onPermissionFailure(requestCode,"授权失败");
            }else {
                doExecuteFail(obj, requestCode);
            }
        } else {
            if(iPermissionsCallback!=null){
                iPermissionsCallback.onPermissionSuccess(requestCode,"授权成功");
            }else {
                doExecuteSuccess(obj, requestCode);
            }
        }
    }


}
