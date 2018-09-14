package ru.agrass.testlitebox.view.main;

import android.support.annotation.NonNull;

import java.util.List;

import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.view.base.IView;

interface MainView extends IView {

    void showPages(@NonNull List<Page> list);
    void showLoadingDialog();
    void closeLoadingDialog();

}
