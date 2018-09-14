package ru.agrass.testlitebox.model.database;

import java.util.List;

import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.model.entity.Query;

public interface DataSource {

    long saveQuery(Query query);

    void savePage(Page page);

    long getLastQuery();

    List<Page> getPagesByQueryUid(long uid);

}
