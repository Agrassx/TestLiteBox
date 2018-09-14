package ru.agrass.testlitebox.view.main;

import android.content.Context;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import ru.agrass.testlitebox.model.database.DataBaseImpl;
import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.model.entity.Query;
import ru.agrass.testlitebox.model.network.responses.QueryResponse;
import ru.agrass.testlitebox.view.base.BasePresenter;

public class MainPresenter<V extends MainView> extends BasePresenter<V> implements IMainPresenter<V> {

    private static final String PLUS_CHAR = "+";
    private static final String SPACE_CHAR = " ";
    private static final String TAG = MainPresenter.class.getSimpleName();

    private DataBaseImpl dataBase;
    private Disposable queryDisposable;
    private Disposable saveQueryDisposable;
    private Disposable savePageDisposable;
    private Disposable lastQueryDisposable;
    private Disposable lastPagesDisposable;

    public MainPresenter(Context context) {
        this.dataBase = DataBaseImpl.getInstance(context);
    }

    @Override
    public void onAttach(V view) {
        super.onAttach(view);
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {
        getCompositeDisposable().clear();
    }

    @Override
    public void destroy() {
        getCompositeDisposable().clear();
    }

    private void saveQuery(String query) {
        saveQueryDisposable = Observable
                .fromCallable(() -> dataBase.saveQuery(
                        new Query(query, System.currentTimeMillis())
                ))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(id -> getPages(id, query), this::ErrorHandler);
        getCompositeDisposable().add(saveQueryDisposable);
    }

    private void getPages(long queryUid, String query) {
        queryDisposable = getApi()
                .getPages(query.replace(SPACE_CHAR, PLUS_CHAR))
                .subscribeOn(getSchedulerProvider().io())
                .map(QueryResponse::getItems)
                .observeOn(getSchedulerProvider().ui())
                .subscribe(pageList -> {
                    savePages(queryUid, pageList);
                }, this::ErrorHandler);
        getCompositeDisposable().add(queryDisposable);
    }

    private void getPagesByQueryUid(long uid) {
        if (lastQueryDisposable != null && !lastQueryDisposable.isDisposed())
            lastQueryDisposable.dispose();

        lastPagesDisposable = Observable
                .fromCallable(() -> dataBase.getPagesByQueryUid(uid))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::showPages, this::ErrorHandler);
    }

    @Override
    public void query(String q) {
        getView().showLoadingDialog();
        saveQuery(q);
    }

    @Override
    public void getLastPages() {
        getView().showLoadingDialog();
        lastQueryDisposable = Observable
                .fromCallable(() -> dataBase.getLastQuery())
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::getPagesByQueryUid, this::ErrorHandler);
        getCompositeDisposable().add(lastQueryDisposable);
    }

    private void savePages(long uidQuery, List<Page> list) {
        savePageDisposable = Observable
                .fromIterable(list)
                .subscribeOn(getSchedulerProvider().io())
                .doOnNext(page -> {
                    page.setQueryUid(uidQuery);
                    dataBase.savePage(page);
                })
                .toList()
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::showPages, this::ErrorHandler);
        getCompositeDisposable().add(savePageDisposable);
    }

    private void showPages(List<Page> pageList) {
        getView().closeLoadingDialog();
        if (savePageDisposable != null && !savePageDisposable.isDisposed())
            savePageDisposable.dispose();

        if (queryDisposable != null && !queryDisposable.isDisposed())
            queryDisposable.dispose();

        if (lastPagesDisposable != null && !lastPagesDisposable.isDisposed())
            lastPagesDisposable.dispose();

        getView().showPages(pageList);
    }

    private void ErrorHandler(Throwable throwable) {
        if (saveQueryDisposable != null && !saveQueryDisposable.isDisposed())
            saveQueryDisposable.dispose();
        if (queryDisposable != null && !queryDisposable.isDisposed())
            queryDisposable.dispose();
        if (savePageDisposable != null && !savePageDisposable.isDisposed())
            savePageDisposable.dispose();
        if (lastQueryDisposable != null && !lastQueryDisposable.isDisposed())
            lastQueryDisposable.dispose();
        if (lastPagesDisposable != null && !lastPagesDisposable.isDisposed())
            lastPagesDisposable.dispose();

        getView().closeLoadingDialog();
        throwable.printStackTrace();
        getView().showMessage("Something wrong");
    }


}
