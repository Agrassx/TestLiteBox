package ru.agrass.testlitebox.view.base.presenter;

import android.app.DialogFragment;

import io.reactivex.disposables.CompositeDisposable;
import ru.agrass.testlitebox.model.network.NetworkAPI;
import ru.agrass.testlitebox.model.network.NetworkClient;
import ru.agrass.testlitebox.utils.rx.AppSchedulerProvider;
import ru.agrass.testlitebox.utils.rx.SchedulerProvider;
import ru.agrass.testlitebox.view.base.IView;

public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final SchedulerProvider schedulerProvider = new AppSchedulerProvider();
    private final NetworkAPI api = NetworkClient.getRetrofit().create(NetworkAPI.class);
    private V view;

    protected BasePresenter() { }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    protected SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    protected NetworkAPI getApi() {
        return api;
    }

    public V getView() {
        return view;
    }

    protected void errorHandler(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
        this.compositeDisposable.dispose();
    }

}
