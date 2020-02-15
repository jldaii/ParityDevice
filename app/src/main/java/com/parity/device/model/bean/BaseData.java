package com.parity.device.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.corelibs.subscriber.ResponseHandler;

public class BaseData<T> implements Parcelable, ResponseHandler.IBaseData {
    public int status;
    public String msg;
    public String code;
    public T data;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.status);
        dest.writeString(this.msg);
        dest.writeString(this.code);
        dest.writeParcelable((Parcelable) this.data, flags);
    }

    public BaseData() {
    }

    protected BaseData(Parcel in) {
        this.status = in.readInt();
        this.msg = in.readString();
        this.code = in.readString();
        this.data = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<BaseData> CREATOR = new Creator<BaseData>() {
        @Override
        public BaseData createFromParcel(Parcel source) {
            return new BaseData(source);
        }

        @Override
        public BaseData[] newArray(int size) {
            return new BaseData[size];
        }
    };

    @Override
    public boolean isSuccess() {
        return status==0;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public Object data() {
        return data;
    }


    @Override
    public String msg() {
        return msg;
    }


    public String code() {
        return code;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
