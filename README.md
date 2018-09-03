# ImageSwitcher
Android拍照或者选择本地手机图片上传服务器
###功能：
#### 可以调用系统摄像头拍照或者选择手机本地图片，并加载显示
#### 选择单个图片上传服务器
### 配置
#### 在project的build.gradle中添加
     allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
#### 添加依赖
      implementation 'com.github.tangguna:ImageSwitcher:1.0.1'
### 选择单个图片上传
#### 调用选择拍照的popupWindow Android6.0注意添加相应权限
         //弹出面板
        PopupWindowBuilder.getInstance(this).showPop(findViewById(R.id.main),R.layout.camera_pop_menu).setOnItemClickListener(new    OnItemClickListener() {
            @Override
            public void openCamera() {
	        //打开摄像机
                FileUtil.startCamera(MainActivity.this, TAKE_PHOTO, Constant.IMG_DIR, fileName);
            }

            @Override
            public void openPhoto() {
	        //打开图库
                FileUtil.openPhoto(MainActivity.this, PHOTO);
            }
        });
![图片加载失败](https://github.com/tangguna/ImageSwitcher/blob/master/img/pic_popupwindow.jpg)
其中showPop(view,id)为显示Popup界面，内部参数分别为所依赖的Activity根页面与popuwindow布局文件。此布局文件亦可自定义样式，但是需要设置拍照，相册，取消三个按钮ID分别为：btn_camera_pop_camera，btn_camera_pop_album，btn_camera_pop_cancel。或者不使用此方法，自己从新编写也可。
 
