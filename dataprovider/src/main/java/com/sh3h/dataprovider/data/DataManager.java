package com.sh3h.dataprovider.data;

import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.data.local.preference.UserSession;
import com.sh3h.mobileutil.util.LogUtil;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by libao on 2018/9/14.
 */
@Singleton
public class DataManager {
    private static final String TAG = "DataManager";

    private final PreferencesHelper mPreferencesHelper;
    private final ConfigHelper mConfigHelper;

    @Inject
    public DataManager(PreferencesHelper preferencesHelper,
                       ConfigHelper configHelper) {
        mPreferencesHelper = preferencesHelper;
        mConfigHelper = configHelper;
    }



    /**
     * initialize logger file
     */
    public void initLogger() {
        LogUtil.initLogger(mConfigHelper.getLogFilePath().getPath());
//        mDbHelper.init();
    }

    /**
     * close the logger file
     */
    public void closeLogger() {
        LogUtil.closeLogger();
    }

    public File getImageFolderPath() {
        return mConfigHelper.getImageFolderPath();
    }

    public File getSoundFolderPath() {
        return mConfigHelper.getSoundFolderPath();
    }

    public Observable<Boolean> savePicQuality(String str) {
        return mConfigHelper.savePicQuality(str);
    }
}
