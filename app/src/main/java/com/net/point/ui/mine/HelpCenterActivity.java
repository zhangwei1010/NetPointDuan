package com.net.point.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.net.point.R;
import com.net.point.base.BaseActivity;

public class HelpCenterActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, HelpCenterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_help_center);
        setContentTitle(R.string.help_center);
    }
}
