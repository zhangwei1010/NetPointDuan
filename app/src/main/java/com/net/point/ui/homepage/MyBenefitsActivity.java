package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.net.point.R;
import com.net.point.adapter.MyBenefitsListAdapter;
import com.net.point.adapter.MyPenaltyListAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.response.PenaltyBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//我的收益
public class MyBenefitsActivity extends BaseActivity {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.tv_time_choose)
    TextView tv_time_choose;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyBenefitsActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_my_benefits);
        setContentTitle(R.string.my_benefits);
        setRightText("明细");
        ButterKnife.bind(this);
        initRecycleView();
    }

    private MyBenefitsListAdapter mAdapter = new MyBenefitsListAdapter();

    private void initRecycleView() {
        List<PenaltyBean> penaltyBeans = new ArrayList<>();
        PenaltyBean penaltyBean = new PenaltyBean();
        penaltyBean.date = "2019-6月";
        penaltyBean.title = "郑州市收益";
        penaltyBean.isHandle = "+100000.00";
        penaltyBeans.add(penaltyBean);
        PenaltyBean penaltyBean1 = new PenaltyBean();
        penaltyBean1.date = "2019-8月";
        penaltyBean1.title = "合肥市收益";
        penaltyBean1.isHandle = "+100000.00";
        penaltyBeans.add(penaltyBean1);
        PenaltyBean penaltyBean2 = new PenaltyBean();
        penaltyBean2.date = "2019-8月";
        penaltyBean2.title = "合肥市收益";
        penaltyBean2.isHandle = "+100000.00";
        penaltyBeans.add(penaltyBean2);
        mRecycleView.setAdapter(mAdapter);
        mAdapter.addItems(penaltyBeans);
    }

    @OnClick({R.id.rl_time_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_time_choose:
                showYearMonthPicker();
                break;
        }
    }

    /**
     * 月日选择
     */
    private void showMonthDayPicker() {
        TimeUtils.showDatePickerDialog(this, false, "请选择月日", 2015,

                8, 28, new TimeUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        toast(year + "-" + month + "-" + dayOfMonth);
                    }

                    @Override
                    public void onCancel() {
                        toast("cancle");
                    }
                }).setYearGone();
    }

    /**
     * 年月选择
     */
    private void showYearMonthPicker() {
        TimeUtils.showDatePickerDialog(this, true, "", 2019, 7, 15,
                new TimeUtils.OnDatePickerListener() {

                    @Override
                    public void onConfirm(int year, int month, int dayOfMonth) {
                        //toast(year + "-" + month + "-" + dayOfMonth);
                        AppUtils.setTexts(tv_time_choose, year + "年" + month + "月");
                    }

                    @Override
                    public void onCancel() {
                        toast("cancle");
                    }
                }).setDayGone();
    }
}
