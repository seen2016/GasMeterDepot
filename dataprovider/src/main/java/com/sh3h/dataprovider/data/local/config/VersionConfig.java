package com.sh3h.dataprovider.data.local.config;


public class VersionConfig extends BaseConfig {
    public static final String APP_VERSION = "app.version";
    public static final String DATA_VERSION = "data.version";
    public static int VERSION_DEFAULT = 1;

    public VersionConfig() {
        set(APP_VERSION, VERSION_DEFAULT);
        set(DATA_VERSION, VERSION_DEFAULT);
    }
}
