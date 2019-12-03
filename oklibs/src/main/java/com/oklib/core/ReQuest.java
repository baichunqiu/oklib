package com.oklib.core;

import com.oklib.callback.CallBack;

import java.util.Map;

public class ReQuest<T> {
    public String url;
    public Map<String, Object> param;
    public CallBack<T> callBack;
    private Method method;

    private ReQuest() {
    }

    public Map<String, Object> param() {
        return param;
    }

    public ReQuest request() {
        if (Method.post == method) {
            Core.core().post(url, param, callBack);
        } else if (Method.get == method) {
            Core.core().get(url, param, callBack);
        }
        return this;
    }

    @Override
    public String toString() {
        return "ReQuest{" +
                "url='" + url + '\'' +
                ", param=" + param +
                ", callBack=" + callBack +
                ", method='" + method + '\'' +
                '}';
    }

    /**
     * ReQuest.Builder
     *
     * @param <T>
     */
    public static class Builder<T> {
        private String url;
        private Map<String, Object> param;
        private CallBack<T> callBack;
        private Method method;

        private Builder() {
        }

        public static Builder get() {
            Builder builder = new Builder();
            builder.method = Method.get;
            return builder;
        }

        public static Builder post() {
            Builder builder = new Builder();
            builder.method = Method.post;
            return builder;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder param(Map<String, Object> param) {
            this.param = param;
            return this;
        }

        public Builder callback(CallBack<T> callBack) {
            this.callBack = callBack;
            return this;
        }

        public ReQuest build() {
            ReQuest reQuest = new ReQuest();
            reQuest.method = method;
            reQuest.url = url;
            reQuest.param = param;
            reQuest.callBack = callBack;
            return reQuest;
        }
    }
}
