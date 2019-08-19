package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//审核中
public class InReViewActivity extends BaseActivity {

    private String status;

    @BindView(R.id.iv_background)
    ImageView iv_background;
    @BindView(R.id.tv_next)
    TextView tv_next;

    public static void start(Context context, String status) {
        Intent intent = new Intent(context, InReViewActivity.class);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_in_review);
        ButterKnife.bind(this);
        status = getIntent().getStringExtra("status");
        if (TextUtils.equals("1", status)) {
            setContentTitle("审核中");
            iv_background.setImageResource(R.drawable.shenhetongguo);
            tv_next.setVisibility(View.GONE);
        } else if (TextUtils.equals("2", status)) {
            setContentTitle("审核通过");
            iv_background.setImageResource(R.drawable.shenhemeitongguo);
            tv_next.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                SuccessfulSignActivity.start(this);
                break;
        }
    }
}
