package ru.agrass.testlitebox.view.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.agrass.testlitebox.view.base.DialogManager;
import ru.agrass.testlitebox.view.base.IView;
import ru.agrass.testlitebox.view.base.LoadingDialog;

public abstract class BaseActivity<V extends IView> extends AppCompatActivity implements ActivityView {

    private LoadingDialog loadingDialog;
    private DialogManager dialogManager;
    private V view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogManager = new DialogManager<LoadingDialog>(getSupportFragmentManager());
    }

    public V getView() {
        return view;
    }

    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void showDialog(String message) {
        if (loadingDialog == null) {
            destroyPreviousDialogFragment();
            loadingDialog = LoadingDialog.newInstance(message);
            loadingDialog.show(getSupportFragmentManager(), LoadingDialog.getTAG());
            return;
        }
        loadingDialog.setMessage(message);
    }

    private void destroyPreviousDialogFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(LoadingDialog.getTAG());
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.commit();
    }


    public void showDialog1(DialogFragment dialog, String tag) {
        dialogManager.showDialog(dialog, tag);
    }

    @Override
    public void showDialog() {
        destroyPreviousDialogFragment();
        loadingDialog = LoadingDialog.newInstance();
        loadingDialog.show(getSupportFragmentManager(), LoadingDialog.getTAG());
    }

    @Override
    public void hideDialog() {
        if (loadingDialog == null) {
            return;
        }
        loadingDialog.dismissAllowingStateLoss();
        loadingDialog = null;
    }

    public DialogManager getDialogManager() {
        return dialogManager;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog = null;
    }
}
