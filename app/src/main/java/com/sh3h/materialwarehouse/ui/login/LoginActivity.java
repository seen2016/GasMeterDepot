package com.sh3h.materialwarehouse.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sh3h.dataprovider.data.BusEvent;
import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.MainActivity;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends ParentActivity implements LoginMvpView{

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    Bus eventBus;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getActivityComponent().inject(this);
        loginPresenter.attachView(this);
        eventBus.register(this);
        ButterKnife.bind(this);
        LogUtil.closeLogger();
        startInit();
    }
    public static final String TAG="LoginActivity";
    @OnClick({ R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                LogUtil.i(TAG,"btn_login--------");
               loginPresenter.test();
                break;
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginPresenter.detachView();
        eventBus.unregister(this);
    }
    @Subscribe
    public void isLogin(BusEvent.isLogin result){
       if( result.isLogin){
           Intent intent=new Intent(this, MainActivity.class);
           startActivity(intent);
           Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
       }
    }
}
