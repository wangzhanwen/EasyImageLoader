package com.wzw.easyimageloader.request;

import android.util.Log;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestQueue {
    private static final String TAG = "RequestQueue";
    /**
     * 阻塞队列：
     * 多线程共享
     * 生产效率和消费效率相差太远
     * 使用优先级队列
     * 优先级高的队列优先被消费
     * 每一个产品都有编号
     */
    private PriorityBlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<>();

    // 转发器的数量
    private int threadCount;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private RequestDispatcher[] mDispatchers;


    public RequestQueue(int threadCount) {
         this.threadCount = threadCount;
    }

    /**
     * 添加任务
     *
     * @param request
     */
    public void addRequest(BitmapRequest request) {

        // 判断请求对列是否包含请求
        if (!mRequestQueue.contains(request)) {

            request.setSerialNo(atomicInteger.incrementAndGet());
            mRequestQueue.add(request);

        } else {
            Log.i(TAG, "请求已经存在 编号:" + request.getSerialNo());
        }
    }

    /**
     * 开始请求
     */
    public void start(){
        stop();
        startDispatcher();
    }

    /**
     * 开启转发器
     */
    private void startDispatcher() {
        mDispatchers = new RequestDispatcher[threadCount];

        for (int i = 0; i < mDispatchers.length; i++) {

            RequestDispatcher requestDispatcher = new RequestDispatcher(mRequestQueue);
            mDispatchers[i]= requestDispatcher;
            mDispatchers[i].start();

        }
    }

    /**
     * 暂停请求
     */
    public void stop(){

    }
}
