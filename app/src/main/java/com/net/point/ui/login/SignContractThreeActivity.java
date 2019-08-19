package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.LoginModel;
import com.net.point.response.ContractInforBean;
import com.net.point.response.UploadContractinforBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.BitMapUtils;
import com.net.point.utils.SpUtils;
import com.net.point.view.LinePathView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//签订合同成功，下一步提交费用
public class SignContractThreeActivity extends BaseActivity {

    private ContractInforBean inforBean;

    @BindView(R.id.mPathView)
    LinePathView mPathView;
    @BindView(R.id.mBtnClear)
    Button mBtnClear;
    @BindView(R.id.tvContract)
    TextView tvContract;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SignContractThreeActivity.class));
    }

    public static void start(Context context, ContractInforBean bean) {
        Intent intent = new Intent(context, SignContractThreeActivity.class);
        intent.putExtra("contractinforbean", bean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.sign_contract);
        addContentView(R.layout.activity_sign_contract_three);
        ButterKnife.bind(this);
        if (mPathView != null) {
            //修改背景、笔宽、颜色
            mPathView.setBackColor(Color.WHITE);
            mPathView.setPaintWidth(5);
            mPathView.setPenColor(Color.BLACK);
        }
        inforBean = getIntent().getParcelableExtra("contractinforbean");
        if (inforBean != null) AppUtils.setTexts(tvContract, inforBean.checkRemark);
    }

    @OnClick({R.id.mBtnClear, R.id.rlSure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //清除
            case R.id.mBtnClear:
                if (mPathView != null) {
                    //修改背景、笔宽、颜色
                    mPathView.clear();
                    mPathView.setBackColor(Color.WHITE);
                    mPathView.setPaintWidth(5);
                    mPathView.setPenColor(Color.BLACK);
                }
                break;
            case R.id.rlSure:
                saveLinePath();
                break;
        }
    }

    private LoginModel model = new LoginModel();

    private void saveLinePath() {
        //    /data/user/0/com.net.point/files/images/linepath.jpg
        String path = getFilesDir() + File.separator + "images" + File.separator + "linepath.jpg";
        mPathView.save(path);
        String base64 = BitMapUtils.imageToBase64(path);
        String userId = SpUtils.getUserId();
        if (inforBean == null) return;
        showProgressDialog();
        model.upLoadContractInfor(userId, inforBean.provinceCode, inforBean.cityCode, inforBean.areaCode, inforBean.areaStreetCode,
                base64, inforBean.companyName, inforBean.identityNum, inforBean.legalPerson,
                e -> {
                    e.printStackTrace();
                    hideProgressDialog();
                }, result -> {
                    hideProgressDialog();
                    UploadContractinforBean data = result.getData();
                    if (result.isSuccess() && result.getData() != null) {
                        String signNetId = data.getSignNetId();
                        if (!TextUtils.isEmpty(signNetId)) {
                            SpUtils.saveSignNetId(signNetId);
                            TwoPayWayActivity.start(this);
                        }
                    }
                });
    }
}
