package com.sh3h.dataprovider.util;

/**
 * 常量类
 */
public class ConstantUtil {

    public static final int DATA_TERM_OF_VALIDITY = 60; //数据有效期 60天
    public static final long ONE_DAY_MSEC = 86400000; //1天的毫秒数
    public static final long ONE_MINUTE_MSEC = 60000; //1分钟的毫秒数
    public static final long ONE_HOUR_MSEC = 3600000; //一小时的毫秒数

    //初始化步骤
    public static class InitStep {
        public static final String INIT_STEP = "initStep";

        //检查权限
        public static final int CHECK_PERMISSION = 1;
        //初始化目录
        public static final int INIT_CONFIG = 2;
        //
        public static final int SAVE_USER_CONFIG = 3;
        //清理超过两个月的历史数据
        public static final int CLEAN_DATA = 4;
        //下载词语
        public static final int DOWNLOAD_WORD = 5;
        //初始化结束
        public static final int INIT_OVER = 6;

    }

    //请求码
    public static class RequestCode {
        //检查权限
        public static final int CHECK_PERMISSION = 1000;
    }
}
