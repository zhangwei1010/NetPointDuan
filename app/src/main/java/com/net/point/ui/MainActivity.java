package com.net.point.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.net.point.NetPointApplication;
import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.fragment.HomeFragment;
import com.net.point.fragment.MineFragment;
import com.net.point.fragment.OrderManageFragment;
import com.net.point.model.HomeModel;
import com.net.point.response.VersionBean;
import com.net.point.service.DownloadIntentService;
import com.net.point.utils.AppUtils;
import com.net.point.utils.PermissionsUtils;
import com.net.point.utils.TimeUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final int REQUEST_SETTING_NOTIFICATION = 1;
    private static WeakReference<MainActivity> reference = null;
    private VersionBean versionBean;

    public static MainActivity getInstance() {
        return reference.get();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @BindView(R.id.iv_homepage)
    ImageView iv_homepage;
    @BindView(R.id.tv_homepage)
    TextView tv_homepage;
    @BindView(R.id.iv_order)
    ImageView iv_order;
    @BindView(R.id.tv_order)
    TextView tv_order;
    @BindView(R.id.iv_mine)
    ImageView iv_mine;
    @BindView(R.id.tv_mine)
    TextView tv_mine;

    private HomeModel homeModel = new HomeModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_main);
        reference = new WeakReference<>(this);
        showAppBar(false);
        useThemestatusBarColor = true;
        setStatusBar();
        ButterKnife.bind(this);
        addFragment(new HomeFragment());
        getVersion();
    }

    @OnClick({R.id.ll_homepage, R.id.ll_order, R.id.ll_mine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_homepage:
//                useThemestatusBarColor = false;
//                setStatusBar();
                setButtonStatue(true, false, false);
                addFragment(new HomeFragment());
                break;
            case R.id.ll_order:
                setButtonStatue(false, true, false);
                addFragment(new OrderManageFragment());
                break;
            case R.id.ll_mine:
//                useThemestatusBarColor = true;
//                setStatusBar();
                setButtonStatue(false, false, true);
                addFragment(new MineFragment());
                break;
        }
    }

    public void setButtonStatue(boolean homepage, boolean order, boolean mine) {
        iv_homepage.setImageResource(homepage ? R.drawable.homepage_click : R.drawable.homepage);
        tv_homepage.setTextColor(homepage ? getResources().getColor(R.color.main_button) : getResources().getColor(R.color.black1));
        iv_order.setImageResource(order ? R.drawable.homepage_order_click : R.drawable.homepage_order);
        tv_order.setTextColor(order ? getResources().getColor(R.color.main_button) : getResources().getColor(R.color.black1));
        iv_mine.setImageResource(mine ? R.drawable.homepage_my_click : R.drawable.homepage_my);
        tv_mine.setTextColor(mine ? getResources().getColor(R.color.main_button) : getResources().getColor(R.color.black1));
    }

    private void addFragment(Fragment fragment) {
        androidx.fragment.app.FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.contenLayout, fragment);
        transaction.commit();
    }

    private void getVersion() {
        homeModel.getVersion("1", e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                List<VersionBean> versionBeans = result.getData();
                versionBean = versionBeans.get(0);
                upload();
            }
        });
    }

    private String downloadurl = "";
    private String version = "";

    private void upload() {
        if (versionBean == null) {
            return;
        }
        String version = versionBean.getVersion();
        String currentVersion = AppUtils.getPackageName(this);
        //当前版本小于服务器版本就更新
        if (AppUtils.compareVersion(version, currentVersion) == 1) {
            //当前时间大于等于服务器的有效时间就更新
            String nowTime = TimeUtils.getStringTime(System.currentTimeMillis());
            String endTime = versionBean.getValidtime();
            if (TimeUtils.timeCompare(nowTime, endTime)) {
                this.downloadurl = versionBean.getDownloadurl();
                this.version = version;
                showUpdataDialog();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 弹出对话框
     */
    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage("软件更新");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", (dialog, which) -> {
            if (!PermissionsUtils.isNotificationEnabled(this)) {
                toast("请设置通知栏权限");
                gotoNotificationSetting(this);
            } else {
                requestInstallPermissions();
            }
        });
        //当点取消按钮时不做任何举动
        builer.setNegativeButton("取消", (dialogInterface, i) -> {
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //....自行做回调处理
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
        }
        if (requestCode == REQUEST_SETTING_NOTIFICATION) {
            if (PermissionsUtils.isNotificationEnabled(this)) {
                requestInstallPermissions();
            } else {
                toast("通知栏权限未申请");
            }
        }
    }

    //请求应用安装权限
    private void requestInstallPermissions() {
        if (Build.VERSION.SDK_INT >= 26) {
            boolean canInstalls = getPackageManager().canRequestPackageInstalls();
            if (canInstalls) {
                startServices();
            } else {
                //请求安装未知应用来源的权限
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
                        INSTALL_PACKAGES_REQUEST_CODE);
            }
        } else {
            startServices();
        }
    }

    private void startServices() {
        Toast.makeText(this, "" + "开始下载", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DownloadIntentService.class);
        intent.putExtra(DownloadIntentService.INTENT_VERSION_NAME, version);
        intent.putExtra(DownloadIntentService.INTENT_DOWNLOAD_URL, downloadurl);
        startService(intent);
    }

    private static final int INSTALL_PACKAGES_REQUEST_CODE = 1;
    private static final int GET_UNKNOWN_APP_SOURCES = 2;

    /**
     * 申请权限回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case INSTALL_PACKAGES_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startServices();
                } else {
                    //  引导用户手动开启安装权限
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, GET_UNKNOWN_APP_SOURCES);
                }
                break;
            default:
                break;
        }
    }
    public void gotoNotificationSetting(Activity activity) {
        ApplicationInfo appInfo = activity.getApplicationInfo();
        String pkg = activity.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, pkg);
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, uid);
                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
                intent.putExtra("app_package", pkg);
                intent.putExtra("app_uid", uid);
                activity.startActivityForResult(intent, REQUEST_SETTING_NOTIFICATION);
            } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("package:" + NetPointApplication.getInstance().getPackageName()));
                activity.startActivityForResult(intent, REQUEST_SETTING_NOTIFICATION);
            } else {
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivityForResult(intent, REQUEST_SETTING_NOTIFICATION);
            }
        } catch (Exception e) {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivityForResult(intent, REQUEST_SETTING_NOTIFICATION);
        }
    }
}
