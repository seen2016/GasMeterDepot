package com.sh3h.materialwarehouse.ui.base;



public class ParentPresenter<P extends MvpView> extends BasePresenter<P> {
    @Override
    public void attachView(P mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }
}
