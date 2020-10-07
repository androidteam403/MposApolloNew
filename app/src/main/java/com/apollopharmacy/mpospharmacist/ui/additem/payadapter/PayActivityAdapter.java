package com.apollopharmacy.mpospharmacist.ui.additem.payadapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.PayActivityAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.AddItemMvpView;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;

import java.util.ArrayList;


public class PayActivityAdapter extends RecyclerView.Adapter<PayActivityAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<PayAdapterModel> arrPayAdapterModel;
    private AddItemMvpView addItemMvpView;

    public PayActivityAdapter(Activity activity, ArrayList<PayAdapterModel> arrPayAdapterModel, AddItemMvpView addItemMvpView) {
        this.activity = activity;
        this.arrPayAdapterModel = arrPayAdapterModel;
        this.addItemMvpView = addItemMvpView;
    }

    @NonNull
    @Override
    public PayActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PayActivityAdapterBinding  payAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.pay_activity_adapter, parent, false);
        return new PayActivityAdapter.ViewHolder(payAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PayActivityAdapter.ViewHolder holder, int position) {
        PayAdapterModel item = arrPayAdapterModel.get(position);
        holder.payAdapterBinding.setModel(item);
        addItemMvpView.cardModePosition(position);
        holder.payAdapterBinding.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItemMvpView != null) {
                    if (!item.isAmountVoid()) {
                        item.setAmountVoid(true);
                        holder.payAdapterBinding.overallAmount.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.payAdapterBinding.rupee.setTextColor(Color.parseColor("#FF0000"));
                        holder.payAdapterBinding.rupee.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        holder.payAdapterBinding.overallAmount.setTextColor(Color.parseColor("#FF0000"));
                        addItemMvpView.toRemovePayedAmount(item,position);

                    } else {
                        item.setAmountVoid(false);
                        holder.payAdapterBinding.overallAmount.setPaintFlags(0);
                        holder.payAdapterBinding.overallAmount.setTextColor(Color.parseColor("#027d9e"));
                        holder.payAdapterBinding.rupee.setTextColor(Color.parseColor("#027d9e"));
                        holder.payAdapterBinding.rupee.setPaintFlags(0);
                        addItemMvpView.toAddPayedAmount(item,position);

                    }
//                    arrPayAdapterModel.remove(position);
//                    notifyItemChanged(position);
//                    addItemMvpView.paymentVoidOption(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrPayAdapterModel.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PayActivityAdapterBinding payAdapterBinding;

        public ViewHolder(@NonNull PayActivityAdapterBinding payAdapterBinding) {
            super(payAdapterBinding.getRoot());
            this.payAdapterBinding = payAdapterBinding;
        }
    }
}