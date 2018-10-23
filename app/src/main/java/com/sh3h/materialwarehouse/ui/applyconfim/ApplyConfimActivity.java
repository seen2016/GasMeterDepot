package com.sh3h.materialwarehouse.ui.applyconfim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyConfimActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_apply_pass)
    Button btnApplyPass;
    @BindView(R.id.btn_apply_notpass)
    Button btnApplyNotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_confim);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("申领确认");
        setSupportActionBar(toolbar);
    }

    private String[] persons = {"原因一", "原因二", "原因三", "原因四"};
    boolean[] isChecked = {false, false, false, false};

    @OnClick({R.id.btn_apply_pass, R.id.btn_apply_notpass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply_pass:
                finish();
                break;
            case R.id.btn_apply_notpass:
                new AlertDialog.Builder(this)
                        .setTitle("请选择不通过原因")
                        .setMultiChoiceItems(persons, isChecked, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .show();
                break;
        }
    }
}
