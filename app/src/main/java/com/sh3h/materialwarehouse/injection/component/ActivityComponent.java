package com.sh3h.materialwarehouse.injection.component;


import com.sh3h.materialwarehouse.injection.annotation.PerActivity;
import com.sh3h.materialwarehouse.injection.module.ActivityModule;
import com.sh3h.materialwarehouse.ui.MainActivity;
import com.sh3h.materialwarehouse.ui.applyconfim.ApplyConfimActivity;
import com.sh3h.materialwarehouse.ui.applyconfim.ApplyListActivity;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;
import com.sh3h.materialwarehouse.ui.login.LoginActivity;
import com.sh3h.materialwarehouse.ui.materialdistribution.MaterialDistributionActivity;
import com.sh3h.materialwarehouse.ui.materialdistribution.MaterialDistributionListActivity;
import com.sh3h.materialwarehouse.ui.outofstorage.OutOfStorageListActivity;
import com.sh3h.materialwarehouse.ui.supplierorder.SupplierOrderListActivity;

import dagger.Component;

//import com.sh3h.gastaskmanager.ui.test.TestSecurityActivity;

/**
 * This component inject dependencies to all Activities across the application
 * 生命周期跟Activity一样
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(ParentActivity parentActivity);
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity);

    void inject(ApplyConfimActivity applyConfimActivity);
    void inject(ApplyListActivity applyListActivity);
    void inject(MaterialDistributionListActivity materialDistributionActivity);
    void inject(MaterialDistributionActivity materialDistributionActivity);
    void inject(SupplierOrderListActivity supplierOrderListActivity);
    void inject(OutOfStorageListActivity outOfStorageListActivity);
}