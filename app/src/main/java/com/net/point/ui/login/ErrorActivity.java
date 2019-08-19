package com.net.point.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.net.point.R;

public class ErrorActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, ErrorActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
    }
}
