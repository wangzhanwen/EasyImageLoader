package com.wzw.easyimageloader.cache;

import android.graphics.Bitmap;
import com.wzw.easyimageloader.request.BitmapRequest;

public interface BitmapCache {
    /**
     * 缓存bitmap
     *
     * @param bitmapRequest
     * @param bitmap
     */
    void put(BitmapRequest bitmapRequest, Bitmap bitmap);

    /**
     * 获得缓存的bitmap
     *
     * @param bitmapRequest
     */
    Bitmap get(BitmapRequest bitmapRequest);

    /**
     * 移除缓存
     *
     * @param bitmapRequest
     */
    void remove(BitmapRequest bitmapRequest);

}
