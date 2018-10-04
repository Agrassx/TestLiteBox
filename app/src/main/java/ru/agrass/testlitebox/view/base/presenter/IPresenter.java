package ru.agrass.testlitebox.view.base.presenter;

import ru.agrass.testlitebox.view.base.IView;

public interface IPresenter<V extends IView> {

    void onAttach(V view);

    void onDetach();

    void resume();

    void pause();

    void destroy();

}
