package com.net.point.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.net.point.R;
import com.net.point.broadcastreceiver.NetBroadcastReceiver;
import com.net.point.event.FirstEvent;
import com.net.point.utils.ActivityUtil;
import com.net.point.utils.AppUtils;
import com.net.point.utils.ConstantUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.Nullable;


/**
 * BaseActivity是所有Activity的基类，把一些公共的方法放到里面，如基础样式设置，权限封装，网络状态监听等
 * <p>
 */
public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.NetChangeListener {
    //拍照和写入权限
    protected String[] permisions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件

    private boolean isOpenKeyboardEvent = true;
    protected String pageSize = "10";
    protected boolean hasMore = false;
    TextView mTitle,tvRightText;
    //@BindView(R.id.mBackImageBtn)
    ImageView mBackImageBtn, mCloseBtn, mSearchView,mShareView;
    RelativeLayout rlAppBar;
    FrameLayout contentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // 执行初始化方法
        // 隐藏标题栏
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);
        // 初始化netEvent
        netEvent = this;
        setContentView(R.layout.activity_base);
        //注册事件传递
        EventBus.getDefault().register(this);
        setClick();
        // 沉浸效果
        setStatusBar();
        //if (NetWorkUtil.isNetworkConnected(this)) loadData();
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    //是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
    protected boolean useThemestatusBarColor = false;
    //是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
    protected boolean useStatusBarColor = true;

    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                getWindow().setStatusBarColor(getResources().getColor(R.color.white));//设置状态栏背景色
            } else {
                getWindow().setStatusBarColor(Color.TRANSPARENT);//透明
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        } else {
            Toast.makeText(this, "低于4.4的android系统版本不存在沉浸式状态栏", Toast.LENGTH_SHORT).show();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && useStatusBarColor) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setParams();
    }

    private void setParams() {
        LinearLayout ll_bar = findViewById(R.id.ll_bar);
        ll_bar.setVisibility(View.VISIBLE);
        //获取到状态栏的高度
        int statusHeight = AppUtils.getStatusBarHeight(this);
        //动态的设置隐藏布局的高度
        ViewGroup.LayoutParams params = ll_bar.getLayoutParams();
        params.height = statusHeight;
        ll_bar.setLayoutParams(params);
    }

    public void onBack(View v) {
        finish();
    }

    public void onClose(View v) {
        finish();
    }

    public void onSearch(View v) {

    }
    public void onShare(View v) {

    }

    //向contentView中添加视图
    public void addContentView(int layout) {
        if (contentView != null) {
            contentView.setVisibility(View.VISIBLE);
            contentView.removeAllViews();
            View view = View.inflate(this, layout, null);
            contentView.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public void showBackButton(boolean show) {
        if (mBackImageBtn != null)
            mBackImageBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showSearchButton(boolean show) {
        if (mSearchView != null)
            mSearchView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    public void showShareView(boolean show) {
        if (mShareView != null)
            mShareView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setAppBarBackground(int resid) {
        if (rlAppBar != null)
            rlAppBar.setBackgroundResource(resid);
    }

    public void showCloseButton(boolean show) {
        if (mCloseBtn != null)
            mCloseBtn.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showAppBar(boolean show) {
        if (rlAppBar != null)
            rlAppBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void showTittle(boolean show) {
        if (mTitle != null)
            mTitle.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    protected void setClick() {
        mTitle = findViewById(R.id.mTitle);
        tvRightText = findViewById(R.id.tvRightText);
        contentView = findViewById(R.id.contentView);
        mBackImageBtn = findViewById(R.id.mBackImageBtn);
        mCloseBtn = findViewById(R.id.mCloseBtn);
        rlAppBar = findViewById(R.id.rl_appBar);
        mSearchView = findViewById(R.id.mSearchView);
        mShareView = findViewById(R.id.mShareView);
    }

    // 抽象 - 初始化方法，可以对数据进行初始化
//    protected abstract boolean initIsOpenKeyboardEvent();

    @Override
    protected void onResume() {
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = ConstantUtil.TEXTVIEWSIZE;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    public void setContentTitle(int title) {
        if (mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }
    }

    public void setContentTitle(String title) {
        if (mTitle != null) {
            mTitle.setVisibility(View.VISIBLE);
            mTitle.setText(title);
        }
    }
    public void setRightText(String title) {
        if (tvRightText != null) {
            tvRightText.setVisibility(View.VISIBLE);
            tvRightText.setText(title);
        }
    }

    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        //取消注册事件传递
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public boolean hasPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doRequestPermissionsResult(requestCode, grantResults);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    // 修改状态栏的颜色
    public void setStatusBarColor(int color) {
        try {
            Window window = getWindow();
            // 取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            // 需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }
            // 设置状态栏颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(color));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 事件分发
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isOpenKeyboardEvent) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
        } else {
            return super.dispatchTouchEvent(ev);
        }
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
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

    protected void toast(String message) {
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}