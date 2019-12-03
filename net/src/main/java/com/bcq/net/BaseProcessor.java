package com.bcq.net;

import com.kit.Logger;
import com.oklib.core.ReQuest;

/**
 * @Author: BaiCQ
 * @ClassName: BaseProcessor
 * @CreateDate: 2019/3/27 14:08
 * @Description:
 */
public abstract class BaseProcessor implements Processor{
    private final static String TAG = "BaseProcessor";
    //最大重复请求次数
    private final static int MAX_REPEAT = 1;
    //重复次数
    private int repeat = 1;
    //缓存上次code
    private int lastCode = 0;

    @Override
    public final void process(int code, ReQuest request) {
        if (code != lastCode) {
            repeat = 1;
        } else {
            repeat++;
        }
        if (repeat > MAX_REPEAT){
            Logger.e("The maximum limit of repeat is 3 . repeat = "+ MAX_REPEAT);
            return;
        }
        if (processCode(code))
            request.request();
    }

    /**
     * 处理error code
     * @param code
     * @return true： 需要再次尝试请求
     *          false：不需要再次请求
     */
    protected abstract boolean processCode(int code);
}