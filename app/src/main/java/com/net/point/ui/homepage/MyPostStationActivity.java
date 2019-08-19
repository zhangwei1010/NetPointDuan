package com.net.point.ui.homepage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.net.point.R;
import com.net.point.adapter.PostStationAdapter;
import com.net.point.model.HomeModel;
import com.net.point.response.PostStationBean;
import com.net.point.utils.MapLocationUtil;
import com.net.point.utils.SpUtils;
import com.net.point.view.HeadRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的驿站
public class MyPostStationActivity extends Activity {

    @BindView(R.id.mRecycleView)
    HeadRecycleView mRecycleView;

    private HomeModel model = new HomeModel();
    private LatLng latLng;
    private ArrayList<PostStationBean> orderBeans;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyPostStationActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_station);
        ButterKnife.bind(this);
        initRecycleView();
        startLocate();
    }

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
        }
    }

    private void loadData() {//获取经纬度
        if (TextUtils.isEmpty(SpUtils.getIncNumber())) {
            return;
        }
        showProgressDialog();
        model.insertNearPosition(SpUtils.getIncNumber(), latLng.longitude, latLng.latitude, e -> {
                    e.printStackTrace();
                    hideProgressDialog();
                    Log.e("", e.getMessage());
                },
                result -> {
                    hideProgressDialog();
                    if (result.isSuccess() && result.getData() != null) {
                        orderBeans = (ArrayList<PostStationBean>) result.getData();
                        mAdapter.setItems(orderBeans);
                    }
                });
    }

    private PostStationAdapter mAdapter;

    private void initRecycleView() {
        mAdapter = new PostStationAdapter(position -> {
            startMapView(orderBeans, position);
        });
        mRecycleView.setAdapter(mAdapter);
    }

    private void startMapView(ArrayList<PostStationBean> orderBeans, Integer position) {
        MapViewActivity.start(this, orderBeans,position);
    }

    private void startMapView(LatLng latLng) {
        MapViewActivity.start(this, latLng);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void toast(String message) {
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

    private int GPS_REQUEST_CODE = 0;

    /**
     * 定位
     */
    private void startLocate() {
        MapLocationUtil instance = MapLocationUtil.getInstance();
        boolean openGPS = instance.isOpenGPS(this);
        if (openGPS) {
            getLocation();
        } else {
            instance.showSetGPSDialog(this, GPS_REQUEST_CODE);
        }
    }

    private void getLocation() {
        MapLocationUtil.getInstance().getLocalPoint(null, false, latLng1 -> {
            if (latLng1 != null) {
                latLng = latLng1;
                loadData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            getLocation();
        }
    }
}
