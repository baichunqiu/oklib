package com.oklib.callback;

import okhttp3.Request;
import okhttp3.Response;

public interface CallBack<T> {
    //  UI Thread 此处可自主添加本次请求header
    void onBefore(Request.Builder builder);

    // 非UI Thread
    T onParse(Response response) throws Exception;

    //UI Thread
    void onProgress(float progress, long total);

    //UI Thread
    void onResponse(T response);

    //UI Thread
    void onError(Exception e);

    //UI Thread
    void onAfter();


}