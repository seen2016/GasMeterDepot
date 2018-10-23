package com.sh3h.materialwarehouse.ui.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.sh3h.dataprovider.data.DataManager;
import com.sh3h.dataprovider.data.local.config.ConfigHelper;
import com.sh3h.dataprovider.data.local.preference.PreferencesHelper;
import com.sh3h.dataprovider.util.ConstantUtil;
import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.MainApplication;
import com.sh3h.materialwarehouse.util.PermissionsChecker;
import com.sh3h.mobileutil.util.TextUtil;
import com.squareup.otto.Bus;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by libao on 2018/9/7.
 */

public class ParentActivity extends BaseActivity implements SwipeBackActivityBase {
    private static final String TAG = "ParentActivity";
    protected static final String TEAM = "team";

    @Inject
    PermissionsChecker permissionsChecker;

    @Inject
    ConfigHelper configHelper;

    @Inject
    PreferencesHelper preferencesHelper;

    @Inject
    DataManager dataManager;

    @Inject
    Bus bus;
    private SwipeBackActivityHelper mHelper;
    protected boolean isInitEnd;

    private static boolean isConfigInit = false;
    private static boolean isDataCleaned = false;
    //初始化到那个步骤
    protected static int initStep = ConstantUtil.InitStep.CHECK_PERMISSION;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.VIBRATE
    };
    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        getActivityComponent().inject(this);
        if (bundle != null) {
            initStep = bundle.getInt(ConstantUtil.InitStep.INIT_STEP);
        }
        setBothAnimation();
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantUtil.InitStep.INIT_STEP, initStep);
    }
    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean b) {
        getSwipeBackLayout().setEnableGesture(b);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityFromTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if ((v == null) && (mHelper != null)) {
            return mHelper.findViewById(id);
        }
        return v;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    protected void startInit() {
        this.isInitEnd = false;
        init(ConstantUtil.InitStep.CHECK_PERMISSION);
    }

    protected void endInit() {
        isInitEnd = true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ConstantUtil.RequestCode.CHECK_PERMISSION
                && hasAllPermissionsGranted(grantResults)) {
            init(ConstantUtil.InitStep.INIT_CONFIG);
        } else {
            popupPermissionDialog();
        }
    }
    /**
     * @param step
     */
    private void init(int step) {
        initStep = step;
        switch (initStep) {
            case ConstantUtil.InitStep.CHECK_PERMISSION:
                checkPermissions();
                break;
            case ConstantUtil.InitStep.INIT_CONFIG:
                initConfig();
                break;
            default:
                endInit();
                break;
        }
    }

    private void checkPermissions() {
        Log.i(TAG, "---checkPermissions---");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionsChecker.lacksPermissions(PERMISSIONS)) {
            requestPermissions(PERMISSIONS, ConstantUtil.RequestCode.CHECK_PERMISSION);
        } else {
            init(ConstantUtil.InitStep.INIT_CONFIG);
        }
    }

    //是否已经取得所有权限
    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    //没有赋予相关权限，退出程序
    private void popupPermissionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_info)
                .setMessage(R.string.text_lack_permissions)
                .setCancelable(false)
                .setPositiveButton(R.string.text_ok, (dialogInterface, i) ->
                        System.exit(0)
                )
                .setNegativeButton(R.string.text_cancel, (dialogInterface, i) ->
                        System.exit(0)
                )
                .create()
                .show();
    }

    /**
     *
     */
    private void initConfig() {
        Log.i(TAG, "---initConfig---");
        if (isConfigInit) {
            init(ConstantUtil.InitStep.INIT_OVER);
            return;
        }

        configHelper.initDefaultConfigs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            Log.i(TAG, "---initConfig onCompleted---");
                            isConfigInit = true;
                            dataManager.initLogger();
                            initConfigEnd();
                            init(ConstantUtil.InitStep.INIT_OVER);
                        }else {
                            isConfigInit = false;
                            System.exit(0);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, "---initConfig onError---" + throwable.getMessage());
                        System.exit(0);
                    }
                });
    }

    //初始化结束后加载数据
    protected void initConfigEnd() {

    }


}
