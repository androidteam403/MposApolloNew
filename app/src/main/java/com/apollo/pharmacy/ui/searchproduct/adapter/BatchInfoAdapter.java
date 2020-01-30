package com.apollo.pharmacy.ui.searchproduct.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ViewSearchProductBatchInfoBinding;
import com.apollo.pharmacy.ui.searchproduct.model.ProductBatchPojo;

import java.util.ArrayList;

public class BatchInfoAdapter extends RecyclerView.Adapter<BatchInfoAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<ProductBatchPojo> productBatchArrayList;

    public BatchInfoAdapter(Activity activity, ArrayList<ProductBatchPojo> productBatchArrayList) {
        this.activity = activity;
        this.productBatchArrayList = productBatchArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewSearchProductBatchInfoBinding viewSearchProductBatchInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_search_product_batch_info, parent, false);
        return new ViewHolder(viewSearchProductBatchInfoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductBatchPojo item = productBatchArrayList.get(position);
        holder.viewSearchProductBatchInfoBinding.setBatchInfo(item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewSearchProductBatchInfoBinding viewSearchProductBatchInfoBinding;

        public ViewHolder(@NonNull ViewSearchProductBatchInfoBinding viewSearchProductBatchInfoBinding) {
            super(viewSearchProductBatchInfoBinding.getRoot());
            this.viewSearchProductBatchInfoBinding = viewSearchProductBatchInfoBinding;
        }
    }

    @Override
    public int getItemCount() {
        return productBatchArrayList.size();
    }
}