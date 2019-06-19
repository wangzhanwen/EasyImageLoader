package com.wzw.easyimageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wzw.easyimageloader.config.DisplayConfig;
import com.wzw.easyimageloader.config.ImageLoaderConfig;
import com.wzw.easyimageloader.request.BitmapRequest;
import com.wzw.easyimageloader.request.RequestQueue;

public class SimpleImageLoader {

    // 配置
    private ImageLoaderConfig mImageLoaderConfig;

    // 请求对列
    private RequestQueue mRequestQueue;

    private static volatile SimpleImageLoader mInstance;

    private SimpleImageLoader() {

    }

    private SimpleImageLoader(ImageLoaderConfig imageLoaderConfig) {
        this.mImageLoaderConfig = imageLoaderConfig;
        this.mRequestQueue = new RequestQueue(mImageLoaderConfig.getThreadCount());
        // 开启请求对列
        this.mRequestQueue.start();

    }

    /**
     * 单例获取实例
     *
     * @param imageLoaderConfig
     * @return
     */
    public static SimpleImageLoader getInstance(ImageLoaderConfig imageLoaderConfig) {
        if (mInstance == null) {
            synchronized (SimpleImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new SimpleImageLoader(imageLoaderConfig);
                }
            }
        }

        return mInstance;
    }

    /**
     * 第二次获取单例
     *
     * @return
     */
    public static SimpleImageLoader getInstance() {
        if (mInstance == null) {
            throw new UnsupportedOperationException("SimpleImageLoader 没有初始化");
        }

        return mInstance;
    }

    /**
     *
     * @param imageView
     * @param url: http file 开头
     */
    public void displayImage(ImageView imageView, String url){
        displayImage(imageView, url, null, null);
    }

    public void displayImage(ImageView imageView, String url, DisplayConfig displayConfig, ImageListener imageListener){
        // 实例化一个请求
        BitmapRequest bitmapRequest = new BitmapRequest(imageView, url, imageListener, displayConfig);
        // 添加到队列里面
        mRequestQueue.addRequest(bitmapRequest);

    }

    public ImageLoaderConfig getImageLoaderConfig() {
        return mImageLoaderConfig;
    }

    /**
     * 图片加载监听
     */
    public static interface ImageListener{

        /**
         * 加载完成
         *
         * @param imageView
         * @param bitmap
         * @param url
         */
        void onComplete(ImageView imageView, Bitmap bitmap, String url);

    }
}
