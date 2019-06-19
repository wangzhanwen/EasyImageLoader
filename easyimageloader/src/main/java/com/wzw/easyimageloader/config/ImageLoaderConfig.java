package com.wzw.easyimageloader.config;

import com.wzw.easyimageloader.cache.BitmapCache;
import com.wzw.easyimageloader.cache.NoCache;
import com.wzw.easyimageloader.policy.LoadPolicy;
import com.wzw.easyimageloader.policy.ReversePolicy;

/**
 * 图片加载配置
 * <p>
 * author: 1412237504@qq.com
 */
public class ImageLoaderConfig {

    // 缓存策略(默认双缓存)
    private BitmapCache mBitmapCache = new NoCache();

    // 加载策略
    private LoadPolicy mLoadPolicy = new ReversePolicy();

    // 默认线程数
    private int mThreadCount = Runtime.getRuntime().availableProcessors();

    // 加载图片配置
    private DisplayConfig mDisplayConfig = new DisplayConfig();

    private ImageLoaderConfig() {

    }


    /**
     * 构建者模式
     */
    public static class Builder {

        private ImageLoaderConfig imageLoaderConfig;

        public Builder() {

            imageLoaderConfig = new ImageLoaderConfig();
            imageLoaderConfig.mDisplayConfig = new DisplayConfig();
        }

        /**
         * 设置缓存策略
         *
         * @param bitmapCache
         * @return
         */
        public Builder setCachePolicy(BitmapCache bitmapCache) {

            imageLoaderConfig.mBitmapCache = bitmapCache;

            return this;
        }

        /**
         * 设置加载策略
         *
         * @param loadPolicy
         * @return
         */
        public Builder setLoadPolicy(LoadPolicy loadPolicy) {
            imageLoaderConfig.mLoadPolicy = loadPolicy;
            return this;
        }


        /**
         * 设置线程数
         *
         * @param count
         * @return
         */
        public Builder setTheadCount(int count) {
            imageLoaderConfig.mThreadCount = count;
            return this;
        }


        /**
         * 设置加载中图片
         *
         * @param resID
         * @return
         */
        public Builder setLoadingImage(int resID) {

            imageLoaderConfig.mDisplayConfig.loadingImage = resID;
            return this;

        }

        /**
         * 设置加载失败图片
         *
         * @param resID
         * @return
         */
        public Builder setLoadFailImage(int resID) {

            imageLoaderConfig.mDisplayConfig.loadFaildImag = resID;

            return this;

        }

        public ImageLoaderConfig build(){
            return imageLoaderConfig;
        }
    }


    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }

    public int getThreadCount() {
        return mThreadCount;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

}
