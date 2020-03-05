package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrderReturnAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter.MedicinesDetailAdapter;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.OrderReturnModel;

import java.util.ArrayList;

public class OrderReturnAdapter extends RecyclerView.Adapter<OrderReturnAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<OrderReturnModel> orderReturnModelArrayList;

    public OrderReturnAdapter(Activity activity, ArrayList<OrderReturnModel> orderReturnModelArrayList) {
        this.activity = activity;
        this.orderReturnModelArrayList = orderReturnModelArrayList;
    }

    @NonNull
    @Override
    public OrderReturnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderReturnAdapterBinding orderReturnAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_return_adapter, parent, false);
        return new OrderReturnAdapter.ViewHolder(orderReturnAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderReturnAdapter.ViewHolder holder, int position) {
        OrderReturnModel item = orderReturnModelArrayList.get(position);
        holder.orderReturnAdapterBinding.setOrderreturnmodel(item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public OrderReturnAdapterBinding orderReturnAdapterBinding;

        public ViewHolder(@NonNull OrderReturnAdapterBinding orderReturnAdapterBinding) {
            super(orderReturnAdapterBinding.getRoot());
            this.orderReturnAdapterBinding = orderReturnAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return orderReturnModelArrayList.size();
    }
}