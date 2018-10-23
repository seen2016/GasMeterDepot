package com.sh3h.materialwarehouse.injection.module;

import android.app.Activity;
import android.content.Context;

import com.sh3h.materialwarehouse.injection.annotation.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 *
 * Created by libao on 2018/9/10.
 */
@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return mActivity;
    }
}
