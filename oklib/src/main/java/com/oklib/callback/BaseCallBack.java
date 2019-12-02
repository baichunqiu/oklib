package com.oklib.callback;

import com.oklib.core.Dispatcher;

import okhttp3.Request;

public abstract class BaseCallBack<T> implements CallBack<T> {
    @Override
    public void onBefore(Request.Builder request) {
    }

    @Override
    public void onAfter() {
    }

    @Override
    public void onProgress(float progress, long total) {
    }

    /**
     * 分发UI Thread
     *
     * @param run
     */
    protected void dispatch(Runnable run) {
        Dispatcher.get().dispatch(run);
    }
}
