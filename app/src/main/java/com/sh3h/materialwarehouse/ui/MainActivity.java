package com.sh3h.materialwarehouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.applyconfim.ApplyListActivity;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;
import com.sh3h.materialwarehouse.ui.materialdistribution.MaterialDistributionListActivity;
import com.sh3h.materialwarehouse.ui.outofstorage.OutOfStorageListActivity;
import com.sh3h.materialwarehouse.ui.supplierorder.SupplierOrderListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by libao on 2018/9/7.
 */

public class MainActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_apply_confim)
    Button btnApplyConfim;
    @BindView(R.id.btn_material_distribution)
    Button btnMaterialDistribution;
    @BindView(R.id.btn_supplier_order)
    Button btnSupplierOrder;
    @BindView(R.id.btn_out_of_storage)
    Button btnOutOfStorage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initToolBar();
    }

    private void initToolBar() {
        toolbar.setTitle("物资仓库");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_apply_confim, R.id.btn_material_distribution, R.id.btn_supplier_order, R.id.btn_out_of_storage})
    public void onViewClicked(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.btn_apply_confim:
                intent.setClass(this, ApplyListActivity.class);
                break;
            case R.id.btn_material_distribution:
                intent.setClass(this, MaterialDistributionListActivity.class);
                break;
            case R.id.btn_supplier_order:
                intent.setClass(this, SupplierOrderListActivity.class);
                break;
            case R.id.btn_out_of_storage:
                intent.setClass(this, OutOfStorageListActivity.class);
                break;
        }
        startActivity(intent);
    }
}
