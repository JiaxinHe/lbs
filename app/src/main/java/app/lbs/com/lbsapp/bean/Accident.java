package app.lbs.com.lbsapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 事故记录
 */
public class Accident implements Parcelable {
    /**
     * 事故ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 时间
     */
    private String time;

    /**
     * 纬度值
     */
    private Float lat;

    /**
     * 经度值
     */
    private Float lng;

    /**
     * 地点
     */
    private String address;

    /**
     * 详情描述
     */
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeString(this.time);
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeString(this.address);
        dest.writeString(this.description);
    }

    public Accident() {
    }

    protected Accident(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.userId = (Long) in.readValue(Long.class.getClassLoader());
        this.time = in.readString();
        this.lat = (Float) in.readValue(Float.class.getClassLoader());
        this.lng = (Float) in.readValue(Float.class.getClassLoader());
        this.address = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Accident> CREATOR = new Parcelable.Creator<Accident>() {
        @Override
        public Accident createFromParcel(Parcel source) {
            return new Accident(source);
        }

        @Override
        public Accident[] newArray(int size) {
            return new Accident[size];
        }
    };
}
