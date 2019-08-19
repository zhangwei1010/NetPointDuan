package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.net.point.ContractWebViewClient;
import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.response.ContractInforBean;
import com.net.point.utils.AppUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//申请网点成功，签订合同
public class SignContractTwoActivity extends BaseActivity {

    private ContractInforBean inforBean;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SignContractTwoActivity.class));
    }

    public static void start(Context context, ContractInforBean bean) {
        Intent intent = new Intent(context, SignContractTwoActivity.class);
        intent.putExtra("contractinforbean", bean);
        context.startActivity(intent);
    }

    @BindView(R.id.tv_remain_time)
    TextView tv_remain_time;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.sign_contract);
        addContentView(R.layout.activity_sign_contract_two);
        ButterKnife.bind(this);
        inforBean = getIntent().getParcelableExtra("contractinforbean");
        timer.start();
        loadingWebView();
    }

    @OnClick({R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_commit:
                if (inforBean != null) {
                    SignContractThreeActivity.start(this, inforBean);
                }
                break;
        }
    }

    private void loadingWebView() {
        String loadingUrl = getUrl();
        webView.loadUrl(loadingUrl);
        webView.setWebViewClient(new ContractWebViewClient());
        webView.setBackgroundColor(Color.WHITE);
        //支持javascript
        webView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        webView.setInitialScale(25);//为25%，最小缩放等级
        // 设置出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    private String getUrl() {
        //http://192.168.124.13:8080/%@.html?companyName=%@&legalPerson=%@&identityNum=%@&province=%@&city=%@&area=%@&areaStreet=%@"
        String province = inforBean.provinceName;
        String city = inforBean.cityName;
        String area = inforBean.areaName;
        String areaStreet = inforBean.areaStreetName;
        String targetUrl = "";
        if (!TextUtils.isEmpty(province)) {
            targetUrl = "shenglianmeng.html";
        }
        if (!TextUtils.isEmpty(city)) {
            targetUrl = "dijishi.html";
        }
        if (!TextUtils.isEmpty(area)) {
            targetUrl = "quxian.html";
        }
        if (!TextUtils.isEmpty(areaStreet)) {
            targetUrl = "wangdian.html";
        }
        // http://122.114.82.200:8081/
        String url = "http://www.k8yz.com/" + targetUrl + "?companyName=" + inforBean.companyName + "&legalPerson="
                + inforBean.legalPerson + "&identityNum=" + inforBean.identityNum + "&province=" + inforBean.provinceName
                + "&city=" + inforBean.cityName + "&area=" + inforBean.areaName + "&areaStreet=" + inforBean.areaStreetName;
        return url;
    }

    /**
     * 倒计时60秒，一次1秒
     */
    private CountDownTimer timer = new CountDownTimer(30 * 60 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub
            SimpleDateFormat dateFormat = new SimpleDateFormat("mm分ss秒");
            //String time = AppUtils.timeParse(millisUntilFinished);
            AppUtils.setTexts(tv_remain_time, dateFormat.format(new Date(millisUntilFinished)));
        }

        @Override
        public void onFinish() {
            AppUtils.setTexts(tv_remain_time, "时间已到");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
