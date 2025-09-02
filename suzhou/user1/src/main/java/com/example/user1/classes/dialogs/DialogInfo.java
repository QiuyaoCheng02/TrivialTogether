package com.example.user1.classes.dialogs;

import android.graphics.PointF;

import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;

public class DialogInfo implements Parcelable {
    private int imageResId;
    private String name;
    private String time;
    private String tag;
    private PointF pointF;
    private String intro;

    public DialogInfo(int imageResId, String name, String time, String tag, PointF pointF, String intro) {
        this.imageResId = imageResId;
        this.name = name;
        this.time = time;
        this.tag = tag;
        this.pointF = pointF;
        this.intro = intro;
    }

    protected DialogInfo(Parcel in) {
        imageResId = in.readInt();
        name = in.readString();
        time = in.readString();
        tag = in.readString();
        pointF = in.readParcelable(PointF.class.getClassLoader());
        intro=in.readString();
    }

    public static final Creator<DialogInfo> CREATOR = new Creator<DialogInfo>() {
        @Override
        public DialogInfo createFromParcel(Parcel in) {
            return new DialogInfo(in);
        }

        @Override
        public DialogInfo[] newArray(int size) {
            return new DialogInfo[size];
        }
    };

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getTag() {
        return tag;
    }

    public PointF getPointF() {
        return pointF;
    }

    public String getIntro() {
        return intro;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageResId);
        dest.writeString(name);
        dest.writeString(time);
        dest.writeString(tag);
        dest.writeParcelable(pointF, flags);
        dest.writeString(intro);
    }
}
