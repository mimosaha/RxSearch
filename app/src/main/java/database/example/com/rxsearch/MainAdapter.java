package database.example.com.rxsearch;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import database.example.com.rxsearch.databinding.ItemPropertyBinding;

/**
 * * ============================================================================
 * * Copyright (C) 2018 W3 Engineers Ltd - All Rights Reserved.
 * * Unauthorized copying of this file, via any medium is strictly prohibited
 * * Proprietary and confidential
 * * ----------------------------------------------------------------------------
 * * Created by: Mimo Saha on [06-Aug-2018 at 1:13 PM].
 * * Email: mimosaha@w3engineers.com
 * * ----------------------------------------------------------------------------
 * * Project: RxSearch.
 * * Code Responsibility: <Purpose of code>
 * * ----------------------------------------------------------------------------
 * * Edited by :
 * * --> <First Editor> on [06-Aug-2018 at 1:13 PM].
 * * --> <Second Editor> on [06-Aug-2018 at 1:13 PM].
 * * ----------------------------------------------------------------------------
 * * Reviewed by :
 * * --> <First Reviewer> on [06-Aug-2018 at 1:13 PM].
 * * --> <Second Reviewer> on [06-Aug-2018 at 1:13 PM].
 * * ============================================================================
 **/
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.StringViewHolder> {

    private List<String> proList = new ArrayList<>();

    MainAdapter(List<String> proList) {
        this.proList.addAll(proList);
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPropertyBinding itemPropertyBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_property,
                parent, false);
        return new StringViewHolder(itemPropertyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        holder.bind(proList.get(position));
    }

    @Override
    public int getItemCount() {
        return proList.size();
    }

    public List<String> getProList() {
        return proList;
    }

    public void clearData() {
        proList.clear();
        notifyDataSetChanged();
    }

    public void addData(List<String> data) {
        this.proList.addAll(data);
        notifyDataSetChanged();
    }

    class StringViewHolder extends RecyclerView.ViewHolder {

        private ItemPropertyBinding itemPropertyBinding;

        public StringViewHolder(ItemPropertyBinding itemPropertyBinding) {
            super(itemPropertyBinding.getRoot());

            this.itemPropertyBinding = itemPropertyBinding;
        }

        public void bind(String property) {
            itemPropertyBinding.setProInfo(property);
            itemPropertyBinding.executePendingBindings();
        }
    }
}
