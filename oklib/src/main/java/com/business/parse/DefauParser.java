package com.business.parse;

import com.business.DataInfo;

import org.json.JSONObject;

/**
 * @author: BaiCQ
 * @ClassName: DefauParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class DefauParser implements Parser {
    public final static int CODE_OK = 0;

    @Override
    public DataInfo parse(String json) throws Exception {
        DataInfo info = new DataInfo();
        JSONObject result = new JSONObject(json);
        info.setTotal(1);
        info.setIndex(0);
        info.setCode(result.optInt("status"));
        info.setBody(json);
        return info;
    }

    @Override
    public boolean success(int code) {
        return code == CODE_OK;
    }

    @Override
    public String[] headers() {
        return new String[]{TOKEN_KEY};
    }
}
