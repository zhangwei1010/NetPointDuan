package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.net.point.R;
import com.net.point.base.BaseActivity;

import butterknife.ButterKnife;

//网点监管
public class NetworkSupervisionActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, NetworkSupervisionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_network_supervision);
        setContentTitle(R.string.network_supervision);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
