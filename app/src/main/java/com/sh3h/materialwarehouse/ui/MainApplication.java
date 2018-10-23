package com.sh3h.materialwarehouse.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.materialwarehouse.injection.component.ApplicationComponent;
import com.sh3h.materialwarehouse.injection.component.DaggerApplicationComponent;
import com.sh3h.materialwarehouse.injection.module.ApplicationModule;
import com.squareup.otto.Bus;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
/**
 * Created by libao on 2018/9/7.
 */

public class MainApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    List<Activity> mActivityList;

    @Inject
    Bus mBus;
    @Inject
    EventPosterHelper mEventPosterHelper;
    public MainApplication() {
        mApplicationComponent = null;
        mActivityList = null;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
        mApplicationComponent.context();
        mBus.register(this);
        mActivityList=new ArrayList<>();
    }

    public  static MainApplication get(Context context){
        return (MainApplication) context.getApplicationContext();
    }

    public void addActivity(Activity activity){
        if (mActivityList!=null){
            mActivityList.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if (mActivityList!=null){
            mActivityList.remove(activity);
        }
    }

    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mBus.unregister(this);
    }
}
