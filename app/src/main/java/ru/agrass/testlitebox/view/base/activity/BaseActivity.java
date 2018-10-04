package ru.agrass.testlitebox.view.base.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.agrass.testlitebox.view.base.IView;
import ru.agrass.testlitebox.view.base.LoadingDialog;

public abstract class BaseActivity<V extends IView> extends AppCompatActivity implements ActivityView {

    private LoadingDialog loadingDialog;
    private V view;


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingDialog = null;
    }
}
