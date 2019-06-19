package com.wzw.easyimageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.wzw.easyimageloader.request.BitmapRequest;
import com.wzw.easyimageloader.utils.BitmapDecoder;
import com.wzw.easyimageloader.utils.ImageViewHelper;

import java.io.File;

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest bitmapRequest) {
        // 得到本地图片的路径
        final String path = Uri.parse(bitmapRequest.getImageUri()).getPath();
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithBitmap(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(path, options);
            }
        };


        return bitmapDecoder.decodeBitmap(ImageViewHelper.getImageViewWidth(bitmapRequest.getImageView()),
                ImageViewHelper.getImageViewHeight(bitmapRequest.getImageView()));

    }
}
