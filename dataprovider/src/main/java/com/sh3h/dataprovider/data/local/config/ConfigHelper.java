package com.sh3h.dataprovider.data.local.config;


import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.local.preference.UserSession;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.dataprovider.util.ConstantUtil;
import com.sh3h.dataprovider.util.FileUtil;
import com.sh3h.mobileutil.util.TextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


@Singleton
public class ConfigHelper {
    public static final File STORAGE_PATH = Environment.getExternalStorageDirectory();
    /**
     * 根目录
     */
    public static final String FOLDER_ROOT = "sh3h/materialwarehouse";
    /**
     * 配置文件目录
     */
    public static final String FOLDER_CONFIG = FOLDER_ROOT + "/config";
    /**
     * 多用户配置文件目录
     */
    public static final String FOLDER_USER = FOLDER_ROOT + "/user";
    /**
     * 数据配置文件目录
     */
    public static final String FOLDER_DATA = FOLDER_ROOT + "/data";
    /**
     * 激活文件目录
     */
    public static final String FOLDER_KEY = FOLDER_ROOT + "/key";

    /**
     * 图片存储目录
     */
    public static final String FOLDER_IMAGES = FOLDER_ROOT + "/images";

    /**
     * 声音存储目录
     */
    public static final String FOLDER_SOUNDS = FOLDER_ROOT + "/sounds";
    /**
     * 更新文件存储目录
     */
    public static final String FOLDER_UPDATE = FOLDER_ROOT + "/update";

    /**
     * apk文件目录
     */
    public static final String FOLDER_APKS = FOLDER_ROOT + "/apks";

    /**
     * www文件存储目录
     */
    public static final String FOLDER_WWW = FOLDER_ROOT + "/www";

    /**
     * 日志文件存储目录
     */
    public static final String FOLDER_LOG = FOLDER_ROOT + "/log";

    /**
     * 用户配置文件名
     */
    public static final String FILE_USER_CONFIG = "user.properties";

    /**
     * 版本记录号
     */
    public static final String FILE_VERSION_UPDATE = "versions.properties";
    /**
     * 系统参数文件名
     */
    public static final String FILE_SYSTEM_CONFIG = "system.properties";

    /**
     * Map参数文件名
     */
    public static final String FILE_MAP_CONFIG = "map.properties";

    /**
     * 下载的配置文件
     */
    public static final String FILE_OTHER_CONFIG = "other.properties";
    /**
     * 数据库文件名
     */
    public static final String DB_NAME = "main.cbj";
    /**
     * 激活文件名
     */
    public static final String FILE_KEY_CONFIG = "key.properties";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final SystemConfig mSystemConfig;
    private final MapConfig mMapConfig;
    private final VersionConfig mVersionConfig;
    private final KeyConfig mKeyConfig;
    private final UserConfig mUserConfig;
    private final OtherConfig mOtherConfig;

    @Inject
    public ConfigHelper(@ApplicationContext Context context,
                        PreferencesHelper preferencesHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mSystemConfig = new SystemConfig();
        mMapConfig = new MapConfig();
        mVersionConfig = new VersionConfig();
        mKeyConfig = new KeyConfig();
        mUserConfig = new UserConfig();
        mOtherConfig = new OtherConfig();
    }

    public Observable<Boolean> initDefaultConfigs() {
        return Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> subscriber) throws Exception {
                subscriber.onNext(initDefaultConfigFiles());
                subscriber.onComplete();
            }
        });
    }

    public Observable<Void> clearAndInitConfigs(final boolean isRestoringFactory) {
        return Observable.create(subscriber -> {
            if ((!clearConfigFiles(isRestoringFactory)) || (!initDefaultConfigFiles())) {
                subscriber.onError(new Throwable("clearConfigs is failure"));
            } else {
                subscriber.onComplete();
            }
        });
    }

    public Observable<Void> initUserConfig(final int userId) {
        return Observable.create(subscriber -> {
            if (!initUserConfigFile(userId)) {
                subscriber.onError(new Throwable("initDefaultConfigFiles is failure"));
            } else {
                subscriber.onComplete();
            }
        });
    }

    /**
     * initialize configure files
     *
     * @return true: success
     */
    private boolean initDefaultConfigFiles() {
        try {
            // config
            File configDir = new File(STORAGE_PATH, FOLDER_CONFIG);
            if (!configDir.exists()) {
                // 创建 FOLDER_CONFIG
                configDir.mkdirs();
            }

            // 遍历 assets/config 文件夹，在sd卡不存在则复制
            AssetManager assetManager = mContext.getAssets();
            String[] configFiles = assetManager.list("config");
            if (configFiles.length > 0) {
                for (String fileName : configFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_CONFIG + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("config/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // data
            File dataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            String[] dataFiles = assetManager.list("data");
            if (dataFiles.length > 0) {
                for (String fileName : dataFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_DATA + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("data/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // key
            File keyDir = new File(STORAGE_PATH, FOLDER_KEY);
            if (!keyDir.exists()) {
                keyDir.mkdirs();
            }

            String[] keyFiles = assetManager.list("key");
            if (keyFiles.length > 0) {
                for (String fileName : keyFiles) {
                    File outputFile = new File(STORAGE_PATH, FOLDER_KEY + "/" + fileName);
                    if (!outputFile.exists()) {
                        InputStream is = assetManager.open("key/" + fileName);
                        FileOutputStream fos = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[256];
                        for (int count = is.read(buffer); count > 0; ) {
                            fos.write(buffer, 0, count);
                            count = is.read(buffer);
                        }

                        fos.flush();
                        fos.close();
                        is.close();
                    }
                }
            }

            // image
            File imagesDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (!imagesDir.exists()) {
                imagesDir.mkdirs();
            }

            // sound
            File soundsDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (!soundsDir.exists()) {
                soundsDir.mkdirs();
            }

            // update
            File updateDir = new File(STORAGE_PATH, FOLDER_UPDATE);
            if (!updateDir.exists()) {
                updateDir.mkdirs();
            }

            File apksDir = new File(STORAGE_PATH, FOLDER_APKS);
            if (!apksDir.exists()) {
                apksDir.mkdirs();
            }

            // log
            File logDir = new File(STORAGE_PATH, FOLDER_LOG);
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            // user
            File userDir = new File(STORAGE_PATH, FOLDER_USER);
            if (!userDir.exists()) {
                userDir.mkdirs();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * clear all configures
     */
    private boolean clearConfigFiles(boolean isRestoringFactory) {
        try {
            if (isRestoringFactory) {
                // apks
                File apksConfigDir = new File(STORAGE_PATH, FOLDER_APKS);
                if (apksConfigDir.exists()) {
                    FileUtil.deleteFile(apksConfigDir);
                }

                // configure
                File oldConfigDir = new File(STORAGE_PATH, FOLDER_CONFIG);
                if (oldConfigDir.exists()) {
                    FileUtil.deleteFile(oldConfigDir);
                }

                // update
                File updateConfigDir = new File(STORAGE_PATH, FOLDER_UPDATE);
                if (updateConfigDir.exists()) {
                    FileUtil.deleteFile(updateConfigDir);
                }

                // user
                File oldUserConfigDir = new File(STORAGE_PATH, FOLDER_USER);
                if (oldUserConfigDir.exists()) {
                    FileUtil.deleteFile(oldUserConfigDir);
                }

                // key
                File keyDir = new File(STORAGE_PATH, FOLDER_KEY);
                if (!keyDir.exists()) {
                    FileUtil.deleteFile(keyDir);
                }
            }

            // data
            File oldDataDir = new File(STORAGE_PATH, FOLDER_DATA);
            if (oldDataDir.exists()) {
                FileUtil.deleteFile(oldDataDir);
            }

            // log
            File oldLogDir = new File(STORAGE_PATH, FOLDER_LOG);
            if (oldLogDir.exists()) {
                FileUtil.deleteFile(oldLogDir);
            }

            // image
            File oldImageDir = new File(STORAGE_PATH, FOLDER_IMAGES);
            if (oldImageDir.exists()) {
                FileUtil.deleteFile(oldImageDir);
            }

            // sound
            File oldSoundDir = new File(STORAGE_PATH, FOLDER_SOUNDS);
            if (oldSoundDir.exists()) {
                FileUtil.deleteFile(oldSoundDir);
            }

            // www
            File oldWWWDir = new File(STORAGE_PATH, FOLDER_WWW);
            if (oldWWWDir.exists()) {
                FileUtil.deleteFile(oldWWWDir);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean initUserConfigFile(int userId) {
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userId);
        File file = new File(dir, FILE_USER_CONFIG);

        if (!file.exists()) {
            dir.mkdirs();
            mUserConfig.loadDefaultConfig();
            mUserConfig.writeProperties(file.getPath());
        } else {
            mUserConfig.readProperties(file.getPath());
        }

        return true;
    }

    /**
     * @return
     */
    public File getConfigFolderPath() {
        return new File(STORAGE_PATH, FOLDER_CONFIG);
    }

    /**
     * get the path of system.properties
     *
     * @return file path
     */
    public File getSystemConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_SYSTEM_CONFIG);
    }

    /**
     * @return
     */
    public File getMapConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_MAP_CONFIG);
    }

    /**
     * @return
     */
    public File getOtherConfigFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_CONFIG);
        return new File(dir, FILE_OTHER_CONFIG);
    }

    /**
     * @return
     */
    public File getUserConfigFilePath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        File dir = new File(STORAGE_PATH, FOLDER_USER + "/" + userSession.getUserId());
        return new File(dir, FILE_USER_CONFIG);
    }

    /**
     * get the path of log file
     *
     * @return file path
     */
    public File getLogFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_LOG);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = String.format("log-%s.txt", TextUtil.format(new Date(), TextUtil.FORMAT_DATE));
        return new File(dir, name);
    }

    /**
     * @return
     */
    public File getKeyFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_KEY);
        return new File(dir, FILE_KEY_CONFIG);
    }

    /**
     * @return
     */
    @Deprecated
    public File getVersionFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_UPDATE);
        return new File(dir, FILE_VERSION_UPDATE);
    }

    /**
     * @return
     */
    public File getDBFilePath() {
        File dir = new File(STORAGE_PATH, FOLDER_DATA);
        return new File(dir, DB_NAME);
    }

    /**
     * @return
     */
    public File getImageFolderPath() {
        return new File(STORAGE_PATH, FOLDER_IMAGES);
    }

    public File getSoundFolderPath() {
        return new File(STORAGE_PATH, FOLDER_SOUNDS);
    }

    /**
     * @return
     */
    public File getUpdateFolderPath() {
        return new File(STORAGE_PATH, FOLDER_UPDATE);
    }

    /**
     * @return
     */
    public File getUpdateFolderPathByUser() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return new File(STORAGE_PATH, FOLDER_UPDATE + "/" + userSession.getUserId());
    }

    /**
     * @return
     */
    public File getApksFolderPath() {
        UserSession userSession = mPreferencesHelper.getUserSession();
        return new File(STORAGE_PATH, FOLDER_APKS + "/" + userSession.getUserId());
    }

    /**
     * @return
     */
    public synchronized SystemConfig getSystemConfig() {
        if (!mSystemConfig.isRead()) {
            mSystemConfig.readProperties(getSystemConfigFilePath().getPath());
        }
        return mSystemConfig;
    }

    /**
     * @return
     */
    public MapConfig getMapConfig() {
        if (!mMapConfig.isRead()) {
            mMapConfig.readProperties(getMapConfigFilePath().getPath());
        }

        return mMapConfig;
    }

    /**
     * @return
     */
    public synchronized UserConfig getUserConfig() {
        if (!mUserConfig.isRead()) {
            mUserConfig.readProperties(getUserConfigFilePath().getPath());
        }
        return mUserConfig;
    }

    /**
     * @return
     */
    public KeyConfig getKeyConfig() {
        if (!mKeyConfig.isRead()) {
            mKeyConfig.readProperties(getKeyFilePath().getPath());
        }
        return mKeyConfig;
    }

    /**
     * @return
     */
    public synchronized VersionConfig getVersionConfig() {
        if (!mVersionConfig.isRead()) {
            mVersionConfig.readProperties(getVersionFilePath().getPath());
        }

        return mVersionConfig;
    }

    /**
     * @return
     */
    public synchronized OtherConfig getOtherConfig() {
        if (!mOtherConfig.isRead()) {
            mOtherConfig.readProperties(getOtherConfigFilePath().getPath());
        }

        return mOtherConfig;
    }

    public String getKey() {
        return getKeyConfig().getString(KeyConfig.PARAM_KEY);
    }

    public boolean setKey(String key) {
        getKeyConfig().set(KeyConfig.PARAM_KEY, key);
        return getKeyConfig().writeProperties(getKeyFilePath().getPath());
    }

    public boolean isGridStyle() {
        UserConfig userConfig = getUserConfig();
        int style = userConfig.getInteger(UserConfig.PARAM_SYS_HOME_STYLE, UserConfig.HOME_STYLE_GRID);
        return style == UserConfig.HOME_STYLE_GRID;
    }

    public String getUserChangYong() {
        UserConfig userConfig = getUserConfig();
        return userConfig.getString(UserConfig.PARAM_CB_DEFAULT_CYCBZT);
    }

    public Observable<Boolean> saveGridStyle(boolean isGrid) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_HOME_STYLE,
                isGrid ? UserConfig.HOME_STYLE_GRID : UserConfig.HOME_STYLE_LIST);
        return Observable.create(subscriber -> {
            boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    @Deprecated
    public Observable<Boolean> saveUserChangYong(String zhuangTaiBM) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_CB_DEFAULT_CYCBZT, zhuangTaiBM);

        return Observable.create(subscriber -> {
            boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> savePhotoQuality(boolean isNormal) {
        UserConfig userConfig = getUserConfig();
        userConfig.set(UserConfig.PARAM_SYS_QUALITY_PHOTO,
                isNormal ? UserConfig.QUALITY_NORMAL : UserConfig.QUALITY_HIGH);
        return Observable.create(subscriber -> {
            boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    @Deprecated
    public boolean saveDataVersion(int version) {
        VersionConfig versionConfig = getVersionConfig();
        versionConfig.set(VersionConfig.DATA_VERSION, version);
        return versionConfig.writeProperties(getVersionFilePath().getPath());
    }

    public boolean isUpdateVersion() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO, false);
    }

    public String getBaseUri() {
        return getSystemConfig().getString(SystemConfig.PARAM_SERVER_BASE_URI);
    }

    public String getReverseBaseUri() {
        return getSystemConfig().getString(SystemConfig.PARAM_SERVER_REVERSE_BASE_URI);
    }

    public String getBrokerUrl() {
        return getSystemConfig().getString(SystemConfig.PARAM_BROKER_URL);
    }

    public String getReverseBrokerUrl() {
        return getSystemConfig().getString(SystemConfig.PARAM_BROKER_REVERSE_URL);
    }

    public boolean isUsingReservedAddress() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getBoolean(SystemConfig.PARAM_USING_RESERVED_ADDRESS, false);
    }

    public int getKeepAliveInterval() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getInteger(SystemConfig.PARAM_KEEP_LIVE_INTERVAL,
                SystemConfig.KEEP_LIVE_INTERVAL_DEFAULT_VALUE);
    }

    public SystemConfig.MonitorType getMonitorType() {
        SystemConfig systemConfig = getSystemConfig();
        String name = systemConfig.getString(SystemConfig.PARAM_SYS_MONITOR_TYPE);
        return SystemConfig.MonitorType.toMonitorType(name);
    }

    public int getDataVersion() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getInteger(SystemConfig.PARAM_SYS_DATA_VERSION, 0);
    }

    public String getCountlyUri() {
        SystemConfig systemConfig = getSystemConfig();
        return systemConfig.getString(SystemConfig.PARAM_COUNTLY_SERVER_URI);
    }

    public Observable<Boolean> saveUpdateVersion(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_UPDATING_VERSION_AUTO,
                flag);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> saveSyncData(boolean flag) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_SYNC_DATA_AUTO,
                flag);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> saveKeepAliveInterval(int keepAliveInterval) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_KEEP_LIVE_INTERVAL,
                keepAliveInterval);

        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public String getReadingDate(boolean isStart) {
        OtherConfig otherConfig = getOtherConfig();
        int date;
        if (isStart) {
            date = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                    OtherConfig.READING_READING_START_DATE);
        } else {
            date = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                    OtherConfig.READING_READING_END_DATE);
        }

        return String.valueOf(date);
    }

    public boolean isCurrentReadingDateValid() {
        OtherConfig otherConfig = getOtherConfig();
        int startDate = otherConfig.getInteger(OtherConfig.READING_START_DATE,
                OtherConfig.READING_READING_START_DATE);
        int endDate = otherConfig.getInteger(OtherConfig.READING_END_DATE,
                OtherConfig.READING_READING_END_DATE);
        if (startDate > endDate) {
            return true;
        }

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DATE);//获取日
        return (startDate <= day) && (day <= endDate);
    }

    public boolean isDownloadingTotal() {
        return getSystemConfig().getBoolean(SystemConfig.PARAM_SYS_DOWNLOADING_TOTAL, false);
    }

    public double getDefaultLongitude() {
        return getMapConfig().getDouble(MapConfig.PARAM_LONGITUDE_DEFAULT, 0);
    }

    public double getDefaultLatitude() {
        return getMapConfig().getDouble(MapConfig.PARAM_LATITUDE_DEFAULT, 0);
    }

    public Observable<Boolean> saveCountlyUri(String countlyUri) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_COUNTLY_SERVER_URI, countlyUri);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public String getGpsType() {
        return getSystemConfig().getString(SystemConfig.PARAM_SYST_GPS_TYPE);
    }

    public Observable<Boolean> saveGpsType(String typeListValue) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYST_GPS_TYPE, typeListValue);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> saveMonitorType(String monitorTypeValue) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SYS_MONITOR_TYPE, monitorTypeValue);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public String getRoles() {
        return mPreferencesHelper.getUserSession().get_roles();
    }

    public Observable<Boolean> clearHistory() {
        return Observable.create(subscriber -> {
            if (subscriber.isDisposed()) {
                return;
            }

            mPreferencesHelper.getUserSession().setClearing(true);
            boolean b = mPreferencesHelper.saveUserSession();
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> restoreFactory() {
        return Observable.create(subscriber -> {
            if (subscriber.isDisposed()) {
                return;
            }

            mPreferencesHelper.getUserSession().setRecovery(true);
            boolean b = mPreferencesHelper.saveUserSession();
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> clearAccount() {
        return Observable.create(subscriber -> {
            if (subscriber.isDisposed()) {
                return;
            }

            UserSession userSession = mPreferencesHelper.getUserSession();
            userSession.setAccount("");
            userSession.set_password("");
            boolean b = mPreferencesHelper.saveUserSession();
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    public Observable<Boolean> saveNetWorkSetting(String baseUri, String reservedBaseUri,
                                                  String brokeUrl, String reservedBrokeUrl, boolean isUsingReservedAddress) {
        SystemConfig systemConfig = getSystemConfig();
        systemConfig.set(SystemConfig.PARAM_SERVER_BASE_URI,
                baseUri);
        systemConfig.set(SystemConfig.PARAM_SERVER_REVERSE_BASE_URI,
                reservedBaseUri);
        systemConfig.set(SystemConfig.PARAM_BROKER_URL,
                brokeUrl);
        systemConfig.set(SystemConfig.PARAM_BROKER_REVERSE_URL,
                reservedBrokeUrl);
        systemConfig.set(SystemConfig.PARAM_USING_RESERVED_ADDRESS,
                isUsingReservedAddress);
        return Observable.create(subscriber -> {
            boolean b = getSystemConfig().writeProperties(getSystemConfigFilePath().getPath());
            subscriber.onNext(b);
            subscriber.onComplete();
        });
    }

    /**
     * 图片质量保存
     *
     * @param str
     * @return
     */
    public Observable<Boolean> savePicQuality(String str) {
        int integer;
        try {
            integer = Integer.parseInt(str);
            if (UserConfig.QUALITY_NORMAL != integer && UserConfig.QUALITY_HIGH != integer) {
                return Observable.error(new Exception("data is error"));
            }
            UserConfig userConfig = getUserConfig();
            userConfig.set(UserConfig.PARAM_SYS_QUALITY_PHOTO,
                    integer);
            return Observable.create(subscriber -> {
                boolean b = getUserConfig().writeProperties(getUserConfigFilePath().getPath());
                subscriber.onNext(b);
                subscriber.onComplete();
            });
        } catch (Exception ex) {
            return Observable.error(new Exception("data is error"));
        }
    }

    public long getDataTermOfValidity() {
        SystemConfig systemConfig = getSystemConfig();
        int termOfValidity = systemConfig.getInteger(SystemConfig.DATA_TERM_OF_VALIDITY, ConstantUtil.DATA_TERM_OF_VALIDITY);
        //return 0L;
        return termOfValidity * ConstantUtil.ONE_DAY_MSEC;
    }

    public boolean isPhotoQualityNormal() {
        UserConfig userConfig = getUserConfig();
        int quality = userConfig.getInteger(UserConfig.PARAM_SYS_QUALITY_PHOTO, UserConfig.QUALITY_NORMAL);
        return quality == UserConfig.QUALITY_NORMAL;
    }

    public Observable<? extends Boolean> deleteAllPicture() {
        return Observable.create(subscriber -> {
                    FileUtil.deleteFile(getImageFolderPath());
                    subscriber.onNext(true);
                    subscriber.onComplete();
                }
        );
    }

    public Observable<? extends Boolean> deleteLog() {
        return Observable.create(subscriber -> {
            FileUtil.deleteFile(getLogFilePath());
            subscriber.onNext(true);
            subscriber.onComplete();
        });
    }

    public int getAutoUploadDataInterval() {
        // 单位：分钟
        return getSystemConfig().getInteger(SystemConfig.PARAM_SYS_AUTO_UPLOAD_DATA_INTERVAL,
                SystemConfig.SYS_AUTO_UPLOAD_DATA_INTERVAL_DEFAULT);
    }
}
