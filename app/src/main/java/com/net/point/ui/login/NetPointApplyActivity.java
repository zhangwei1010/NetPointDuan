package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.LoginModel;
import com.net.point.response.ProvinceBean;
import com.net.point.ui.LevelType;
import com.net.point.widget.CityPopupWindow;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NetPointApplyActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, NetPointApplyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.apply_net_point);
        addContentView(R.layout.activity_net_point_apply);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_province, R.id.rl_city, R.id.rl_counties, R.id.ll_net_point})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_province:
                showProgressDialog();
                loadProvinceData(LevelType.PROVINCE);
                break;
            case R.id.rl_city:
                showProgressDialog();
                loadProvinceData(LevelType.CITY);
                break;
            case R.id.rl_counties:
                showProgressDialog();
                loadProvinceData(LevelType.COUNTIES);
                break;
            case R.id.ll_net_point:
                //showProgressDialog();
                //loadProvinceData(LevelType.NET_POINT);
                toast("此功能正在开发中");
                break;
        }
    }

    private LoginModel model = new LoginModel();

    private void loadProvinceData(int type) {
        model.insertProvinces("", e -> {
            hideProgressDialog();
            e.printStackTrace();
        }, result -> {
            List<ProvinceBean> beanList = result.getData();
            hideProgressDialog();
            boolean success = result.isSuccess();
            if (!success) return;
            if (result.getData() != null) {
                showPopupWindow(type, beanList);
            }
        });
    }

    private CityPopupWindow mCityWindow;

    private void showPopupWindow(int type, List<ProvinceBean> data) {
        if (mCityWindow != null) {
            mCityWindow = null;
        }
        mCityWindow = new CityPopupWindow(this, type, data);
        mCityWindow.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCityWindow != null) {
            mCityWindow = null;
        }
    }
}
