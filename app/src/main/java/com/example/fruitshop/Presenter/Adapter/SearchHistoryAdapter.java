package com.example.fruitshop.Presenter.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fruitshop.Infrastructure.Data.SearchHistoryHelper;
import com.example.fruitshop.databinding.SearchHistoryViewholderBinding;

import java.util.ArrayList;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<String> histories;
    SearchHistoryViewholderBinding binding;
    SearchHistoryHelper searchHistoryHelper;

    public SearchHistoryAdapter(ArrayList<String> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public SearchHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SearchHistoryViewholderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        context = parent.getContext();
        searchHistoryHelper = new SearchHistoryHelper(context);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryAdapter.ViewHolder holder, int position) {
        String history = histories.get(position);
        holder.binding.txtHistory.setText(history);
        holder.binding.btnRemove.setOnClickListener(v->{
            searchHistoryHelper.remove(history);
            histories.remove(history);
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SearchHistoryViewholderBinding binding;
        public ViewHolder(@NonNull SearchHistoryViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
