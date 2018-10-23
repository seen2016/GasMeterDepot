package com.sh3h.dataprovider.data.local.config;


public class SystemConfig extends BaseConfig {

    public static final String PARAM_LOCATION_URL = "location.url";
    //数据的有效期
    public static final String DATA_TERM_OF_VALIDITY = "sys.data.term.of.validity";

    /**
     * gps定位方式
     */
    public static final String PARAM_SYST_GPS_TYPE = "sys.gps.type";
    public static final String GPS_TYPE_NONE = "none";
    public static final String GPS_TYPE_SYSTEM = "system";
    public static final String GPS_TYPE_BAIDU = "baidu";

    /**
     * 数据后台地址
     */
    public static final String PARAM_SERVER_BASE_URI = "server.baseuri";
    public static final String PARAM_SERVER_REVERSE_BASE_URI = "reserved.server.baseuri";

    /**
     * 推送后台地址
     */
    public static final String PARAM_BROKER_URL = "broker.url";
    public static final String PARAM_BROKER_REVERSE_URL = "reserved.broker.url";
    /**
     * Countly服务器地址
     **/
    public static final String PARAM_COUNTLY_SERVER_URI = "countly.server.uri";
    /**
     * 是否使用备用地址
     */
    public static final String PARAM_USING_RESERVED_ADDRESS = "using.reserved.address";

    /**
     * 调试模式
     */
    public static final String PARAM_SYS_IS_DEBUG_MODE = "sys.is_debug_mode";

    /**
     * 呼吸包发送频率
     */
    public static final String PARAM_KEEP_LIVE_INTERVAL = "sys.keep.alive.interval";

    public static final int KEEP_LIVE_INTERVAL_DEFAULT_VALUE = 300000;

    /**
     * downloading parameter
     */
    public static final String PARAM_SYS_DOWNLOADING_TOTAL = "sys.downloading.total";

    /**
     * uploading parameter
     */
    public static final String PARAM_SYS_UPLOADING_TOTAL = "sys.uploading.total";

    /**
     * synchronize data automatically
     */
    public static final String PARAM_SYS_SYNC_DATA_AUTO = "sys.sync.data.auto";

    /**
     * update version automatically
     */
    public static final String PARAM_SYS_UPDATING_VERSION_AUTO = "sys.updating.version.auto";


    /**
     * 监控类型：none & countly & umeng
     */
    public static final String PARAM_SYS_MONITOR_TYPE = "sys.monitor.type";

    /**
     * data版本号
     */
    public static final String PARAM_SYS_DATA_VERSION = "sys.data.version";

    /**
     * 自动上传时间间隔(单位：分钟)
     */
    public static final String PARAM_SYS_AUTO_UPLOAD_DATA_INTERVAL = "sys.auto.upload.data.interval";
    public static final int SYS_AUTO_UPLOAD_DATA_INTERVAL_DEFAULT = 3;

    public enum MonitorType {
        NONE(0, "none"),
        COUTLY(1, "countly"),
        UMENG(2, "umeng");

        MonitorType(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public static MonitorType toMonitorType(String name) {
            switch (name) {
                case "none":
                    return NONE;
                case "countly":
                    return COUTLY;
                case "umeng":
                    return UMENG;
                default:
                    return NONE;
            }
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private int index;
        private String name;
    }

    /**
     * 后台版本（1.0 或者 2.0）
     */
    public static final String PLATFORM_VERSION = "platform.version";

    /**
     * 支付时的公司编码
     */
    public static final String SYS_COMPANYCODE="sys.companycode";
    /**
     * 评价时拼接的编码
     */
    public static final String SYS_EVALUATECODE="sys.evaluatecode";

}
