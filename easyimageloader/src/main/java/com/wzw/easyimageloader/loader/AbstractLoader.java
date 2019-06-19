package com.wzw.easyimageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wzw.easyimageloader.cache.BitmapCache;
import com.wzw.easyimageloader.config.DisplayConfig;
import com.wzw.easyimageloader.request.BitmapRequest;

public abstract class AbstractLoader implements Loader {

    // 拿到用户自定义配置的缓存策略
    private BitmapCache mBitmapCache = SimpleImageLoader.getInstance()
            .getImageLoaderConfig()
            .getBitmapCache();

    // 拿到显示配置
    private DisplayConfig mDisplayConfig = SimpleImageLoader.getInstance()
            .getImageLoaderConfig()
            .getDisplayConfig();

    @Override
    public void loadImage(BitmapRequest bitmapRequest) {

        // 从缓存取到Bitmap
        Bitmap bitmap = mBitmapCache.get(bitmapRequest);
        if (bitmap == null) {
            showLoadingImage(bitmapRequest);
            // 开始真正加载图片
            bitmap = onLoad(bitmapRequest);
            // 缓存图片
            cacheBitmap(bitmapRequest, bitmap);
        }


        //显示
        deliveryToUIThread(bitmapRequest, bitmap);

    }


    /**
     * 交给主线程显示
     *
     * @param request
     * @param bitmap
     */
    protected void deliveryToUIThread(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, bitmap);
            }

        });
    }


    private void updateImageView(final BitmapRequest request, final Bitmap bitmap) {
        ImageView imageView = request.getImageView();
        //加载正常 防止错位
        if (bitmap != null && imageView.getTag().equals(request.getImageUri())) {
            imageView.setImageBitmap(bitmap);
        }
        //有可能加载失败
        if (bitmap == null && hasLoadFailPlaceHolder()) {
            imageView.setImageResource(mDisplayConfig.loadFaildImag);
        }
        //监听
        //回调
        if (request.imageListener != null) {
            request.imageListener.onComplete(imageView, bitmap, request.getImageUri());
        }
    }


    /**
     * 缓存图片
     *
     * @param request
     * @param bitmap
     */
    private void cacheBitmap(BitmapRequest request, Bitmap bitmap) {
        if (request != null && bitmap != null) {
            synchronized (AbstractLoader.class) {
                mBitmapCache.put(request, bitmap);
            }
        }
    }

    /**
     * 抽象加载策略 因为加载网络图片和本地图片有差异
     *
     * @param bitmapRequest
     * @return
     */
    protected abstract Bitmap onLoad(BitmapRequest bitmapRequest);

    /**
     * 显示用户 自定义的加载中图片
     *
     * @param bitmapRequest
     */
    private void showLoadingImage(BitmapRequest bitmapRequest) {
        if (hasLoadingPlaceHolder()) {
            final ImageView imageView = bitmapRequest.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(mDisplayConfig.loadingImage);
                    }
                });
            }

        }
    }

    protected boolean hasLoadingPlaceHolder() {
        return (mDisplayConfig != null && mDisplayConfig.loadingImage > 0);
    }


    /**
     * 显示用户 自定义的加载失败图片
     *
     * @param bitmapRequest
     */
    private void showLoadFailImage(BitmapRequest bitmapRequest) {
        if (hasLoadFailPlaceHolder()) {
            final ImageView imageView = bitmapRequest.getImageView();
            if (imageView != null) {
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(mDisplayConfig.loadFaildImag);
                    }
                });
            }

        }
    }

    protected boolean hasLoadFailPlaceHolder() {
        return (mDisplayConfig != null && mDisplayConfig.loadFaildImag > 0);
    }
}
