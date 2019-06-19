package com.wzw.easyimageloader.policy;

import com.wzw.easyimageloader.request.BitmapRequest;

public interface LoadPolicy {

    /**
     *
     * @param request0
     * @param request1
     * @return
     */
    int compare(BitmapRequest request0, BitmapRequest request1);
}
