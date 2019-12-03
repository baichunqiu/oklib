//package com.bcq.net.domain;
//
//import com.bcq.net.NetApi;
//import com.bcq.net.callback.base.BaseListCallback;
//import com.bcq.net.callback.base.IListCallback;
//import com.bcq.net.enums.ReqMode;
//import com.business.IBusiCallback;
//import com.business.ILoadTag;
//import com.business.parse.Parser;
//
//import java.io.Serializable;
//import java.util.Map;
//
///**
// * @author: BaiCQ
// * @ClassName: ReQuest
// * @date: 2018/6/27
// * @Description: ReQuest 再次请求参数封装实体
// */
//public class Request<B, E> implements Serializable {
//    private ILoadTag tag;
//    private String url;
//    private Map<String, Object> params;
//    private Parser parser;
//    private IBusiCallback<B, E> iCallback;
//    private ReqMode type;
//
//    public <I> Request(ILoadTag tag, String url, Map<String, Object> params, Parser parser, IBusiCallback<B, E> iCallback, ReqMode type) {
//        this.tag = tag;
//        this.url = url;
//        this.params = params;
//        this.parser = parser;
//        this.iCallback = iCallback;
//        this.type = type;
//    }
//
//    public ILoadTag getTag() {
//        return tag;
//    }
//
//    public ReqMode getType() {
//        return type;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public Map<String, Object> getParams() {
//        return params;
//    }
//
//    public Parser getParser() {
//        return parser;
//    }
//
//    public IBusiCallback<B, E> getiCallback() {
//        return iCallback;
//    }
//
//    private boolean isArr() {
//        return null != iCallback && iCallback instanceof BaseListCallback;
//    }
//
//    /**
//     * 再次发起请求
//     */
//    public <R> Request requestAgain() {
//        switch (type) {
//            case GET:
//                if (isArr()) {
//                    return NetApi.getArr(tag, url, params, parser, (IListCallback<R>) iCallback);
//                } else {
//                    return NetApi.get(tag, url, params, parser, (IBusiCallback<Integer, String>) iCallback);
//                }
//            case POST:
//                if (isArr()) {
//                    return NetApi.postArr(tag, url, params, parser, (IListCallback<R>) iCallback);
//                } else {
//                    return NetApi.post(tag, url, params, parser, (IBusiCallback<Integer, String>) iCallback);
//                }
//        }
//        return null;
//    }
//
//}
