package com.wzw.easyimageloader.request;

import android.util.Log;

import com.wzw.easyimageloader.loader.Loader;
import com.wzw.easyimageloader.loader.LoaderManager;

import java.util.concurrent.PriorityBlockingQueue;

import static android.content.ContentValues.TAG;

/**
 * 转发器
 * 请求转发线程，不独请求对列中获得请求
 */
public class RequestDispatcher extends Thread {
    // 请求对列
    private PriorityBlockingQueue<BitmapRequest> mRequestQueue;

    public RequestDispatcher(PriorityBlockingQueue<BitmapRequest> requestQueue) {
        this.mRequestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                // 阻塞式函数
                BitmapRequest bitmapRequest = mRequestQueue.take();
                // 处理请求对象
                String schema = parseSchema(bitmapRequest.getImageUri());
                // 获取加载器
                Loader loader = LoaderManager.getInstance().getLoader(schema);
                loader.loadImage(bitmapRequest);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String imageUrl) {

        if (imageUrl == null) {
            return null;
        }

        if (imageUrl.contains("://")) {
            return imageUrl.split("://")[0];
        } else {
            Log.i(TAG, "不支持此类型");
            return null;
        }
    }
}
