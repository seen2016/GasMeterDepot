package com.sh3h.materialwarehouse.login;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgress(R.string.text_cancel);
    }
}
