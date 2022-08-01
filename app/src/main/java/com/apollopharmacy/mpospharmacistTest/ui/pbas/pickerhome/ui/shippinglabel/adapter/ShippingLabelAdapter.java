package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterShippingLabelBinding;

public class ShippingLabelAdapter extends RecyclerView.Adapter<ShippingLabelAdapter.ViewHolder> {
    private Context mContext;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterShippingLabelBinding shippingLabelBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_shipping_label, parent, false);
        return new ShippingLabelAdapter.ViewHolder(shippingLabelBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterShippingLabelBinding shippingLabelBinding;

        public ViewHolder(@NonNull AdapterShippingLabelBinding shippingLabelBinding) {
            super(shippingLabelBinding.getRoot());
            this.shippingLabelBinding = shippingLabelBinding;
        }
    }
}
