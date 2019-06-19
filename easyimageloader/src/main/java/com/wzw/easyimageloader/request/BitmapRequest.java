package com.wzw.easyimageloader.request;

import android.widget.ImageView;

import com.wzw.easyimageloader.config.DisplayConfig;
import com.wzw.easyimageloader.loader.SimpleImageLoader;
import com.wzw.easyimageloader.policy.LoadPolicy;
import com.wzw.easyimageloader.utils.MD5Utils;

import java.lang.ref.SoftReference;


/**
 *
 */
public class BitmapRequest implements Comparable<BitmapRequest> {

    // 持有ImageView软引用
    private SoftReference<ImageView> imageViewSoftReference;

    // 图片路径
    private String imageUri;
    // MD5图片路径
    private String imageUriMD5;
    // 加载完成监听
    public SimpleImageLoader.ImageListener imageListener;

    // 加载策略
    public LoadPolicy mLoadPolicy = SimpleImageLoader.getInstance().getImageLoaderConfig()
            .getLoadPolicy();

    // 编号
    private int serialNo;

    private DisplayConfig mDisplayConfig;

    public BitmapRequest(ImageView imageView,
                         String imageUri,
                         SimpleImageLoader.ImageListener imageListener,
                         DisplayConfig displayConfig) {

        this.imageViewSoftReference = new SoftReference<ImageView>(imageView);
        this.imageUri = imageUri;
        this.imageUriMD5 = MD5Utils.toMD5(imageUri);
        // 设置可见Image的tag，要下载的图片路径
        imageView.setTag(imageUri);
        this.imageListener = imageListener;
        if (displayConfig != null) {
            this.mDisplayConfig = displayConfig;
        }
    }



    public int getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public ImageView getImageView() {

        if (imageViewSoftReference == null) {
            return null;
        }
        return imageViewSoftReference.get();
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getImageUriMD5() {
        return imageUriMD5;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mLoadPolicy == null) ? 0 : mLoadPolicy.hashCode());
        result = prime * result + serialNo;
        return result;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BitmapRequest other = (BitmapRequest) obj;
        if (mLoadPolicy == null) {
            if (other.mLoadPolicy != null)
                return false;
        } else if (!mLoadPolicy.equals(other.mLoadPolicy))
            return false;
        if (serialNo != other.serialNo)
            return false;
        return true;
    }

    @Override
    public int compareTo(BitmapRequest o) {
        return mLoadPolicy.compare(o, this);
    }
}
