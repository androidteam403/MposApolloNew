package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterInwardProcessLineItemsBinding;

public class InwardProcessLineItemsAdapter extends RecyclerView.Adapter<InwardProcessLineItemsAdapter.ViewHolder> {
    @NonNull
    @Override
    public InwardProcessLineItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_inward_process_line_items, parent, false);
        return new InwardProcessLineItemsAdapter.ViewHolder(inwardProcessLineItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InwardProcessLineItemsAdapter.ViewHolder holder, int position) {
        holder.inwardProcessLineItemsBinding.physicalExp.setOnClickListener(v -> {

        });
        holder.inwardProcessLineItemsBinding.topLayout.setOnClickListener(v -> {
            if (holder.inwardProcessLineItemsBinding.extraLayout.getVisibility() == View.VISIBLE) {
                holder.inwardProcessLineItemsBinding.extraLayout.setVisibility(View.GONE);
                holder.inwardProcessLineItemsBinding.dropDownIcon.animate().rotation(90).setDuration(150).start();
            } else {
                holder.inwardProcessLineItemsBinding.extraLayout.setVisibility(View.VISIBLE);
                holder.inwardProcessLineItemsBinding.dropDownIcon.animate().rotation(270).setDuration(150).start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding;

        public ViewHolder(@NonNull AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding) {
            super(inwardProcessLineItemsBinding.getRoot());
            this.inwardProcessLineItemsBinding = inwardProcessLineItemsBinding;
        }
    }
}