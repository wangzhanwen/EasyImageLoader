package com.wzw.easyimageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.wzw.easyimageloader.request.BitmapRequest;

public class NullLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest bitmapRequest) {

        Log.e("jason", "图片无法记载!");
        return null;
    }
}
