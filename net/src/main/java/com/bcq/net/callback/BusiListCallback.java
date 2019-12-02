package com.bcq.net.callback;

import com.bcq.net.callback.base.IListCallback;
import com.bcq.net.domain.Request;
import com.business.DataInfo;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.kit.GsonUtil;
import com.kit.Logger;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: BusiListCallback
 * @Description: 业务处理回调
 */
public class BusiListCallback<R> extends GeneralCallBack<List<R>, Boolean> {
    public BusiListCallback(Request<List<R>, Boolean> request) {
        super(request.getTag(), request.getParser(), request.getiCallback());
    }

    @Override
    protected List<R> parseResult(DataInfo netInfo, IBusiCallback<List<R>, Boolean> iCallback) throws Exception {
        if (!parser.success(netInfo.getCode()) || !(iCallback instanceof IListCallback))
            return null;
        IListCallback<R> iListCallback = (IListCallback<R>) iCallback;
        //body 原始数据解析
        List<R> resultData = GsonUtil.json2List(netInfo.getBody(), iListCallback.setType());
        //预处理数据
        resultData = iListCallback.onPreprocess(resultData);
        Logger.e(TAG, "parseResult : " + (null == resultData ? 0 : resultData.size()));
        return resultData;
    }

    @Override
    protected Boolean parseExtra(DataInfo netInfo) {
        return netInfo.getIndex() >= netInfo.getTotal();
    }
}
