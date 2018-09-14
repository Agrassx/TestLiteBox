package ru.agrass.testlitebox.view.base;

public interface IPresenter<V extends IView> {

    void onAttach(V view);

    void onDetach();

    void resume();

    void pause();

    void destroy();

}
