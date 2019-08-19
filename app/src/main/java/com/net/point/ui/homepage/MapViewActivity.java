package com.net.point.ui.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.net.point.R;
import com.net.point.response.PostStationBean;
import com.net.point.utils.MapLocationUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的驿站
public class MapViewActivity extends Activity {

    @BindView(R.id.mMapView)
    MapView mMapView;
    @BindView(R.id.iv_return)
    ImageView iv_return;
    @BindView(R.id.iv_navigation)
    ImageView iv_navigation;

    private BaiduMap baiduMap;
    private LatLng currentLatLng;
    private LatLng redLatLng;
    private ArrayList<PostStationBean> beanArrayList;
    private int position = -1;

    public static void start(Context context, LatLng latLng) {
        Intent intent = new Intent();
        intent.putExtra("currentLatLng", latLng);
        context.startActivity(intent);
    }

    public static void start(Context context, ArrayList<PostStationBean> beans, Integer position) {
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.putParcelableArrayListExtra("poststationbean", beans);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mapview);
        ButterKnife.bind(this);
        initData();
        initMapView();
        mMapView.onCreate(this, savedInstanceState);
    }

    private void initData() {
        //currentLatLng = getIntent().getParcelableExtra("currentLatLng");
        beanArrayList = getIntent().getParcelableArrayListExtra("poststationbean");
        position = getIntent().getIntExtra("position", -1);
    }

    private void initMapView() {
        baiduMap = mMapView.getMap();
        MapLocationUtil locationUtil = MapLocationUtil.getInstance();
        if (beanArrayList != null && position != -1) {
            for (int i = 0; i < beanArrayList.size(); i++) {
                PostStationBean stationBean = beanArrayList.get(i);
                LatLng latLng = new LatLng(stationBean.latitude, stationBean.longtitude);
                if (i == position) {
                    redLatLng = new LatLng(stationBean.latitude, stationBean.longtitude);
                    moveToPoint(redLatLng);
                    locationUtil.setLocationMark(baiduMap, latLng, 17f, true);
                } else {
                    locationUtil.setLocationMark(baiduMap, latLng, 17f, false);
                }
            }
        }
    }

    @OnClick({R.id.ivBack, R.id.iv_return, R.id.iv_navigation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.iv_return:
                moveToPoint(redLatLng);
                break;
            case R.id.iv_navigation:
                break;
        }
    }

    private void moveToPoint(LatLng latLng) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(18.0f);
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁地图前线关闭个性化地图，否则会出现资源无法释放
        MapView.setMapCustomEnable(false);
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
    }
}
