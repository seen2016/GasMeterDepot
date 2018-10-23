package com.sh3h.materialwarehouse.ui.outofstorage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OutOfStorageListActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ln_one)
    LinearLayout lnOne;
    @BindView(R.id.ln_two)
    LinearLayout lnTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_storage_list);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("出入库列表");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.ln_one, R.id.ln_two})
    public void onViewClicked(View view) {
        Intent intent=new Intent(this,OutOfStorageActivity.class);
        startActivity(intent);
    }
}
