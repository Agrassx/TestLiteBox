package ru.agrass.testlitebox.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "queries")
public class Query {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "query")
    private String query;

    public Query(long uid, String query, long date) {
        setUid(uid);
        setQuery(query);
        setDate(date);
    }

    @Ignore
    public Query(String query,long date) {
        setQuery(query);
        setDate(date);
    }


    public String getQuery() {
        return query;
    }

    public long getUid() {
        return uid;
    }

    public long getDate() {
        return date;
    }

    private void setUid(long uid) {
        this.uid = uid;
    }

    private void setDate(long date) {
        this.date = date;
    }

    private void setQuery(String query) {
        this.query = query;
    }
}
