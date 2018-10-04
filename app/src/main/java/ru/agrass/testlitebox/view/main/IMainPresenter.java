package ru.agrass.testlitebox.view.main;

import ru.agrass.testlitebox.view.base.presenter.IPresenter;

public interface IMainPresenter<V extends MainView> extends IPresenter<V> {
    void query(String q);
    void getLastPages();
}
