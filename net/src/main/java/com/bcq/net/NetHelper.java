package com.bcq.net;

import com.business.parse.Parser;
import com.business.parse.ParserHelper;
import com.kit.KitConstant;

import java.io.File;

public class NetHelper {
    private static Processor processor;
    private static String ROOT_NAME = KitConstant.ROOT_NAME;

    public static void setBasePath(String rootName) {
        ROOT_NAME = rootName;
    }

    public static String getBasePath(){
        return KitConstant.ROOT + ROOT_NAME + File.separator;
    }
    /**
     * 设置默认Json解析器
     * @param defaultParser
     */
    public static void setDefaultParser(Parser defaultParser) {
        ParserHelper.setDefaultParser(defaultParser);
    }

    /**
     * 设置错误处理器
     * @param processor
     */
    public static void setProcessor(Processor processor) {
        NetHelper.processor = processor;
    }

    public static Processor getProcessor() {
        return processor;
    }
}
