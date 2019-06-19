package com.wzw.easyimageloader.policy;

import com.wzw.easyimageloader.request.BitmapRequest;


/**
 * 先进先出
 *
 */
public class SerialPolicy implements LoadPolicy{

    @Override
    public int compare(BitmapRequest request0, BitmapRequest request1) {
        return request0.getSerialNo() - request1.getSerialNo();
    }
}
