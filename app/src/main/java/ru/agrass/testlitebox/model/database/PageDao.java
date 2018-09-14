package ru.agrass.testlitebox.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.agrass.testlitebox.model.entity.Page;

@Dao
interface PageDao {

    @Insert
    long insert(Page page);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Page... pages);

    @Query("SELECT * FROM pages INNER JOIN queries " +
            "ON pages.queryUid = queries.uid WHERE queries.uid = :uid")
    List<Page> getPagesByQueryUid(final long uid);
}
