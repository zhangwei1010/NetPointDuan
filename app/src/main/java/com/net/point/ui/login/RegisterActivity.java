package com.net.point.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.net.point.R;
import com.net.point.model.LoginModel;
import com.net.point.response.PersonalBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends Activity {

    @BindView(R.id.ll_bar)
    LinearLayout ll_bar;
    @BindView(R.id.et_use_name)
    EditText et_use_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_at_place)
    EditText et_at_place;
    @BindView(R.id.rl_next)
    RelativeLayout rl_next;

    public static void start(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setStatusBar();
    }

    @OnClick({R.id.rl_next, R.id.rl_cancel, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_next:
                uploadUserInfor();
                break;
            case R.id.rl_cancel:
                finish();
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    private LoginModel model = new LoginModel();

    private void uploadUserInfor() {
        /**
         *   "code": 1,
         *     "msg": "保存用户信息成功！",
         *       "code": 2,
         *     "msg": "网签了，但未支付",
         *       "code": 3,
         *     "msg": "支付成功，正在审核！",
         *       "code": 4,
         *     "msg": "审核通过！",
         *
         */
        if (isEmpty()) return;
        String at_place = "";
        if (!TextUtils.isEmpty(et_at_place.getText().toString())) {
            at_place = et_at_place.getText().toString();
        }
        showProgressDialog();
        String name = et_use_name.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        model.uploadUserInfor(name, password, at_place
                , e -> {
                    e.printStackTrace();
                    hideProgressDialog();
                }, result -> {
                    hideProgressDialog();
                    if (result.isSuccess() && result.getData() != null) {
                        PersonalBean dataBean = result.getData();
                        if (dataBean != null) {
                            SpUtils.saveUserId(dataBean.getUserId());
                            SpUtils.saveSignNetId(dataBean.getNetSignId());
                        }
                        switch (result.getCode()) {
                            case 1:
                                SpUtils.saveUserId(dataBean.getUserId());
                                NetPointApplyActivity.start(this);
                                break;
                            case 2:
                                SpUtils.saveContractFree(dataBean.getPayMoney());
                                TwoPayWayActivity.start(this);
                                break;
                            case 3:
                                InReViewActivity.start(this, "1");
                                finish();
                                break;
                            case 4:
                                InReViewActivity.start(this, "2");
                                finish();
                                break;
                        }
                    }
                }
        );
    }

    private boolean isEmpty() {
        boolean isNull = false;
        if (TextUtils.isEmpty(et_use_name.getText().toString())) {
            toast("联系人不能为空");
            isNull = true;
        }
        if (TextUtils.isEmpty(et_password.getText().toString())) {
            toast("联系方式不能为空");
            isNull = true;
        }
        return isNull;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            //根据上面设置是否对状态栏单独设置颜色
            // getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏背景色
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

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private ProgressDialog dialog;

    protected void showProgressDialog() {
        if (dialog == null) {
            dialog = ProgressDialog.show(this, "提示", "正在加载中");
        }
        dialog.show();
    }

    protected void hideProgressDialog() {
        if (dialog == null) return;
        if (dialog.isShowing()) dialog.dismiss();
        dialog = null;
    }
}
