package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.LoginModel;
import com.net.point.response.ContractInforBean;
import com.net.point.response.FranchisePriceBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.IDCard;
import com.net.point.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//申请网点成功，签订合同
public class SignContractActivity extends BaseActivity {

    private ContractInforBean inforBean;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SignContractActivity.class));
    }

    public static void start(Context context, ContractInforBean bean) {
        Intent intent = new Intent(context, SignContractActivity.class);
        intent.putExtra("contractinforbean", bean);
        context.startActivity(intent);
    }

    @BindView(R.id.tvContract)
    TextView tvContract;
    @BindView(R.id.et_legal_name)
    TextView et_legal_name;
    @BindView(R.id.et_compony_name)
    EditText et_compony_name;
    @BindView(R.id.et_id_number)
    EditText et_id_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.sign_contract);
        addContentView(R.layout.activity_sign_contract);
        ButterKnife.bind(this);
        inforBean = getIntent().getParcelableExtra("contractinforbean");
        loadData();
    }

    private LoginModel model = new LoginModel();

    private void loadData() {
        if (inforBean != null) {
            model.getFranchisePrice(inforBean.provinceCode, inforBean.cityCode, inforBean.areaCode,
                    inforBean.areaStreetCode, e -> {
                        e.printStackTrace();
                        toast(e.getMessage());
                    }, result -> {
                        FranchisePriceBean priceBean = result.getData();
                        if (result.isSuccess() && result.getData() != null) {
                            if (inforBean != null) inforBean.checkRemark = priceBean.checkRemark;
                            AppUtils.setTexts(tvContract, priceBean.remark);
                            SpUtils.saveContractFree(priceBean.joinprice);
                        }
                    });
        }
    }

    @OnClick({R.id.rl_sign_contract, R.id.rl_last})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_sign_contract:
                if (isNull()) return;
                if (inforBean != null) {
                    inforBean.companyName = et_compony_name.getText().toString();
                    inforBean.legalPerson = et_legal_name.getText().toString();
                    inforBean.identityNum = et_id_number.getText().toString();
                    SignContractTwoActivity.start(this, inforBean);
                }
                break;
            case R.id.rl_last:
                finish();
                break;
        }
    }

    private boolean isNull() {
        boolean isnull = false;
        if (TextUtils.isEmpty(et_legal_name.getText().toString())) {
            isnull = true;
            toast("法人姓名不能为空");
        }
        if (TextUtils.isEmpty(et_id_number.getText().toString())) {
            isnull = true;
            toast("身份证号码不能为空");
        }
        if (!IDCard.IDCardValidate(et_id_number.getText().toString())) {
            isnull = true;
            toast("请输入有效身份证号码");
        }
        return isnull;
    }
}
