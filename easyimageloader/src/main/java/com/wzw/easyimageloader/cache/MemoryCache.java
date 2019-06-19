package com.wzw.easyimageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.wzw.easyimageloader.request.BitmapRequest;

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mLruCache;

    public MemoryCache() {

        int maxSize = (int) (Runtime.getRuntime().freeMemory() / 1024 / 8);
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };

    }

    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        mLruCache.put(bitmapRequest.getImageUriMD5(), bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return mLruCache.get(bitmapRequest.getImageUriMD5());
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        mLruCache.remove(bitmapRequest.getImageUriMD5());
    }
}
