package com.tangguna.imageswitcher.library.bean;

/**
 * 图片描述
 */
public class PhonePhoto {
    private String description; // 照片描述
    private String imgUrl; // 照片地址
    private String uploadId; // 上传成功后的id
    private String compressImgUrl; // 压缩后照片地址
    private int index; // 1 表示添加其他照片按钮  其他为显示图片
    private boolean isCancelUpload; // 手动取消上传标识
    private boolean isCompress; // 是否已经压缩
    private int uploadFailNum; // 上传失败计数标识

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getCompressImgUrl() {
        return compressImgUrl;
    }

    public void setCompressImgUrl(String compressImgUrl) {
        this.compressImgUrl = compressImgUrl;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCancelUpload() {
        return isCancelUpload;
    }

    public void setCancelUpload(boolean cancelUpload) {
        isCancelUpload = cancelUpload;
    }

    public boolean isCompress() {
        return isCompress;
    }

    public void setCompress(boolean compress) {
        isCompress = compress;
    }

    public int getUploadFailNum() {
        return uploadFailNum;
    }

    public void setUploadFailNum(int uploadFailNum) {
        this.uploadFailNum = uploadFailNum;
    }
}
