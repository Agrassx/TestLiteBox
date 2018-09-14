package ru.agrass.testlitebox.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import java.util.List;

import ru.agrass.testlitebox.model.entity.Query;

@Dao
interface QueryDao {

    @Insert
    long insert(Query query);

    @android.arch.persistence.room.Query("SELECT * FROM queries")
    List<Query> getAllQueries();

    @android.arch.persistence.room.Query("Select uid FROM queries " +
            "WHERE date = (SELECT MAX(date) FROM queries)")
    long getLastQuery();

}
