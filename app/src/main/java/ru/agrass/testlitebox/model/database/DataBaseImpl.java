package ru.agrass.testlitebox.model.database;


import android.content.Context;

import java.util.List;

import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.model.entity.Query;

public class DataBaseImpl implements DataSource {

    private static DataBaseImpl instance;
    private final QueryHistoryDataBase db;

    public static DataBaseImpl getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseImpl(context);
        }
        return instance;
    }

    private DataBaseImpl(Context context) {
        this.db = QueryHistoryDataBase.getDatabase(context);
        instance = this;
    }

    @Override
    public long saveQuery(Query query) {
        return db.queryDao().insert(query);
    }

    @Override
    public void savePage(Page page) {
        db.pageDao().insert(page);
    }

    @Override
    public long getLastQuery() {
        return db.queryDao().getLastQuery();
    }

    @Override
    public List<Page> getPagesByQueryUid(long uid) {
        return db.pageDao().getPagesByQueryUid(uid);
    }
}
