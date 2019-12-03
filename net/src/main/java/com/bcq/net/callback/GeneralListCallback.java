package com.bcq.net.callback;

import com.bcq.net.callback.base.IListCallback;
import com.business.DataInfo;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;
import com.business.parse.Parser;
import com.kit.GsonUtil;
import com.kit.Logger;

import java.util.List;

/**
 * @author: BaiCQ
 * @ClassName: GeneralListCallback
 * @Description: 列表数据回调
 */
public class GeneralListCallback<R> extends GeneralCallBack<List<R>, Boolean> {

    public GeneralListCallback(ILoadTag loadTag, Parser parser, IBusiCallback<List<R>, Boolean> iBusiCallback) {
        super(loadTag, parser, iBusiCallback);
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
