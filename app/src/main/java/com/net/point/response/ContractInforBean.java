package com.net.point.response;

import android.os.Parcel;
import android.os.Parcelable;


public class ContractInforBean implements Parcelable {
    public String provinceCode;
    public String cityCode;
    public String areaCode;
    public String areaStreetCode;
    public String provinceName;
    public String cityName;
    public String areaName;
    public String areaStreetName;
    public String companyName;
    public String legalPerson;
    public String identityNum;
    public String checkRemark;

    public ContractInforBean() {
    }

    public ContractInforBean(Parcel in) {
        provinceCode = in.readString();
        cityCode = in.readString();
        areaCode = in.readString();
        areaStreetCode = in.readString();
        provinceName = in.readString();
        cityName = in.readString();
        areaName = in.readString();
        areaStreetName = in.readString();
        companyName = in.readString();
        legalPerson = in.readString();
        identityNum = in.readString();
        checkRemark = in.readString();
    }

    public static final Creator<ContractInforBean> CREATOR = new Creator<ContractInforBean>() {
        @Override
        public ContractInforBean createFromParcel(Parcel in) {
            return new ContractInforBean(in);
        }

        @Override
        public ContractInforBean[] newArray(int size) {
            return new ContractInforBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(provinceCode);
        parcel.writeString(cityCode);
        parcel.writeString(areaCode);
        parcel.writeString(areaStreetCode);
        parcel.writeString(provinceName);
        parcel.writeString(cityName);
        parcel.writeString(areaName);
        parcel.writeString(areaStreetName);
        parcel.writeString(companyName);
        parcel.writeString(legalPerson);
        parcel.writeString(identityNum);
        parcel.writeString(checkRemark);
    }
}
