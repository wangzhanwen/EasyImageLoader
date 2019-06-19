package com.wzw.easyimageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.wzw.easyimageloader.disk.DiskLruCache;
import com.wzw.easyimageloader.disk.IOUtil;
import com.wzw.easyimageloader.request.BitmapRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class DiskCache implements BitmapCache {

    private static DiskCache mDiskCache;
    //缓存路径
    private String mCacheDir = "image_cache";
    //MB
    private static final int MB = 1024 * 1024;
    //jackwharton的杰作
    private DiskLruCache mDiskLruCache;

    private DiskCache(Context context) {
        initDiskCache(context);
    }

    public static DiskCache getInstance(Context context) {
        if (mDiskCache == null) {
            synchronized (DiskCache.class) {
                if (mDiskCache == null) {
                    mDiskCache = new DiskCache(context);
                }
            }
        }
        return mDiskCache;
    }

    private void initDiskCache(Context context) {
        // 得到缓存的目录： android/data/xxx.xxx.xxx/cache/image_cache
        File direcotry = getDiskCache(mCacheDir, context);
        if (!direcotry.exists()) {
            direcotry.mkdir();
        }

        try {

            mDiskLruCache = DiskLruCache.open(direcotry, 1, 1, 50 * MB);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private File getDiskCache(String mCacheDir, Context context) {
        String cachePath;
        //默认缓存路径
        return new File(Environment.getExternalStorageDirectory(), mCacheDir);
    }



    @Override
    public void put(BitmapRequest bitmapRequest, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        OutputStream outputStream = null;
        try {
            editor = mDiskLruCache.edit(bitmapRequest.getImageUriMD5());
            outputStream = editor.newOutputStream(0);

            if (perseBitmap2Disk(bitmap, outputStream)) {
                editor.commit();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean perseBitmap2Disk(Bitmap bitmap, OutputStream outputStream) {

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bufferedOutputStream);

        try {
            bufferedOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            IOUtil.closeQuietly(bufferedOutputStream);
        }
        return true;
    }

    @Override
    public Bitmap get(BitmapRequest bitmapRequest) {

        try {

            if (mDiskLruCache == null) {

                Log.e(TAG, "mDiskLruCache is null");
                return null;
            }

            if (bitmapRequest == null) {
                Log.e(TAG, "bitmapRequest is null");
                return null;
            }

            if (bitmapRequest.getImageUriMD5() == null) {
                Log.e(TAG, "bitmapRequest.getImageUriMD5() is null");
                return null;
            }


            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(bitmapRequest.getImageUriMD5());

            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void remove(BitmapRequest bitmapRequest) {
        try {
            mDiskLruCache.remove(bitmapRequest.getImageUriMD5());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
