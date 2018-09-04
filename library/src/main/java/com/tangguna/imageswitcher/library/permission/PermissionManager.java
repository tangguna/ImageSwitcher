package com.tangguna.imageswitcher.library.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限管理类
 */
public class PermissionManager {

    private PermissionManager(){

    }

    /**
     * 判断版本是否超过Android6.0
     * @return
     */
    public static boolean isMoreThanSix(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 从申请的权限中查找没有授权的权限
     * @param activity
     * @param permissions
     * @return
     */
    @TargetApi(value = Build.VERSION_CODES.M)
    public static List<String> findDeniedPermissions(Activity activity,String... permissions){
        List<String> denyPermisssions = new ArrayList<>();
        for (String value : permissions){
            if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED){
                denyPermisssions.add(value);
            }
        }
        return denyPermisssions;
    }

    /**
     * 寻找相应注解方法
     * @param clazz
     *         寻找的那个类
     * @param clazz1
     *         响应的注解的标记
     * @return
     */
    public static List<Method> findAnnotationMethonds(Class clazz, Class<? extends Annotation> clazz1){
        List<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()){
            if (method.isAnnotationPresent(clazz1)){
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * 查找授权失败的权限
     * @param clazz
     * @param permissionFailClass
     * @param requestCode
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findMethodPermissionFailWithRequestCode(Class clazz, Class<A> permissionFailClass, int requestCode) {
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(permissionFailClass)){
                if(requestCode == method.getAnnotation(PermissionFailure.class).requestCode()){
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 查找相对应的注解方法并且requestCode与需要的一致
     * @param method
     * @param clazz
     * @param requestCode
     * @return
     */
    public static boolean isEqualRequestCodeFromAnntation(Method method,Class clazz,int requestCode){
        if (clazz.equals(PermissionFailure.class)){
            return requestCode == method.getAnnotation(PermissionFailure.class).requestCode();
        }else if (clazz.equals(PermissionSuccess.class)){
            return requestCode == method.getAnnotation(PermissionSuccess.class).requestCode();
        }else {
            return false;
        }
    }

    public static <A extends Annotation> Method findMethodWithRequestCode(Class clazz,Class<A> annotation,int requestCode){
        for (Method method : clazz.getDeclaredMethods()){
            if (isEqualRequestCodeFromAnntation(method,annotation,requestCode)){
                return method;
            }
        }
        return null;
    }

    public static <A extends Annotation> Method findMethodPermissionSuccessWithRequestCode(Class clazz,
                                                                                           Class<A> permissionFailClass, int requestCode) {
        for(Method method : clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(permissionFailClass)){
                if(requestCode == method.getAnnotation(PermissionSuccess.class).requestCode()){
                    return method;
                }
            }
        }
        return null;
    }

    public static Activity getActivity(Object object){
        if(object instanceof Fragment){
            return ((Fragment)object).getActivity();
        } else if(object instanceof Activity){
            return (Activity) object;
        }
        return null;
    }


}
