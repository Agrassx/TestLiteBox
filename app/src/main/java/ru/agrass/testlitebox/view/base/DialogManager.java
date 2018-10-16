package ru.agrass.testlitebox.view.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class DialogManager<D extends DialogFragment> {

    private DialogManagerView dialogManagerView;
    private FragmentManager fragmentManager;
    private DialogView<D> view;
    private String tag;
    private D dialog;

    public DialogManager(@NonNull FragmentManager fragmentManager, @Nullable DialogView<D> view) {
        this.fragmentManager = fragmentManager;
        this.view = view;
    }

    public DialogManager(@NonNull FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void setDialogManagerView(@Nullable DialogManagerView dialogManagerView) {
        this.dialogManagerView = dialogManagerView;
    }

    public void setView(@Nullable DialogView<D> view) {
        this.view = view;
    }

    public void showDialog(@NonNull D dialog, String tag) {
        if (this.tag != null) {
            destroyPreviousDialogFragment(this.tag);
        }

        this.dialog = dialog;
        this.tag = tag;
        this.dialog.show(fragmentManager, tag);

        if (view == null) {
            return;
        }
        view.onDialogShow(this.dialog);
    }

    public void hideDialog() {
        if (dialog == null) {
            return;
        }
        if (view != null) {
            view.onDialogHide();
        }
        dialog.dismissAllowingStateLoss();
        dialog = null;
        tag = null;
    }

    public void destroy() {
        if (dialogManagerView != null) {
            dialogManagerView.onDialogManagerDestroy();
        }
        hideDialog();
        dialogManagerView = null;
        fragmentManager = null;
        view = null;
    }

    @Nullable
    public D getDialog() {
        return dialog;
    }

    private void destroyPreviousDialogFragment(String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(tag);
        if (prev != null) {
            transaction.remove(prev);
        }
        transaction.commitAllowingStateLoss();
    }

    public interface DialogView<D extends DialogFragment> {
        void onDialogShow(D dialog);

        void onDialogHide();
    }

    public interface DialogManagerView {
        void onDialogManagerDestroy();
    }

}




