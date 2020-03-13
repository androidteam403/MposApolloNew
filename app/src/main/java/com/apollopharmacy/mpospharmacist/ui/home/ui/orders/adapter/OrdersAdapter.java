package com.apollopharmacy.mpospharmacist.ui.home.ui.orders.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentOrderAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<OrderListRes> ordersModelArrayList;

    public OrdersAdapter(Activity activity, ArrayList<OrderListRes> ordersModelArrayList) {
        this.activity = activity;
        this.ordersModelArrayList = ordersModelArrayList;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FragmentOrderAdapterBinding fragmentOrderAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment_order_adapter, parent, false);
        return new OrdersAdapter.ViewHolder(fragmentOrderAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        OrderListRes item = ordersModelArrayList.get(position);
        holder.fragmentOrderAdapterBinding.setOrderDetails(item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public FragmentOrderAdapterBinding fragmentOrderAdapterBinding;

        public ViewHolder(@NonNull FragmentOrderAdapterBinding fragmentOrderAdapterBinding) {
            super(fragmentOrderAdapterBinding.getRoot());
            this.fragmentOrderAdapterBinding = fragmentOrderAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return ordersModelArrayList.size();
    }
}
