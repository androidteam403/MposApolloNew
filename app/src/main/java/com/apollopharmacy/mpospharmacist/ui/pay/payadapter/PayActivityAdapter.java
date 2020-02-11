package com.apollopharmacy.mpospharmacist.ui.pay.payadapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.PayActivityAdapterBinding;

import java.util.ArrayList;


public class PayActivityAdapter extends RecyclerView.Adapter<PayActivityAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<PayAdapterModel> arrPayAdapterModel;
    PayActivityAdapterBinding payAdapterBinding;

    public PayActivityAdapter(Activity activity, ArrayList<PayAdapterModel> arrPayAdapterModel) {
        this.activity = activity;
        this.arrPayAdapterModel = arrPayAdapterModel;
    }

    @NonNull
    @Override
    public PayActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        payAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.pay_activity_adapter, parent, false);
        return new PayActivityAdapter.ViewHolder(payAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PayActivityAdapter.ViewHolder holder, int position) {
        PayAdapterModel item = arrPayAdapterModel.get(position);
        holder.payAdapterBinding.setModel(item);
    }

    @Override
    public int getItemCount() {
        return arrPayAdapterModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public PayActivityAdapterBinding payAdapterBinding;

        public ViewHolder(@NonNull PayActivityAdapterBinding payAdapterBinding) {
            super(payAdapterBinding.getRoot());
            this.payAdapterBinding = payAdapterBinding;
        }
    }
}