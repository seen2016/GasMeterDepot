package com.sh3h.materialwarehouse.injection.component;

import android.content.Context;


import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.injection.annotation.ApplicationContext;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.materialwarehouse.injection.module.ApplicationModule;
import com.sh3h.materialwarehouse.ui.MainApplication;
import com.sh3h.materialwarehouse.util.PermissionsChecker;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Component;

/**
 * 生命周期跟Application一样
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainApplication mainApplication);

    @ApplicationContext
    Context context();

    EventPosterHelper eventPosterHelper();

    Bus eventBus();

    DataManager dataManager();

    PreferencesHelper preferencesHelper();

    ConfigHelper configHelper();

//     PermissionsChecker permissionsChecher();
}