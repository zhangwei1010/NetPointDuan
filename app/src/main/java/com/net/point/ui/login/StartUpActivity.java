package com.net.point.ui.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.net.point.R;
import com.net.point.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

//启动页
public class StartUpActivity extends Activity {

    private ImageView skip_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        skip_layout = findViewById(R.id.skip_layout);
        skip_layout.setImageResource(R.drawable.start_up);
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    LoginActivity.start(this);
                    finish();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
