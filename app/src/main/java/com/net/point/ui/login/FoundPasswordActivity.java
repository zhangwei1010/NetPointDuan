package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.net.point.R;
import com.net.point.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FoundPasswordActivity extends BaseActivity {

    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    Unbinder unbinder;

    public static void start(Context context) {
        context.startActivity(new Intent(context, FoundPasswordActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_found_password);
        setContentTitle(R.string.find_password);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                break;
        }
    }
}
