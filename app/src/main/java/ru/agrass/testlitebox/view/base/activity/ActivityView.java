package ru.agrass.testlitebox.view.base.activity;

import ru.agrass.testlitebox.view.base.IView;

public interface ActivityView extends IView {
    void showDialog(String message);
    void showDialog();
    void hideDialog();
}
