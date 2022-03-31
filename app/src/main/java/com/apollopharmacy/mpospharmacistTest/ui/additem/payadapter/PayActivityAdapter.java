package com.apollopharmacy.mpospharmacistTest.ui.additem.payadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.PayActivityAdapterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.AddItemMvpView;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PayActivityAdapterBinding payAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.pay_activity_adapter, parent, false);
        return new ViewHolder(payAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PayAdapterModel item = arrPayAdapterModel.get(position);
        holder.payAdapterBinding.setModel(item);
        holder.payAdapterBinding.closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addItemMvpView != null) {
                    if (!item.isAmountVoid()) {
                        item.setAmountVoid(true);
                        addItemMvpView.toRemovePayedAmount(position, holder);
                    } else {
                        item.setAmountVoid(false);
                        addItemMvpView.toAddPayedAmount(item, position);
                    }
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