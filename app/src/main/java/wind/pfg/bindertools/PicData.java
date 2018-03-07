package wind.pfg.bindertools;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by pengfugen on 2017/8/22.
 */

public class PicData implements Parcelable {

    public String mName;
    public int mSize;
    public byte [] mData;

    public PicData(String name, int size, byte[] data) {
        mName = name;
        mSize = size;
        mData = data;
    }

    protected PicData(Parcel in) {
        mName = in.readString();
        mSize = in.readInt();
        mData = new byte[mSize];
        in.readByteArray(mData);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mSize);
        dest.writeByteArray(mData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "name = "+mName+" size="+mSize;
    }

    public static final Creator<PicData> CREATOR = new Creator<PicData>() {
        @Override
        public PicData createFromParcel(Parcel in) {
            return new PicData(in);
        }

        @Override
        public PicData[] newArray(int size) {
            return new PicData[size];
        }
    };
}
