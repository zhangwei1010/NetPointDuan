package com.net.point.response;

import android.os.Parcel;
import android.os.Parcelable;

public class PostStationBean implements Parcelable {
    /**
     * "id": 4,
     * "name": null,
     * "incNumber": "4111000",
     * "address": "东风街12号",
     * "longtitude": 125.24,
     * "latitude": 45.2154,
     * "remark": "备注信息",
     * "delFlage": 0,
     * "distance": "5264.71km",
     * "crtTime": "2019-07-29 10:20:10",
     * "contactsPeople": "张三丰",
     * "phoneNum": "13835351484"
     */

    public int id;
    public String name;
    public String incNumber;
    public String address;
    public double longtitude;
    public double latitude;
    public String remark;
    public int delFlage;
    public String distance;
    public String crtTime;
    public String contactsPeople;
    public String phoneNum;

    protected PostStationBean(Parcel in) {
        id = in.readInt();
        name = in.readString();
        incNumber = in.readString();
        address = in.readString();
        longtitude = in.readDouble();
        latitude = in.readDouble();
        remark = in.readString();
        delFlage = in.readInt();
        distance = in.readString();
        crtTime = in.readString();
        contactsPeople = in.readString();
        phoneNum = in.readString();
    }

    public static final Creator<PostStationBean> CREATOR = new Creator<PostStationBean>() {
        @Override
        public PostStationBean createFromParcel(Parcel in) {
            return new PostStationBean(in);
        }

        @Override
        public PostStationBean[] newArray(int size) {
            return new PostStationBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(incNumber);
        dest.writeString(address);
        dest.writeDouble(longtitude);
        dest.writeDouble(latitude);
        dest.writeString(remark);
        dest.writeInt(delFlage);
        dest.writeString(distance);
        dest.writeString(crtTime);
        dest.writeString(contactsPeople);
        dest.writeString(phoneNum);
    }
}
