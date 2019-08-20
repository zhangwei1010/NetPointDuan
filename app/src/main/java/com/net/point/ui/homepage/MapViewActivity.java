package com.net.point.ui.homepage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.net.point.R;
import com.net.point.response.PostStationBean;
import com.net.point.utils.AppUtils;
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
    @BindView(R.id.ll_bar)
    LinearLayout ll_bar;

    private BaiduMap baiduMap;
    private LatLng currentLatLng;
    private LatLng redLatLng;
    private ArrayList<PostStationBean> beanArrayList;
    private int position = -1;

    public static void start(Context context, ArrayList<PostStationBean> beans, Integer position, LatLng currentLatLng) {
        Intent intent = new Intent(context, MapViewActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("currentLatLng", currentLatLng);
        intent.putParcelableArrayListExtra("poststationbean", beans);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mapview);
        ButterKnife.bind(this);
        setStatusBar();
        initData();
        initMapView();
        mMapView.onCreate(this, savedInstanceState);
    }

    private void initData() {
        currentLatLng = getIntent().getParcelableExtra("currentLatLng");
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
                    moveToPoint(latLng);
                    locationUtil.setLocationMark(baiduMap, latLng, 20f, true, map -> initInforWindow(map, stationBean,latLng));
                } else {
                    locationUtil.setLocationMark(baiduMap, latLng, 20f, false, map -> initInforWindow(map, stationBean,latLng));
                }
            }
        }
    }

    private void initInforWindow(BaiduMap map, PostStationBean stationBean, LatLng latLng) {
        View view = LayoutInflater.from(this).inflate(R.layout.baidumap_infowindow, null);
        InfoWindow mInfoWindow = new InfoWindow(view, latLng, -47);
        TextView title = view.findViewById(R.id.tvTitle);
        AppUtils.setTexts(title, stationBean.address);
        map.showInfoWindow(mInfoWindow);
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
                startNavigation();
                break;
        }
    }

    private void startNavigation() {
        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(currentLatLng).endPoint(redLatLng)
                .startName("天安门").endName("百度大厦");

        try {
            BaiduMapNavigation.openBaiduMapNavi(para, this);
        } catch (BaiduMapAppNotSupportNaviException e) {
            e.printStackTrace();
            showDialog();
        }
    }

    private void moveToPoint(LatLng latLng) {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(latLng).zoom(20f);
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

    /**
     * 提示未安装百度地图app或app版本过低
     */
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                OpenClientUtil.getLatestBaiduMapApp(MapViewActivity.this);
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
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
