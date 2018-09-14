package ru.agrass.testlitebox.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.model.entity.Query;


@Database(entities = {Page.class, Query.class}, version = 1)
public abstract class QueryHistoryDataBase extends RoomDatabase {

    private static final String NAME_DATABASE = "timer-database";
    private static QueryHistoryDataBase mInstance;

    public abstract PageDao pageDao();
    public abstract QueryDao queryDao();


    public static QueryHistoryDataBase getDatabase(final Context context) {
        if (mInstance == null) {
            synchronized (QueryHistoryDataBase.class) {
                mInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        QueryHistoryDataBase.class,
                        NAME_DATABASE
                ).build();
            }
        }
        return mInstance;
    }

}
