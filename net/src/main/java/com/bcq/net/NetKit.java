package com.bcq.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.ProgressBar;

import com.bcq.net.callback.BusiCallback;
import com.bcq.net.callback.BusiListCallback;
import com.bcq.net.callback.base.IListCallback;
import com.bcq.net.domain.Request;
import com.bcq.net.enums.NetType;
import com.bcq.net.enums.ReqMode;
import com.bcq.net.view.LoadTag;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Parser;
import com.kit.Logger;
import com.kit.UIKit;
import com.oklib.OkApi;
import com.oklib.callback.FileCallBack;

import java.io.File;
import java.util.Map;


/**
 * @author: BaiCQ
 * @ClassName: OkUtil
 * @Description: OkHttp网络请求工具类
 */
public class NetKit {
    public final static String TAG = "NetKit";

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param parser        解析器
     * @param IListCallback 有body的网络回调
     * @param <R>           tag的泛型
     */
    public static <R> Request postArr(ILoadTag tag, String url, Map<String, Object> params, Parser parser, IListCallback<R> IListCallback) {
        Request request = new Request(tag, url, params, parser, IListCallback, ReqMode.POST);
        if (check(tag, url)) {
            OkApi.post(url, params, new BusiListCallback(request));
        }
        return request;
    }

    public static <R> Request getArr(ILoadTag tag, String url, Map<String, Object> params, IListCallback<R> IListCallback) {
        return getArr(tag, url, params, null, IListCallback);
    }

    /**
     * @param tag           请求的tag
     * @param url           url
     * @param params        Map<String,String>
     * @param parser        解析器
     * @param IListCallback 有body的网络回调
     * @param <R>           tag的泛型
     */
    public static <R> Request getArr(ILoadTag tag, String url, Map<String, Object> params, Parser parser, IListCallback<R> IListCallback) {
        Request request = new Request(tag, url, params, parser, IListCallback, ReqMode.GET);
        if (check(tag, url)) {
            OkApi.get(url, params, new BusiListCallback(request));
        }
        return request;
    }

    public static void post(ILoadTag tag, String url, Map<String, Object> params, IBusiCallback<Integer, String> iCallback) {
        post(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     */
    public static Request post(ILoadTag tag, String url, Map<String, Object> params, Parser parser, IBusiCallback<Integer, String> iCallback) {
        Request request = new Request(tag, url, params, parser, iCallback, ReqMode.POST);
        if (check(tag, url)) {
            OkApi.post(url, params, new BusiCallback(request));
        }
        return request;
    }

    public static void get(ILoadTag tag, String url, Map<String, Object> params, IBusiCallback<Integer, String> iCallback) {
        get(tag, url, params, null, iCallback);
    }

    /**
     * @param tag       请求的tag
     * @param url       url
     * @param params    Map<String,String>
     * @param parser    解析器
     * @param iCallback 无body的网络回调
     */
    public static Request get(ILoadTag tag, String url, Map<String, Object> params, Parser parser, IBusiCallback<Integer, String> iCallback) {
        Request request = new Request(tag, url, params, parser, iCallback, ReqMode.GET);
        if (check(tag, url)) {
            OkApi.get(url, params, new BusiCallback(request));
        }
        return request;
    }

    /**
     * 下载文件
     *
     * @param tag
     * @param url
     * @param params
     * @param fileName 文件名称 存放在downLoad 目录下
     * @param <T>
     */
    public static <T> void downLoad(T tag, String url, Map<String, Object> params, String fileName, final ProgressBar mProgressBar) {
        OkApi.download(url, params, new FileCallBack(NetHelper.getBasePath(), fileName) {
            @Override
            public void onResponse(File response) {

            }

            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onProgress(float progress, long total) {
                super.onProgress(progress, total);
                mProgressBar.setProgress((int) (100 * progress));
            }
        });
    }

    /**
     * 检查网 并根据tag的类型 取消加载动画
     *
     * @param tag 请求的TAG
     * @return
     */
    public static <T> boolean check(T tag, String url) {
        if (TextUtils.isEmpty(url)) {
            Logger.e(TAG, "you net request url is null ，please check it！");
            return false;
        }
        boolean connected = isNetworkConnected();
        if (!connected && null != tag && tag instanceof LoadTag) {
            ((LoadTag) tag).dismiss();
        }
        return connected;
    }

    /**
     * 是否连接上网络
     *
     * @return 连接上true，未连接上false
     * @date: 2017-1-16 下午17:52
     */
    public static boolean isNetworkConnected() {
        return NetType.NONE != getNetWorkState();
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    public static NetType getNetWorkState() {
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) UIKit.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        //如果网络连接，判断该网络类型
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NetType.WIFI;//wifi
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NetType.MOBILE;//mobile
            }
        }//网络异常
        return NetType.NONE;
    }
}
