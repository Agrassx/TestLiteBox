package ru.agrass.testlitebox.view.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.agrass.testlitebox.R;

public class LoadingDialog extends DialogFragment {

    private static final String TAG = LoadingDialog.class.getSimpleName();
    private static final String DIALOG_MESSAGE = "dialogMessage";
    private static final String DEFAULT_MESSAGE = "Loading...";

    private TextView textView;

    public static String getTAG() {
        return TAG;
    }

    public static LoadingDialog newInstance(String message) {
        LoadingDialog dialog = new LoadingDialog();
        Bundle args = new Bundle();
        args.putString(DIALOG_MESSAGE, message);
        dialog.setArguments(args);
        return dialog;
    }

    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }


    public void setMessage(String message) {
        if (textView == null) {
            Log.e(TAG, "TextView = null");
            return;
        }
        textView.setText(message);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_progress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.dialogProgressMessage);
        if (getArguments() == null) {
            textView.setText(DEFAULT_MESSAGE);
            return;
        }
        textView.setText(getArguments().getString(DIALOG_MESSAGE, DEFAULT_MESSAGE));
    }
}
