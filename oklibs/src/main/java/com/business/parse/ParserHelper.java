package com.business.parse;


public class ParserHelper {
    private static Parser parser;
    private static boolean debug = true;

    /**
     * 设置默认Json解析器
     *
     * @param defaultParser
     */
    public static void setDefaultParser(Parser defaultParser) {
        ParserHelper.parser = defaultParser;
    }

    public static Parser getParser() {
        if (null == parser) {//使用默认解析器
            parser = new DefauParser();
        }
        return parser;
    }

    public static void setDebug(boolean debug) {
        ParserHelper.debug = debug;
    }

    public static boolean debug() {
        return debug;
    }
}
