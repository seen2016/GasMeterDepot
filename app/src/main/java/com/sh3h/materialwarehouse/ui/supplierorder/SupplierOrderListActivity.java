package com.sh3h.materialwarehouse.ui.supplierorder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupplierOrderListActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_receive)
    TextView tvReceive;
    @BindView(R.id.tv_rejection)
    TextView tvRejection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_order_list);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("供应商接单");
        setSupportActionBar(toolbar);
    }

    private String[] persons = {"原因一", "原因二", "原因三", "原因四"};
    boolean[] isChecked = {false, false, false, false};
    @OnClick({R.id.tv_receive, R.id.tv_rejection,R.id.tv_receive_two, R.id.tv_rejection_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_receive:
                Toast.makeText(this, "接收成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_rejection:
                new AlertDialog.Builder(this)
                        .setTitle("请选择拒收原因")
                        .setMultiChoiceItems(persons, isChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
            case R.id.tv_receive_two:
                Toast.makeText(this, "接收成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_rejection_two:
                new AlertDialog.Builder(this)
                        .setTitle("请选择拒收原因")
                        .setMultiChoiceItems(persons, isChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                break;
        }
    }
}
