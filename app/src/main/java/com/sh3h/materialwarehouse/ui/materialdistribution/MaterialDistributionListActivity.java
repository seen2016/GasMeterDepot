package com.sh3h.materialwarehouse.ui.materialdistribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sh3h.materialwarehouse.R;
import com.sh3h.materialwarehouse.ui.base.ParentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MaterialDistributionListActivity extends ParentActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_distribution_list);
        ButterKnife.bind(this);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setTitle("物资分配列表");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.ln_one, R.id.ln_two})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ln_one:
                Intent intent=new Intent(this,MaterialDistributionActivity.class);
                intent.putExtra("state",0);
                startActivity(intent);
                break;
            case R.id.ln_two:
                Intent intent2=new Intent(this,MaterialDistributionActivity.class);
                intent2.putExtra("state",1);
                startActivity(intent2);
                break;
        }

    }
}
