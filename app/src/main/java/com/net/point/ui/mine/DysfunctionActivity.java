package com.net.point.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.TextureMapView;
import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.ui.homepage.MyPostStationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//功能异常
public class DysfunctionActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, DysfunctionActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_dysfunction);
        setContentTitle(R.string.dys_function);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.re_question1,
            R.id.re_question2, R.id.re_question3, R.id.re_question4, R.id.re_question5,
            R.id.re_question6, R.id.re_question7, R.id.re_question8, R.id.re_question9
    })
    public void onViewClicked(View view) {
        String title = "";
        String type = "";
        switch (view.getId()) {
            case R.id.re_question1:
                title = "无法打开APP";
                type = "1";
                break;
            case R.id.re_question2:
                title = "APP闪退";
                type = "2";
                break;
            case R.id.re_question3:
                title = "卡顿";
                type = "3";
                break;
            case R.id.re_question4:
                title = "黑屏白屏";
                type = "4";
                break;
            case R.id.re_question5:
                title = "死机";
                type = "5";
                break;
            case R.id.re_question6:
                title = "界面错位";
                type = "6";
                break;
            case R.id.re_question7:
                title = "界面加载慢";
                type = "7";
                break;
            case R.id.re_question8:
                title = "其他异常";
                type = "8";
                break;
            case R.id.re_question9:
                title = getString(R.string.opinions_and_suggestions);
                break;
        }
        FunctionCommitActivity.start(this, title,type);
    }
}
