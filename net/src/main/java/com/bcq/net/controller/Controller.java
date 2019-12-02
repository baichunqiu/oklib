package com.bcq.net.controller;

import com.bcq.net.NetKit;
import com.bcq.net.callback.base.BaseCallback;
import com.bcq.net.callback.base.BaseListCallback;
import com.bcq.net.callback.base.IRefreshView;
import com.bcq.net.domain.Request;
import com.bcq.net.enums.ReqMode;
import com.bcq.net.view.LoadTag;
import com.business.parse.Parser;
import com.kit.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: BaiCQ
 * @createTime: 2017/1/13 11:38
 * @className: Controller
 * @Description: 供没有listview的页面使用的控制器
 */
public abstract class Controller<T> implements IPage {
    public final static String TAG = "Controller";
    private Class<T> tClass;
    private Request<List<T>, Boolean> request;
    protected int currentPage = PAGE_FIRST;//当前页的索引

    public Controller(Class<T> clazz) {
        this.tClass = clazz;
    }

    /**
     * 相同参数再次请求数据  根据refresh 修改 currentPage
     *
     * @param refresh
     */
    protected void requestAgain(boolean refresh) {
        if (null != request) {
            Map<String, Object> params = request.getParams();
            if (null != params && params.containsKey(KEY_PAGE_INDEX)) {
                currentPage = refresh ? PAGE_FIRST : Integer.valueOf(params.get(KEY_PAGE_INDEX).toString()) + 1;
                params.put(KEY_PAGE_INDEX, currentPage + "");
            }
            request = (Request<List<T>, Boolean>) request.requestAgain();
        }
    }

    public void postArr(String url, Map<String, Object> params, LoadTag loadBar) {
        obtainNetData(true, url, params, null, loadBar, ReqMode.POST, null, null);
    }

    public void getArr(String url, Map<String, Object> params, LoadTag loadBar) {
        obtainNetData(true, url, params, null, loadBar, ReqMode.GET, null, null);
    }

    protected final void obtainNetData(final boolean isRefresh, String mUrl, Map<String, Object> params, Parser parser, LoadTag dialog, ReqMode reqMode, final IOperate operator, IRefreshView refreshView) {
        Logger.e(TAG, "obtainNetData");
        if (null != operator) {//operator 不为空 需要分页处理
            if (isRefresh) currentPage = PAGE_FIRST;
            if (null == params) params = new HashMap<>(2);
            if (!params.containsKey(PAGE_SIZE)) {
                params.put(KEY_PAGE_SIZE, PAGE_SIZE + "");
                params.put(KEY_PAGE_INDEX, currentPage + "");
            }
        }
        BaseListCallback<T> baseListCallback = new BaseListCallback<T>(refreshView) {
            List<T> tempData = null;

            @Override
            public List<T> onPreprocess(List<T> rawData) {
                return null == operator ? rawData : operator.onPreprocess(rawData);
            }

            @Override
            public void onSuccess(List<T> tList, Boolean loadFull) {
                super.onSuccess(tList, loadFull);
                tempData = tList;
                if (null != operator && !loadFull) {
                    currentPage++;
                }
            }

            @Override
            public void onAfter (int status, String msg) {
                super.onAfter( status, msg);
                onRefreshData(tempData, isRefresh);
                if (null == tempData && null != operator) operator.onError(status, msg);
            }

            @Override
            public Class<T> setType() {
                return tClass;
            }
        };
        if (reqMode == ReqMode.GET) {
            request = NetKit.getArr(dialog, mUrl, params, parser, baseListCallback);
        } else {
            request = NetKit.postArr(dialog, mUrl, params, parser, baseListCallback);
        }
    }


    /**
     * 没有结果集，只有状态的post请求
     *
     * @param mUrl   url
     * @param params 参数
     * @param dialog 进度条
     */
    public void post(final String mUrl, Map<String, Object> params, LoadTag dialog) {
        NetKit.post(dialog, mUrl, params, new BaseCallback() {
            @Override
            public void onAfter(int status, String sysMsg) {
                super.onAfter(status, sysMsg);
                _onResponceCallBack(mUrl, status, sysMsg);
            }
        });
    }

    /**
     * 接口响应回调
     *
     * @param url       接口连接
     * @param stateCode 状态 1：成功 其他：失败
     * @param msg       服务器返回msg
     */
    public void _onResponceCallBack(String url, int stateCode, String msg) {

    }

    /**
     * 刷新数据数据回调
     *
     * @param netData
     * @param isRefresh
     */
    public abstract void onRefreshData(List<T> netData, boolean isRefresh);


    public interface IOperate<T> {
        /**
         * 解析数据时,数据预处理
         */
        List<T> onPreprocess(List<T> rawData);

        /**
         * 加载失败回调
         */
        void onError(int status, String errMsg);
    }

}