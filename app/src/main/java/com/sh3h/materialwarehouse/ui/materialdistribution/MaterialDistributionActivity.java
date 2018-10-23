package com.sh3h.materialwarehouse.ui.materialdistribution;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDistributionActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_distribution);
        ButterKnife.bind(this);

        initToolBar();

        state = getIntent().getIntExtra("state", -1);

        initData();
    }

    private void initData() {
        if (state == 0) {
            tvOne.setVisibility(View.VISIBLE);
            tvTwo.setVisibility(View.VISIBLE);
            tvThree.setVisibility(View.GONE);

            btnSubmit.setVisibility(View.VISIBLE);
        } else {
            tvOne.setVisibility(View.GONE);
            tvTwo.setVisibility(View.GONE);
            tvThree.setVisibility(View.VISIBLE);

            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("物资分配");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_add_order,R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_order:
                View orderView = LayoutInflater.from(this).inflate(R.layout.edit_order_content, null);
                new AlertDialog.Builder(this)
                        .setTitle("请输入订单信息")
                        .setView(orderView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            case R.id.btn_submit:
                finish();
                break;
        }
    }
}
