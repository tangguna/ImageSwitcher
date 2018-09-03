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
#### 调用选择拍照的popupWindow
         //弹出面板
        PopupWindowBuilder.getInstance(this).showPop(findViewById(R.id.main),R.layout.camera_pop_menu).setOnItemClickListener(new    OnItemClickListener() {
            @Override
            public void openCamera() {
                FileUtil.startCamera(MainActivity.this, TAKE_PHOTO, Constant.IMG_DIR, fileName);
            }

            @Override
            public void openPhoto() {
                FileUtil.openPhoto(MainActivity.this, PHOTO);
            }
        });
