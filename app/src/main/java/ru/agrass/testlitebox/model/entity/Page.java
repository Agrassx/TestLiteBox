package ru.agrass.testlitebox.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "pages")
public class Page implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "queryUid")
    private long queryUid;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "url")
    @SerializedName("link")
    @Expose
    private String url;

    @ColumnInfo(name = "description")
    @SerializedName("snippet")
    @Expose
    private String description;

    public Page(long uid, long queryUid, String title, String url, String description) {
        setUid(uid);
        setUrl(url);
        setTitle(title);
        setQueryUid(queryUid);
        setDescription(description);
    }

    private Page(Parcel in) {
        uid = in.readLong();
        queryUid = in.readLong();
        title = in.readString();
        url = in.readString();
        description = in.readString();
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public long getUid() {
        return uid;
    }

    public String getDescription() {
        return description;
    }

    public long getQueryUid() {
        return queryUid;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    private void setUid(long uid) {
        this.uid = uid;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public void setQueryUid(long queryUid) {
        this.queryUid = queryUid;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(uid);
        parcel.writeLong(queryUid);
        parcel.writeString(url);
        parcel.writeString(title);
        parcel.writeString(description);
    }
}
