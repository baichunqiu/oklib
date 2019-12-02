package com.kit;

import android.os.Environment;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: KitConstant
 * @date: 2018/8/17
 * @Description: KitConstant 公共常量
 */
public class KitConstant {
    /*****************存储路径 path*****************/
    public final static String ROOT_NAME = "_kit";
    public final static String ROOT = Environment.getExternalStorageDirectory() + File.separator;
    public final static String BASE_PATH = ROOT + ROOT_NAME + File.separator;

    /***************** Intent key *****************/
    public final static String KEY_BASE = "key_basis";
    public final static String KEY_OBJ = "key_obj";

}
