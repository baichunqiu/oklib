package com.bcq.net.callback;

import com.bcq.net.domain.Request;
import com.bcq.net.view.LoadTag;
import com.business.DataInfo;
import com.business.GeneralCallBack;
import com.business.IBusiCallback;
import com.business.ILoadTag;

/**
 * @author: BaiCQ
 * @ClassName: BusiCallback
 * @Description: 业务处理 没有body的回调
 */
public class BusiCallback extends GeneralCallBack<Integer, String> {
    public BusiCallback(Request<Integer, String> request) {
        super(request.getTag(), request.getParser(), request.getiCallback());
    }

    @Override
    protected Integer parseResult(DataInfo netInfo, IBusiCallback<Integer, String> iCallback) {
        return netInfo.getCode();
    }

    @Override
    protected String parseExtra(DataInfo netInfo) {
        return netInfo.getMessage();
    }
}
