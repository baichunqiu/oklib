package com.oklib.callback;

import okhttp3.Request;
import okhttp3.Response;

public interface CallBack<T> {
    //  UI Thread
    void onBefore(Request request);

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