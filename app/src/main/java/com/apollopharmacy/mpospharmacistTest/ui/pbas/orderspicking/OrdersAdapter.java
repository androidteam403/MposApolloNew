package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderspicking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrdersLayoutBinding;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context context;

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrdersLayoutBinding ordersLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_orders_layout, parent, false);
        return new ViewHolder(ordersLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 2) {
            holder.ordersLayoutBinding.bottomStripLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrdersLayoutBinding ordersLayoutBinding;

        public ViewHolder(@NonNull AdapterOrdersLayoutBinding ordersLayoutBinding) {
            super(ordersLayoutBinding.getRoot());
            this.ordersLayoutBinding = ordersLayoutBinding;
        }
    }
}
