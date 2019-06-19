package com.wzw.easyimageloader.loader;

import com.wzw.easyimageloader.request.BitmapRequest;

public interface Loader {

    /**
     * 加载图片
     *
     * @param bitmapRequest
     */
    void loadImage(BitmapRequest bitmapRequest);
}
