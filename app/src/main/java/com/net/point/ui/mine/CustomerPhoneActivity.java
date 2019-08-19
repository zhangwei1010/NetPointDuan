package com.net.point.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.widget.WarnDialog;

public class CustomerPhoneActivity extends BaseActivity {
    private ImageView iv_customer_phone;

    public static void start(Context context) {
        context.startActivity(new Intent(context, CustomerPhoneActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_customer_phone);
        setContentTitle("客服电话");
        iv_customer_phone = findViewById(R.id.iv_customer_phone);
        iv_customer_phone.setOnClickListener(view -> showDialog());
    }

    private void showDialog() {
        WarnDialog warnDialog = new WarnDialog(this);
        warnDialog.setOK("拨打", () ->
                callPhone(getResources().
                        getString(R.string.customer_phone))).
                setCancel("取消", () -> warnDialog.dismiss())
                .show();
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
}
