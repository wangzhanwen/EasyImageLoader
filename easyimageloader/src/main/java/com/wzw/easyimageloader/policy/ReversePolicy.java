package com.wzw.easyimageloader.policy;

import com.wzw.easyimageloader.request.BitmapRequest;

/**
 * 后进先出
 *
 */
public class ReversePolicy implements LoadPolicy{



    @Override
    public int compare(BitmapRequest request0, BitmapRequest request1) {
        return request1.getSerialNo() - request0.getSerialNo();
    }
}
