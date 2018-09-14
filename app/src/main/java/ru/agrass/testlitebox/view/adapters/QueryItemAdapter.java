package ru.agrass.testlitebox.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.agrass.testlitebox.R;
import ru.agrass.testlitebox.model.entity.Page;

public class QueryItemAdapter extends RecyclerView.Adapter<QueryItemAdapter.ViewHolder> {


    private ArrayList<Page> list;

    public QueryItemAdapter(ArrayList<Page> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_query, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    public void clear() {
        if (list.isEmpty()) return;
        list.clear();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public ArrayList<Page> getList() {
        return list;
    }

    public void add(Page page) {
        list.add(page);
    }

    public void addAll(Page... pages) {
        Collections.addAll(list, pages);
    }

    public void addAll(List<Page> pages) {
        list.addAll(pages);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_query_url) TextView textViewUrl;
        @BindView(R.id.item_query_title) TextView textViewTitle;
        @BindView(R.id.item_query_description) TextView textViewDescription;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Page page) {
            textViewUrl.setText(page.getUrl());
            textViewTitle.setText(page.getTitle());
            textViewDescription.setText(page.getDescription());
        }
    }
}
