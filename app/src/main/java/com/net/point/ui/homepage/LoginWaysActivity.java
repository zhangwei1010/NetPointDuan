package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;

public class LoginWaysActivity extends BaseActivity {

    private TextView tvPostStation;
    private TextView tvDot;
    private TextView tvAllianceBusiness;

    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginWaysActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_denglu);
        setContentTitle(R.string.login);
        tvPostStation = findViewById(R.id.tvPostStation);
        tvDot = findViewById(R.id.tvDot);
        tvAllianceBusiness = findViewById(R.id.tv_AllianceBusiness);
    }

    public void OnPostStation(View view) {
        tvPostStation.setTextColor(getResources().getColor(R.color.main_button));
    }

    public void OnDot(View view) {
        tvDot.setTextColor(getResources().getColor(R.color.black));
    }

    public void OnAllianceBusiness(View view) {
        tvAllianceBusiness.setTextColor(getResources().getColor(R.color.white));
    }
}
