package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.LoginModel;
import com.net.point.response.UseCountBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessfulSignActivity extends BaseActivity {

    @BindView(R.id.et_input_username)
    EditText et_input_username;
    @BindView(R.id.ed_set_password)
    EditText ed_set_password;
    @BindView(R.id.ed_confirm_password)
    EditText ed_confirm_password;
    @BindView(R.id.tv_successful_sign)
    TextView tv_successful_sign;
    private UseCountBean data;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SuccessfulSignActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.successful_sign);
        addContentView(R.layout.activity_successful_sign);
        ButterKnife.bind(this);
        getCount();
        AppUtils.setTexts(tv_successful_sign, "恭喜您成为" + SpUtils.getSignName() + "级代理！");
    }

    private void getCount() {
        String userId = SpUtils.getUserId();
        model.getCount(userId, e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                data = result.getData();
                et_input_username.setText(data.userNum);
            }
        });
    }

    private LoginModel model = new LoginModel();

    @OnClick({R.id.rl_login})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_login:
                saveSignCount();
                break;
        }
    }

    private void saveSignCount() {
        String userId = SpUtils.getUserId();
        String signName = et_input_username.getText().toString().trim();
        String setPwd = ed_set_password.getText().toString().trim();
        String confirmPwd = ed_confirm_password.getText().toString().trim();
        if (isEmpty(signName, setPwd, confirmPwd)) return;
        showProgressDialog();
        model.uploadSignCount(userId, signName, setPwd, e -> {
            e.printStackTrace();
            hideProgressDialog();
        }, result -> {
            if (result.isSuccess()) {
                SpUtils.saveUserNum(data.userNum);
                LoginActivity.start(this);
            }
        });
    }

    private boolean isEmpty(String signName, String setPwd, String confirmPwd) {
        if (TextUtils.isEmpty(setPwd)) {
            toast("请输入密码");
            return true;
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            toast("请输入确认密码");
            return true;
        }
        if (!TextUtils.equals(setPwd, confirmPwd)) {
            toast("两次输入密码不一致");
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
