package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//开通小号
public class OpenTrumpetActivity extends BaseActivity {

    private HomeModel model = new HomeModel();

    @BindView(R.id.tv_get_vertication)
    TextView tv_get_vertication;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.ed_confirm_password)
    EditText ed_confirm_password;
    @BindView(R.id.ed_verticition)
    EditText ed_verticition;

    public static void start(Context context) {
        context.startActivity(new Intent(context, OpenTrumpetActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_open_trumpet);
        setContentTitle(R.string.open_trumpet);
        ButterKnife.bind(this);
        Timer timer = new Timer();
    }

    @OnClick({R.id.tv_get_vertication, R.id.rl_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_vertication:
                timer.start();
                break;
            case R.id.rl_login:
                openTrumpet();
                break;
        }
    }

    private void openTrumpet() {
        String userId = SpUtils.getUserId();
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        String mobile = et_mobile.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            toast("请填写手机号");
            return;
        }
        String password = ed_confirm_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toast("请设置密码");
            return;
        }
        String smsCode = ed_verticition.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            toast("请输入验证码");
            return;
        }
        model.openTrumpet(userId, mobile, password, smsCode, e -> {
            toast(e.getMessage());
            e.printStackTrace();
        }, result -> {
            if (result.isSuccess()) {
                finish();
                if (result.getData() != null) {
                }
            }
            toast(result.getMsg());
        });
    }

    /**
     * 倒计时60秒，一次1秒
     */
    private CountDownTimer timer = new CountDownTimer(30 * 60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm分ss秒");
            //String time = AppUtils.timeParse(millisUntilFinished);
            AppUtils.setTexts(tv_get_vertication, dateFormat.format(new Date(millisUntilFinished)));
        }

        @Override
        public void onFinish() {
            AppUtils.setTexts(tv_get_vertication, "重新获取");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
