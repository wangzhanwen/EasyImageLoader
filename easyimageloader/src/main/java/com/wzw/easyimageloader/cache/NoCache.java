package com.wzw.easyimageloader.cache;

import android.graphics.Bitmap;

import com.wzw.easyimageloader.request.BitmapRequest;

public class NoCache implements BitmapCache{
    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {

    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {
        return null;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {

    }
}
