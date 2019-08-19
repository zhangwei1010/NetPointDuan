package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.response.ContractInforBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//线上，线下两种支付方式
public class TwoPayWayActivity extends BaseActivity {

    @BindView(R.id.iv_online)
    ImageView iv_online;
    @BindView(R.id.iv_offline)
    ImageView iv_offline;

    public static void start(Context context) {
        context.startActivity(new Intent(context, TwoPayWayActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.pay_way);
        addContentView(R.layout.activity_two_pay_way);
        ButterKnife.bind(this);
    }

    private boolean onLineChecked = true;

    @OnClick({R.id.rl_onLine, R.id.rl_offLine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_onLine:
                onLineChecked = !onLineChecked;
                PayMoneyActivity.start(this);
                break;
            case R.id.rl_offLine:
                onLineChecked = !onLineChecked;
                PaymentAdvanceActivity.start(this);
                break;
        }
    }
}
