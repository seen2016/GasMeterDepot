package com.sh3h.materialwarehouse.ui.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.injection.component.ActivityComponent;
import com.sh3h.materialwarehouse.injection.component.DaggerActivityComponent;
import com.sh3h.materialwarehouse.injection.module.ActivityModule;
import com.sh3h.materialwarehouse.ui.MainApplication;

/**
 *
 * Created by libao on 2018/9/7.
 */

public class BaseActivity extends AppCompatActivity {
    private ActivityComponent mActivityComponent;
    private boolean mStartAnimation;
    private boolean mEndAnimation;

    private ProgressDialog mProgressDialog;

    public BaseActivity(){
        mActivityComponent = null;
        mStartAnimation=true;
        mEndAnimation=true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setForwardAnimation();
        MainApplication.get(this).addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        setBackwardAnimation();
        MainApplication.get(this).removeActivity(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (mStartAnimation) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }
    public ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(MainApplication.get(this).getComponent())
                    .build();

        }
        return mActivityComponent;
    }
    @Override
    public void finish() {
        super.finish();

        if (mEndAnimation) {
            // 设置切换动画，从左边进入，右边退出
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

    public void setStartAnimation(boolean animation) {
        mStartAnimation = animation;
    }

    public void setEndAnimation(boolean animation) {
        mEndAnimation = animation;
    }

    public void setForwardAnimation() {
        setStartAnimation(true);
        setEndAnimation(false);
    }

    public void setBackwardAnimation() {
        setStartAnimation(false);
        setEndAnimation(true);
    }

    public void setBothAnimation() {
        setStartAnimation(true);
        setEndAnimation(true);
    }

    public void showProgress(int message) {
        hideProgress();
        mProgressDialog = ProgressDialog.show(this,
                getResources().getString(R.string.text_prompt),
                getResources().getString(message),
                false, false);
    }

    public void hideProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
