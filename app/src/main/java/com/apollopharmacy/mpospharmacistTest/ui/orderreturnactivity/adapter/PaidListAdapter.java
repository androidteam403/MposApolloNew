package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewOrderHistoryPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.OrderReturnModel;

import java.util.ArrayList;

public class PaidListAdapter extends RecyclerView.Adapter<PaidListAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<OrderReturnModel> arrPayAdapterModel;
    ViewOrderHistoryPaymentBinding payAdapterBinding;

    public PaidListAdapter(Activity activity, ArrayList<OrderReturnModel> arrPayAdapterModel) {
        this.activity = activity;
        this.arrPayAdapterModel = arrPayAdapterModel;
    }

    @NonNull
    @Override
    public PaidListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        payAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_order_history_payment, parent, false);
        return new PaidListAdapter.ViewHolder(payAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaidListAdapter.ViewHolder holder, int position) {
        OrderReturnModel item = arrPayAdapterModel.get(position);
        holder.historyPaymentBinding.setModel(item);
    }

    @Override
    public int getItemCount() {
        return arrPayAdapterModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewOrderHistoryPaymentBinding historyPaymentBinding;

        public ViewHolder(@NonNull ViewOrderHistoryPaymentBinding historyPaymentBinding) {
            super(historyPaymentBinding.getRoot());
            this.historyPaymentBinding = historyPaymentBinding;
        }
    }
}