package com.net.point.model;

import androidx.annotation.NonNull;

import com.net.point.base.BaseModel;
import com.net.point.request.HttpResult;
import com.net.point.request.RetrofitUtil;
import com.net.point.response.BankInfoBean;
import com.net.point.response.FranchisePriceBean;
import com.net.point.response.PersonalBean;
import com.net.point.response.ProvinceBean;
import com.net.point.response.UploadContractinforBean;
import com.net.point.response.UseCountBean;
import com.net.point.response.UserInforBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by ${yyy} on 18-5-17.
 * Model of SignIn SignUp BindMobileNumber
 */

public class LoginModel extends BaseModel {

    @NonNull
    private Service service = RetrofitUtil.getInstance().create(Service.class);

    interface Service {
        @POST("regionInfo/selectRegionList")
        @FormUrlEncoded
        Observable<HttpResult<List<ProvinceBean>>> insertProvinces(@Field("parentCode") @NonNull String parentCode);

        @POST("regionInfo/selectRegionByCodes")
        @FormUrlEncoded
        Observable<HttpResult<List<ProvinceBean>>> insertByCodes(@Field("codes") @NonNull String codes);

        @POST("Netsign/checkRegionNetSign")
        @FormUrlEncoded
        Observable<HttpResult<HasSignBean>> hasSign(@Field("provinceCode") @NonNull String province,
                                                    @Field("cityCode") @NonNull String city,
                                                    @Field("network") @NonNull String network, @Field("county") @NonNull String county);

        @POST("Netsign/saveNetSignInfo1")
        @FormUrlEncoded
        Observable<HttpResult<UploadContractinforBean>> upLoadContractInfor(@Field("userId") @NonNull String userId,
                                                                            @Field("province") @NonNull String province,
                                                                            @Field("city") @NonNull String city,
                                                                            @Field("area") @NonNull String area,
                                                                            @Field("areaStreet") @NonNull String areaStreet,
                                                                            @Field("imgValue") @NonNull String imgValue,
                                                                            @Field("companyName") @NonNull String companyName,
                                                                            @Field("identityNum") @NonNull String identityNum,
                                                                            @Field("legalPerson") @NonNull String legalPerson);

        @POST("bankpay/bankpayinfo")
        @FormUrlEncoded
        Observable<HttpResult<Object>> upLoadUnlineInfor(@Field("signid") @NonNull String province,
                                                         @Field("imgValue") @NonNull String imgValue,
                                                         @Field("customername") @NonNull String customername,
                                                         @Field("banknum") @NonNull String banknum,
                                                         @Field("banktype") @NonNull String banktype);

        @POST("app/dict/defaultPrice")
        @FormUrlEncoded
        Observable<HttpResult<FranchisePriceBean>> getFranchisePrice(@Field("province") @NonNull String province,
                                                                     @Field("city") @NonNull String city,
                                                                     @Field("area") @NonNull String area,
                                                                     @Field("areaStreet") @NonNull String areaStreet);

        @POST("login/addUserName")
        @FormUrlEncoded
        Observable<HttpResult<PersonalBean>> uploadUserInfor(@Field("realName") @NonNull String realName,
                                                             @Field("phone") @NonNull String phone,
                                                             @Field("address") @NonNull String address);

        @POST("login/updateUserInfo")
        @FormUrlEncoded
        Observable<HttpResult<String>> uploadSignCount(@Field("userId") @NonNull String userId,
                                                       @Field("userName") @NonNull String userName,
                                                       @Field("password") @NonNull String password);

        @POST("login/dologin")
        @FormUrlEncoded
        Observable<HttpResult<UserInforBean>> login(@Field("number") @NonNull String number,
                                                    @Field("Password") @NonNull String Password);


        @POST("login/crtUserNumber")
        @FormUrlEncoded
        Observable<HttpResult<UseCountBean>> getCount(@Field("userId") @NonNull String userId);


        @POST("app/dict/defaultBankInfo")
        Observable<HttpResult<BankInfoBean>> getBankInfo();

    }

    //查询省市县区
    public void insertProvinces(@NonNull String parentCode, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<ProvinceBean>>> onResult) {
        register(service.insertProvinces(parentCode), onError, onResult);
    }

    //判断是否为网签
    public void hasSign(
            @NonNull String province,
            @NonNull String city,
            @NonNull String network,
            @NonNull String county, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<HasSignBean>> onResult) {
        register(service.hasSign(province, city, network, county), onError, onResult);
    }

    //提交线下支付的信息
    public void upLoadUnlineInfor(
            @NonNull String signid,
            @NonNull String imgValue,
            @NonNull String customername,
            @NonNull String banknum,
            @NonNull String banktype, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<Object>> onResult) {
        register(service.upLoadUnlineInfor(signid, imgValue, customername, banknum, banktype), onError, onResult);
    }

    //获取加盟费用
    public void getFranchisePrice(
            @NonNull String province,
            @NonNull String city,
            @NonNull String area,
            @NonNull String areaStreet, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<FranchisePriceBean>> onResult) {
        register(service.getFranchisePrice(province, city, area, areaStreet), onError, onResult);
    }

    //提交合同信息
    public void upLoadContractInfor(
            @NonNull String userId,
            @NonNull String province,
            @NonNull String city,
            @NonNull String area,
            @NonNull String areaStreet,
            @NonNull String imgValue,
            @NonNull String companyName,
            @NonNull String identityNum,
            @NonNull String legalPerson, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<UploadContractinforBean>> onResult) {
        register(service.upLoadContractInfor(userId, province, city, area, areaStreet, imgValue, companyName, identityNum,
                legalPerson), onError, onResult);
    }

    //获取加盟费用
    public void uploadUserInfor(
            @NonNull String realName,
            @NonNull String phone, @NonNull String address, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<PersonalBean>> onResult) {
        register(service.uploadUserInfor(realName, phone, address), onError, onResult);
    }

    //保存代理账户和密码
    public void uploadSignCount(
            @NonNull String userId,
            @NonNull String userName,
            @NonNull String password, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<String>> onResult) {
        register(service.uploadSignCount(userId, userName, password), onError, onResult);
    }

    //保存代理账户和密码
    public void login(
            @NonNull String number,
            @NonNull String Password, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<UserInforBean>> onResult) {
        register(service.login(number, Password), onError, onResult);
    }

    //通过code查询
    public void insertByCodes(
            @NonNull String codes, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<ProvinceBean>>> onResult) {
        register(service.insertByCodes(codes), onError, onResult);
    }

    //获取账户名
    public void getCount(
            @NonNull String userId, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<UseCountBean>> onResult) {
        register(service.getCount(userId), onError, onResult);
    }

    //获取银行账户信息
    public void getBankInfo(@NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<BankInfoBean>> onResult) {
        register(service.getBankInfo(), onError, onResult);
    }

    public class HasSignBean {
        public boolean hasSigned;
    }
}
