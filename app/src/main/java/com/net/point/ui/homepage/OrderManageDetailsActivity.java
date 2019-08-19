package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.response.OrderDetailsBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

//订单管理详情页面
public class OrderManageDetailsActivity extends BaseActivity {

    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tv_sender)
    TextView tv_sender;
    @BindView(R.id.tv_sender_name)
    TextView tv_sender_name;
    @BindView(R.id.tv_from_tel)
    TextView tv_from_tel;
    @BindView(R.id.tv_sender_address)
    TextView tv_sender_address;
    @BindView(R.id.tv_addressee)
    TextView tv_addressee;
    @BindView(R.id.tv_addressee_name)
    TextView tv_addressee_name;
    @BindView(R.id.tv_to_tel)
    TextView tv_to_tel;
    @BindView(R.id.tv_addressee_address)
    TextView tv_addressee_address;
    @BindView(R.id.tv_isgoods)
    TextView tv_isgoods;
    @BindView(R.id.tv_isfile)
    TextView tv_isfile;

    private HomeModel model = new HomeModel();
    private String number = "";
    private OrderDetailsBean detailsBean;

    public static void start(Context context, String number) {
        Intent intent = new Intent(context, OrderManageDetailsActivity.class);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("number", number);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.order_manage);
        addContentView(R.layout.activity_order_manage_details);
        ButterKnife.bind(this);
        number = getIntent().getStringExtra("number");
        loadData();
        //根据numer生成二维码
    }

    private void loadData() {
        if (TextUtils.isEmpty(number)) return;
        model.insertOrderDetails(number, e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                detailsBean = result.getData();
                initView();
            }
        });
    }

    private void initView() {
        //设置数据
        AppUtils.setTexts(tvDestination, detailsBean.fromprovincename);
        AppUtils.setTexts(tv_sender, "寄件人:" + detailsBean.fromname);
        AppUtils.setTexts(tv_sender_name, detailsBean.fromname);
        AppUtils.setTexts(tv_from_tel, detailsBean.fromtel);
        //寄件人地址
        AppUtils.setTexts(tv_sender_address, detailsBean.fromaddress);
        AppUtils.setTexts(tv_addressee, "收件人:" + detailsBean.toname);
        AppUtils.setTexts(tv_addressee_name, detailsBean.toname);
        AppUtils.setTexts(tv_to_tel, detailsBean.totel);
        AppUtils.setTexts(tv_to_tel, detailsBean.totel);
        //收货人地址
        AppUtils.setTexts(tv_addressee_address, detailsBean.toaddress);
        AppUtils.setTexts(tv_isgoods, detailsBean.isgoods);
        AppUtils.setTexts(tv_isfile, detailsBean.num + "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
