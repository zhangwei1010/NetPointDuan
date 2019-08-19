package com.net.point.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.net.point.CrashHandler;
import com.net.point.R;
import com.net.point.model.LoginModel;
import com.net.point.response.UserInforBean;
import com.net.point.ui.MainActivity;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SoftKeyboardUtil;
import com.net.point.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends Activity {

    @BindView(R.id.ll_bar)
    LinearLayout ll_bar;
    @BindView(R.id.ivNetApply)
    ImageView ivNetApply;
    @BindView(R.id.tvForgetPwd)
    TextView tvForgetPwd;
    @BindView(R.id.rl_login)
    RelativeLayout rl_login;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    Unbinder unbinder;

    private LoginModel model = new LoginModel();
    private ImageView iv_close;
    public static void start(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        setStatusBar();
        if (!TextUtils.isEmpty(SpUtils.getUserNumr())) et_username.setText(SpUtils.getUserNumr());
    }

    @OnClick({R.id.ivNetApply, R.id.tvForgetPwd, R.id.rl_login, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivNetApply:
                RegisterActivity.start(this);
                break;
            case R.id.tvForgetPwd:
                toast("此功能正在开发中");
                //FoundPasswordActivity.start(this);
                break;
            case R.id.rl_login:
                login();
                //MainActivity.start(this);
                break;
            case R.id.iv_close:
//                finish();
                //LoginWaysActivity.start(this);
                iv_close.setImageResource(R.drawable.in_review);
                break;
        }
    }

    private void login() {
        if (isEmpty()) return;
        model.login(et_username.getText().toString().trim(),
                et_pwd.getText().toString().trim(), e -> e.printStackTrace(), result -> {
                    if (result.isSuccess() & result.getData() != null) {
                        UserInforBean userInforBean = result.getData();
                        SoftKeyboardUtil.hideSoftKeyboard(this);
                        SpUtils.saveIncNumber(userInforBean.getIncnumber());
                        MainActivity.start(this);
                        //ErrorActivity.start(this);
                    } else {
                        toast(result.getMsg());
                    }
                });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(et_username.getText().toString())) {
            toast("请输入用户名");
            return true;
        }
        if (TextUtils.isEmpty(et_pwd.getText().toString())) {
            toast("请输入密码");
            return true;
        }
        return false;
    }

    private void toast(String qi) {
        Toast.makeText(LoginActivity.this, qi, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            //根据上面设置是否对状态栏单独设置颜色
            //getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏背景色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setParams();
    }

    private void setParams() {
        ll_bar.setVisibility(View.VISIBLE);
        //获取到状态栏的高度
        int statusHeight = AppUtils.getStatusBarHeight(this);
        //动态的设置隐藏布局的高度
        ViewGroup.LayoutParams params = ll_bar.getLayoutParams();
        params.height = statusHeight;
        ll_bar.setLayoutParams(params);
    }
}
