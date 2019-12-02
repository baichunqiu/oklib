package com.business;

import android.text.TextUtils;
import android.util.Log;


import com.business.parse.Parser;
import com.business.parse.ParserHelper;
import com.oklib.callback.BaseCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * @param <R> result:
 *            List 数据集合
 *            int  code
 * @param <E> extra：
 *            boolen loadfull
 *            message info
 * @author: BaiCQ
 * @ClassName: GeneralCallBack
 * @Description: GeneralCallBack
 * 1.响应结果是List
 * 2.响应结果无，只有code和message
 */
public abstract class GeneralCallBack<R, E> extends BaseCallBack<R> {
    protected final String TAG = this.getClass().getSimpleName();
    public final static String _unKnow_error = "未知错误!";
    public final static Map<String, String> cacheHeaders = new HashMap(2);
    private ILoadTag tTag;
    protected Parser parser;
    private IBusiCallback<R, E> iBusiCallback;

    private int code;
    private E extra;
    private String message = "";

    public GeneralCallBack(ILoadTag loadTag, Parser parser, IBusiCallback<R, E> iBusiCallback) {
        this.tTag = loadTag;
        this.iBusiCallback = iBusiCallback;
        this.parser = null != parser ? parser : ParserHelper.getParser();
    }

    /**
     * 根据header的key缓存
     *
     * @param response
     */
    private final void cacheHeader(Response response) {
        String[] headerKeys = parser.headers();
        for (String key : headerKeys) {
            String value = response.header(key);
            if (key.equalsIgnoreCase(Parser.TOKEN_KEY)) {
                value = "Bearer " + value;
            }
            cacheHeaders.put(key, value);
        }
    }

    @Override
    public void onBefore(okhttp3.Request.Builder builder) {
        if (null != tTag)tTag.show();
        for (Map.Entry<String, String> entry : cacheHeaders.entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public R onParse(Response response) throws Exception {
        String res = response.body().string();
        DataInfo dataInfo = null;
        if (ParserHelper.debug()) Log.e(TAG, "res = " + res);
        if (!TextUtils.isEmpty(res)) {
            if (null != parser) {
                dataInfo = parser.parse(res);
                if (null != dataInfo) {
                    message = dataInfo.getMessage();
                    code = dataInfo.getCode();
                    extra = parseExtra(dataInfo);
                    if (ParserHelper.debug()) Log.e(TAG, "code = " + code);
                }
            }
        }
        return parseResult(dataInfo, iBusiCallback);
    }

    @Override
    public void onResponse(R result) {
        boolean success = false;
        if (result instanceof Integer) {
            success = parser.success(code);
        } else if (result instanceof List) {
            success = null != result && !((List) result).isEmpty();
        }
        if (success) {
            iBusiCallback.onSuccess(result, extra);
        } else {
            iBusiCallback.onError(code, TextUtils.isEmpty(message) ? _unKnow_error : message);
        }
    }

    @Override
    public void onError(Exception e) {
        message = e.getLocalizedMessage();//错误信息级别比接口返回信息稿
        iBusiCallback.onError(code, message);
    }

    @Override
    public void onAfter() {
        if (null != tTag) tTag.dismiss();
        iBusiCallback.onAfter(code, message);
    }

    /**
     * 解析结果集
     *
     * @param dataInfo
     * @param iBusiCallback
     */
    protected abstract R parseResult(DataInfo dataInfo, IBusiCallback<R, E> iBusiCallback)throws Exception;

    /**
     * 解析附加信息
     *
     * @param dataInfo
     */
    protected abstract E parseExtra(DataInfo dataInfo);
}
