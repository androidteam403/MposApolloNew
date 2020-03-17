package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.PayActivityAdapterBinding;
import com.apollopharmacy.mpospharmacist.databinding.ViewOrderHistoryPaymentBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.model.OrderReturnModel;
import com.apollopharmacy.mpospharmacist.ui.pay.payadapter.PayAdapterModel;

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