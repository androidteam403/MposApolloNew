package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnbillprint.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewOrderHistoryPaymentPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity.model.OrderReturnModel;

import java.util.ArrayList;

public class PaidListBillPrintAdapter extends RecyclerView.Adapter<PaidListBillPrintAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<OrderReturnModel> arrPayAdapterModel;
    ViewOrderHistoryPaymentPBinding payAdapterPBinding;

    public PaidListBillPrintAdapter(Activity activity, ArrayList<OrderReturnModel> arrPayAdapterModel) {
        this.activity = activity;
        this.arrPayAdapterModel = arrPayAdapterModel;
    }

    @NonNull
    @Override
    public PaidListBillPrintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        payAdapterPBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_order_history_payment_p, parent, false);
        return new PaidListBillPrintAdapter.ViewHolder(payAdapterPBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PaidListBillPrintAdapter.ViewHolder holder, int position) {
        OrderReturnModel item = arrPayAdapterModel.get(position);
        holder.historyPaymentBinding.setModel(item);
    }

    @Override
    public int getItemCount() {
        return arrPayAdapterModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewOrderHistoryPaymentPBinding historyPaymentBinding;

        public ViewHolder(@NonNull ViewOrderHistoryPaymentPBinding historyPaymentBinding) {
            super(historyPaymentBinding.getRoot());
            this.historyPaymentBinding = historyPaymentBinding;
        }
    }
}