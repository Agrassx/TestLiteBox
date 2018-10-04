package ru.agrass.testlitebox.view.main;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.agrass.testlitebox.R;
import ru.agrass.testlitebox.model.entity.Page;
import ru.agrass.testlitebox.view.adapters.QueryItemAdapter;
import ru.agrass.testlitebox.view.base.activity.BaseActivity;

public class MainActivity extends BaseActivity<MainView> implements MainView {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PAGE_LIST = "page_list";

    private Unbinder unbinder;
    private SearchView searchView;
    private QueryItemAdapter adapter;
    private MainPresenter<MainView> presenter;

    @BindView(R.id.queryResultsRecyclerView) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setView(this);
        unbinder = ButterKnife.bind(this);
        mRecyclerView = findViewById(R.id.queryResultsRecyclerView);
        presenter = new MainPresenter<>(getApplicationContext());
        presenter.onAttach(this);
        if (savedInstanceState != null) {
            ArrayList<Page> pages = savedInstanceState.getParcelableArrayList(PAGE_LIST);
            adapter = new QueryItemAdapter(pages);
            initRecyclerView();
            return;
        }
        presenter.getLastPages();

        adapter = new QueryItemAdapter(new ArrayList<>());
        initRecyclerView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!adapter.isEmpty()) {
            outState.putParcelableArrayList(PAGE_LIST, adapter.getList());
        }
        super.onSaveInstanceState(outState);
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.onActionViewCollapsed();
                presenter.query(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void showPages(@NonNull List<Page> list) {
        adapter.clear();
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void showDialog(String message) {
        super.showDialog(message);
//        Либо своя реализация...
//        Переопределять методы не обязательно
    }

    @Override
    public void hideDialog() {
        super.hideDialog();
//        Либо своя реализация...
    }

    @Override
    public void showLoadingDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void closeLoadingDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
