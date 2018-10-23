package com.sh3h.materialwarehouse.ui.applyconfim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ApplyListActivity extends ParentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_list);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("申领列表");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.ln_one, R.id.ln_two})
    public void onViewClicked(View view) {
        Intent intent=new Intent(this,ApplyConfimActivity.class);
        startActivity(intent);
    }
}
