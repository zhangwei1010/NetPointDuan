package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alipay.sdk.app.PayTask;
import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.response.PayResult;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

//微信支付和支付宝支付
public class PayMoneyActivity extends BaseActivity {

    @BindView(R.id.iv_zhifubao)
    ImageView iv_zhifubao;
    @BindView(R.id.iv_weixin)
    ImageView iv_weixin;
    @BindView(R.id.tv_money)
    TextView tv_money;
    private Thread payThread;
    private String data;

    public static void start(Context context) {
        context.startActivity(new Intent(context, PayMoneyActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.cash_paymengt);
        addContentView(R.layout.activity_pay_money);
        ButterKnife.bind(this);
        AppUtils.setTexts(tv_money, SpUtils.getContractFree());
        getData();
    }

    private boolean online = true;
    private String totalAmount = "0.1";
    private String body = "kddk";//固定，后台暂时不用
    private String subject = "网签费用";//固定，后台暂时不用
    private String netSignId = "1";

    @OnClick({/*R.id.rl_weixin,*/ R.id.rl_zhifubao, R.id.btn_pay})
    void onViewClicked(View view) {
        switch (view.getId()) {
          /*  case R.id.rl_weixin:
                iv_zhifubao.setImageResource(R.drawable.uncheck_online);
                iv_weixin.setImageResource(R.drawable.check_online);
                break;*/
            case R.id.rl_zhifubao:
                online = !online;
                iv_zhifubao.setImageResource(online ? R.drawable.check_online : R.drawable.uncheck_online);
                break;
            case R.id.btn_pay:
                //String url = "http://192.168.124.7:8081/alipayy/AppPay";
                /**
                 * String url = String.format("http://192.168.124.7:8081/alipayy/AppPay?totalAmount=%s&body=%s&subject=%s&netSignId=%s",
                 * totalAmount, body, subject, netSignId);
                 */
                if (online) payWithZhifubao(data);
                break;
        }
    }

    private void getData() {//okhttp3 异步post请求
        totalAmount = tv_money.getText().toString().trim();
        netSignId = SpUtils.getSignNetId();
        FormBody.Builder builder = new FormBody.Builder();
        /* 添加两个参数 */
        builder.add("totalAmount", totalAmount);
        builder.add("body", body);
        builder.add("subject", subject);
        builder.add("netSignId", netSignId);
        String url = "http://122.114.82.200/aliPay/AppPay";
        //url = "http://192.168.124.15:8081/aliPay/AppPay";
        url = "http://www.k8yz.com/aliPay/AppPay";
        Request request = new Request.Builder().url(url).post(builder.build()).build();

        /* 下边和get一样了 */
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, Response response) {
                Log.d("", "response: " + response);
                try {
                    final String bodyStr = response.body().string();
                    JSONObject jsonObject = new JSONObject(bodyStr);
                    /**
                     * data =
                     * alipay_sdk=alipay-sdk-java-3.7.89.ALL&app_id=2019070465728546&biz_content=%7B%22body%22%3A%22kddk%22%2C%22out_trade_no
                     * %22%3A%2220190716091519010%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22miaoshu%22%2C%22
                     * timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.
                     * trade.app.pay&notify_url=www.k8yz.com%2FreturnPay%2FappaliReturnPay&sign=Aq9F8nLls6zcKIjIZdNahphSTpr7M1JICik
                     * OXn0%2FEsv3RAMZqwS7ekiTLtRQHKtlhVkdGS%2BPFtW1vkTr3o8sTKQtoqNJjM5ix%2Fmr4lkLRD6qqFaqzj%2Bdl06NzHhsgyKFv8FA4Cpp7
                     * hJFmu6wFbRww0tv4pCD7ZMRJ6wf%2F%2BwND5d3VDDdoH3ioeZeq8rLyjOEdW%2B8isP9lhHDfykeFPrfHGw73eYRWl%2FRCRGsZXNWOw7QqbLH9
                     * D%2BYrc0onzwxE6w6In4gcaz3cP34Son97BWvnE%2FKL1EhxxVuPWK1J4SqllhfqbRtr8LNA3wGC4Unce2ErNhsGO0ZXcyleBRwM%2B5kvw%3D%3D&
                     * sign_type=RSA2&timestamp=2019-07-16+09%3A15%3A19&version=1.0
                     */
                    data = jsonObject.getString("data");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(Call call, final IOException e) {
                runOnUiThread(() -> Toast.makeText(PayMoneyActivity.this, "error : " + e.toString(), Toast.LENGTH_SHORT).show());
            }
        });
    }

    /******************************************支付宝**********************************************/
    private void payWithZhifubao(@NonNull String payInfo) {
        Observable.just(payInfo)
                .map(s -> {
                    PayTask alipay = new PayTask(PayMoneyActivity.this);
                    Map<String, String> result = alipay.payV2(payInfo, true);
                    return result;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(map -> {
                    PayResult payResult = new PayResult(map);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Observable.timer(2, TimeUnit.SECONDS)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(aLong -> {
                                    finish();
                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知
                                    Intent intent = new Intent(PayMoneyActivity.this, SuccessfulSignActivity.class);
                                    startActivity(intent);
                                    toast("支付成功");
                                });
                    } else {
                        //TODO  realPay.setEnabled(true);  设置支付按钮是否可点击
                        toast("支付失败");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
