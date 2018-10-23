package com.sh3h.materialwarehouse.ui.login;

import com.sh3h.dataprovider.data.BusEvent;
import com.sh3h.dataprovider.util.EventPosterHelper;
import com.sh3h.materialwarehouse.ui.base.ParentPresenter;
import com.sh3h.mobileutil.util.LogUtil;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 *
 * Created by libao on 2018/9/10.
 */

public class LoginPresenter extends ParentPresenter<LoginMvpView> {

    public static final String TAG="LoginPresenter";
    @Inject
    EventPosterHelper mEventPosterHelper;
    @Inject
    LoginPresenter(){

    }
    public void test(){
        LogUtil.i(TAG,"test--------");
        mEventPosterHelper.postEventSafely(new BusEvent.isLogin("登录成功喽",true));
//        getMvpView().loginSuccess();
    }
}
