package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.net.point.R;
import com.net.point.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

//我的账单
public class MyBillActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyBillActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_my_bill);
        setContentTitle(R.string.my_bill);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_my_benefits, R.id.ll_my_penalty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_my_benefits:
                MyBenefitsActivity.start(this);
                break;
            case R.id.ll_my_penalty:
                MyPenaltyActivity.start(this, 2);
                break;
        }
    }
}
