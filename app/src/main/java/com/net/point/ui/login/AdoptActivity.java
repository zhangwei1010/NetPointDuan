package com.net.point.ui.login;

import android.os.Bundle;

import com.net.point.R;
import com.net.point.base.BaseActivity;
//代理通过
public class AdoptActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_adopt);
    }
}
