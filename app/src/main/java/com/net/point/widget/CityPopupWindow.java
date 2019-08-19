package com.net.point.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.net.point.R;
import com.net.point.model.LoginModel;
import com.net.point.response.ContractInforBean;
import com.net.point.response.ProvinceBean;
import com.net.point.ui.LevelType;
import com.net.point.ui.login.SignContractActivity;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.net.point.R.*;

public class CityPopupWindow extends PopupWindow implements View.OnClickListener {

    private RecyclerView mRecycleView, mVerRecycleView;
    private View mPopView;
    private LinearLayout llTop, ll_content, ll_comfirm_cancel, ll_line_1, ll_line_2, ll_line_3, ll_line_4;
    private RelativeLayout rl_districts, rl_province, rl_city, rl_counties;
    private ImageView ivClose;
    private Button comfirm, cancel;
    public Activity context;
    public TextView tv_successful_sign, tv_province, tv_city, tv_counties, tv_districts, tv_please_choose;
    private NetPointAdapter mAdapter;

    public String provinceCode = "", cityCode = "", countiesCode = "", netPointCode = "";
    public String provinceName = "", cityName = "", areaName = "", areaStreetName = "";

    public CityPopupWindow(Activity context, int type, List<ProvinceBean> data) {
        super(context);        // TODO Auto-generated constructor stub
        this.context = context;
        init(context, data, type);
        setPopupWindow(context);
    }

    /**
     * 初始化
     *
     * @param context
     * @param data
     * @param type
     */
    private void init(Context context, List<ProvinceBean> data, int type) {        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);        //绑定布局
        mPopView = inflater.inflate(R.layout.city_popup_window, null);

        findView();
        initRecycleView(context, data, type);

        tv_successful_sign.setVisibility(View.GONE);
        ll_comfirm_cancel.setVisibility(View.GONE);
    }

    private void findView() {
        ll_content = mPopView.findViewById(id.ll_content);
        llTop = mPopView.findViewById(id.llTop);
        ivClose = mPopView.findViewById(id.ivClose);
        tv_successful_sign = mPopView.findViewById(id.tv_successful_sign);
        tv_province = mPopView.findViewById(id.tv_province);
        tv_city = mPopView.findViewById(id.tv_city);
        tv_counties = mPopView.findViewById(id.tv_counties);
        tv_districts = mPopView.findViewById(id.tv_districts);
        tv_please_choose = mPopView.findViewById(id.tv_please_choose);
        rl_districts = mPopView.findViewById(id.rl_districts);
        rl_province = mPopView.findViewById(id.rl_province);
        rl_city = mPopView.findViewById(id.rl_city);
        rl_counties = mPopView.findViewById(id.rl_counties);
        ll_comfirm_cancel = mPopView.findViewById(id.ll_comfirm_cancel);
        ll_line_1 = mPopView.findViewById(id.ll_line_1);
        ll_line_2 = mPopView.findViewById(id.ll_line_2);
        ll_line_3 = mPopView.findViewById(id.ll_line_3);
        ll_line_4 = mPopView.findViewById(id.ll_line_4);
        cancel = mPopView.findViewById(id.cancel);
        comfirm = mPopView.findViewById(id.comfirm);
        mRecycleView = mPopView.findViewById(id.mRecycleView);
        mVerRecycleView = mPopView.findViewById(id.mVerRecycleView);
    }

    private void initRecycleView(Context context, List<ProvinceBean> city, int type) {
        mAdapter = new NetPointAdapter(context, city, type);
        mRecycleView.setAdapter(mAdapter);
    }

    float scale = 458 / 374;

    /**
     * 设置窗口的相关属性
     *
     * @param context
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow(Activity context) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        llTop.setOnClickListener(view -> dismiss());
        ivClose.setOnClickListener(view -> dismiss());
        cancel.setOnClickListener(view -> {
            tv_successful_sign.setVisibility(View.GONE);
            ll_comfirm_cancel.setVisibility(View.GONE);
            currentPosition = -1;
            if (mAdapter != null) mAdapter.notifyDataSetChanged();
        });

        comfirm.setOnClickListener(view -> decideIsHasSign());
    }

    public void show() {
        showAsDropDown(mPopView);
    }


    @Override
    public void onClick(View v) {        // TODO Auto-generated method stub
    }

    public String name = "";
    private int count = 1;
    private int currentPosition = -1;
    private int detailsPosition = -1;
    private int checkPosition = -1;

    private boolean showView = false;

    public class NetPointAdapter extends RecyclerView.Adapter<ViewHolder> {

        private Context context;
        private List<ProvinceBean> provinceList;
        private int type;

        public NetPointAdapter(Context context, List<ProvinceBean> provinceList, int type) {
            this.context = context;
            this.provinceList = provinceList;
            this.type = type;
            setTitle();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_net_point_province, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProvinceBean dataListBean = provinceList.get(position);
            AppUtils.setTexts(holder.tv_province, dataListBean.name);
            holder.iv_check.setOnClickListener(view -> {
                if (type == LevelType.PROVINCE) checkPosition = position;
                tv_successful_sign.setVisibility(View.VISIBLE);
                ll_comfirm_cancel.setVisibility(View.VISIBLE);
                holder.iv_check.setImageResource(R.drawable.check_online);
                provinceCode = dataListBean.code;
                notifyDataSetChanged();
            });
            if (type == LevelType.PROVINCE) {
                if (showView) {
                    holder.iv_check.setVisibility(View.VISIBLE);
                    holder.tv_insertDetails.setVisibility(View.VISIBLE);
                    holder.tv_number.setVisibility(View.VISIBLE);
                    int num = position + 1;
                    holder.tv_number.setText("(" + num + ")");
                } else {
                    holder.iv_check.setVisibility(View.GONE);
                    holder.tv_insertDetails.setVisibility(View.GONE);
                    holder.tv_number.setVisibility(View.GONE);
                }
                if (detailsPosition == position) {
                    holder.tv_insertDetails.setTextColor(context.getResources().getColor(color.main_button));
                } else {
                    holder.tv_insertDetails.setTextColor(Color.BLACK);
                }
                if (checkPosition == position) {
                    holder.iv_check.setImageResource(drawable.check_online);
                } else {
                    holder.iv_check.setImageResource(drawable.uncheck_online);
                }
            } else {
                holder.iv_check.setVisibility(View.GONE);
                holder.tv_number.setVisibility(View.GONE);
                holder.tv_insertDetails.setVisibility(View.GONE);
            }
            if (currentPosition == position) {//选中的item
                holder.tv_province.setTextColor(context.getResources().getColor(color.main_button));
            } else {
                holder.tv_province.setTextColor(Color.BLACK);
            }
            holder.tv_insertDetails.setOnClickListener(view -> {
                detailsPosition = position;
                holder.tv_insertDetails.setTextColor(context.getResources().getColor(color.main_button));
                if (count == 2) initHorRecycleView(holder, dataListBean);
            });
            holder.itemView.setOnClickListener(view -> {
                if (showView) {
                    return;
                }
                tv_successful_sign.setText("是否成为该" + name + "代理");
                currentPosition = position;
                holder.tv_province.setTextColor(context.getResources().getColor(color.main_button));
                notifyDataSetChanged();
                switch (type) {
                    case LevelType.PROVINCE:
                        if (count == 1) {
                            provinceCode = dataListBean.code;
                            provinceName = dataListBean.name;
                            if (dataListBean.code.contains(",")) {
                                loadDataByCodes(dataListBean.code);
                            } else {
                                tv_successful_sign.setVisibility(View.VISIBLE);
                                ll_comfirm_cancel.setVisibility(View.VISIBLE);
                            }
                        }
                        if (count == 2) {
                            if (!dataListBean.code.contains(",")) {
                                tv_successful_sign.setVisibility(View.VISIBLE);
                                ll_comfirm_cancel.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case LevelType.CITY:
                        showCityView(dataListBean);
                        if (count < type)
                            loadMore(dataListBean.code);
                        break;
                    case LevelType.COUNTIES:
                        showCountiesView(dataListBean);
                        if (count < type)
                            loadMore(dataListBean.code);
                        break;
                    case LevelType.NET_POINT:
                        if (count < type) {
                            loadMore(dataListBean.code);
                        }
                        break;
                }
            });
        }

        private void setTitle() {
            switch (type) {
                case LevelType.PROVINCE:
                    name = "省";
                    break;
                case LevelType.CITY:
                    name = "市";
                    break;
                case LevelType.COUNTIES:
                    name = "区、县";
                    break;
                case LevelType.NET_POINT:
                    name = "网点";
                    break;
            }
            AppUtils.setTexts(tv_please_choose, "请选择代理的" + name);
        }

        private void loadDataByCodes(String code) {
            model.insertByCodes(code, e ->
                    e.printStackTrace(), result -> {
                count++;
                List<ProvinceBean> provinceBeanList = result.getData();
                if (result.isSuccess() && !provinceBeanList.isEmpty()) {
                    showView = true;
                    currentPosition = -1;
                    initRecycleView(context, provinceBeanList, type);
                }
            });
        }

        private void loadMore(String code) {
            model.insertProvinces(code, e ->
                    e.printStackTrace(), result -> {
                count++;
                List<ProvinceBean> provinceBeanList = result.getData();
                if (result.isSuccess() && !provinceBeanList.isEmpty()) {
                    currentPosition = -1;
                    initRecycleView(context, provinceBeanList, type);
                }
            });
        }

        @Override
        public int getItemCount() {
            return provinceList.size();
        }

        private void initHorRecycleView(ViewHolder holder, ProvinceBean dataListBean) {
            model.insertProvinces(dataListBean.code, e ->
                    e.printStackTrace(), result -> {
                List<ProvinceBean> provinceBeanList = result.getData();
                mAdapter.notifyDataSetChanged();
                if (result.isSuccess() && !provinceBeanList.isEmpty()) {
                    mVerRecycleView.setVisibility(View.VISIBLE);
                    HorizontailAdapter horizontailAdapter = new HorizontailAdapter(context, provinceBeanList, type);
                    mVerRecycleView.setAdapter(horizontailAdapter);
                }
            });
        }
    }

    private LoginModel model = new LoginModel();

    private void decideIsHasSign() {//判断是否为网签
        ContractInforBean bean = new ContractInforBean();
        bean.provinceCode = provinceCode;
        bean.cityCode = cityCode;
        bean.areaCode = countiesCode;
        bean.areaStreetCode = netPointCode;
        bean.provinceName = provinceName;
        bean.cityName = cityName;
        bean.areaName = areaName;
        bean.areaStreetName = areaStreetName;
        model.hasSign(provinceCode, cityCode, countiesCode, netPointCode,
                e -> e.printStackTrace(), result -> {
                    LoginModel.HasSignBean hasSignBean = result.getData();
                    if (hasSignBean.hasSigned) {
                        ll_comfirm_cancel.setVisibility(View.GONE);
                        tv_successful_sign.setText("*该" + name + "已有代理，请重新选择！");
                    } else {
                        ll_comfirm_cancel.setVisibility(View.VISIBLE);
                        SpUtils.saveSignName(name);
                        SignContractActivity.start(context, bean);
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(id.tv_province)
        TextView tv_province;
        @BindView(id.iv_check)
        ImageView iv_check;
        @BindView(id.tv_number)
        TextView tv_number;
        @BindView(id.tv_insertDetails)
        TextView tv_insertDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String cityNameTV = "";

    private void showCountiesView(ProvinceBean dataListBean) {
        rl_province.setVisibility(View.VISIBLE);
        if (count == 1) {
            provinceName = dataListBean.name;
            provinceCode = dataListBean.code;
            ll_line_1.setVisibility(View.GONE);
            tv_province.setText(dataListBean.name);
        } else if (count == 2) {
            cityCode = dataListBean.code;
            cityName = dataListBean.name;
            rl_districts.setVisibility(View.VISIBLE);
            ll_line_1.setVisibility(View.VISIBLE);
            ll_line_4.setVisibility(View.VISIBLE);
            tv_city.setText(dataListBean.name);
            tv_districts.setText(dataListBean.name);
            cityNameTV = dataListBean.name;
        } else {
            countiesCode = dataListBean.code;
            areaName = dataListBean.name;
            rl_city.setVisibility(View.VISIBLE);
            ll_line_4.setVisibility(View.VISIBLE);
            tv_city.setText(cityNameTV);
            tv_districts.setText(dataListBean.name);
            tv_successful_sign.setVisibility(View.VISIBLE);
            ll_comfirm_cancel.setVisibility(View.VISIBLE);
        }
    }

    private void showCityView(ProvinceBean dataListBean) {
        rl_province.setVisibility(View.VISIBLE);
        if (count == 1) {
            provinceName = dataListBean.name;
            provinceCode = dataListBean.code;
            ll_line_1.setVisibility(View.GONE);
            tv_province.setText(dataListBean.name);
        } else if (count == 2) {
            cityCode = dataListBean.code;
            cityName = dataListBean.name;
            rl_districts.setVisibility(View.VISIBLE);
            rl_districts.setVisibility(View.VISIBLE);
            ll_line_1.setVisibility(View.VISIBLE);
            ll_line_4.setVisibility(View.VISIBLE);
            tv_districts.setText(dataListBean.name);

            tv_successful_sign.setVisibility(View.VISIBLE);
            ll_comfirm_cancel.setVisibility(View.VISIBLE);
        }
    }

    public class HorizontailAdapter extends RecyclerView.Adapter<HorizontailViewHolder> {

        private Context context;
        private List<ProvinceBean> provinceList;

        public HorizontailAdapter(Context context, List<ProvinceBean> provinceList, int type) {
            this.context = context;
            this.provinceList = provinceList;
        }

        @NonNull
        @Override
        public HorizontailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_net_point_province_horzontail, parent, false);
            return new HorizontailViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HorizontailViewHolder holder, int position) {
            holder.tv_province.setText(provinceList.get(position).name);
        }


        @Override
        public int getItemCount() {
            return provinceList.size();
        }
    }

    public class HorizontailViewHolder extends RecyclerView.ViewHolder {
        @BindView(id.tv_province)
        TextView tv_province;

        public HorizontailViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}