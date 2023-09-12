package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrdersListLayoutBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.allorders.AllOrdersMvpView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Context context;
    private AllOrdersMvpView mvpView;

    public OrdersAdapter(Context context, AllOrdersMvpView mvpView) {
        this.context = context;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrdersListLayoutBinding ordersListLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_orders_list_layout, parent, false);
        return new ViewHolder(ordersListLayoutBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.ordersListLayoutBinding.status.setText("Inprogress");
            holder.ordersListLayoutBinding.items.setText("2");
            holder.ordersListLayoutBinding.status.setTextColor(Color.parseColor("#85b0a2"));
            holder.ordersListLayoutBinding.leftStrip.setBackgroundColor(Color.parseColor("#80b6a4"));
            holder.ordersListLayoutBinding.inProgressLayout.setVisibility(View.VISIBLE);
            holder.ordersListLayoutBinding.notAllocatedLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.requestLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.onHoldLayout.setVisibility(View.GONE);
        } else if (position == 1) {
            holder.ordersListLayoutBinding.status.setText("Not Allocated");
            holder.ordersListLayoutBinding.items.setText("5");
            holder.ordersListLayoutBinding.status.setTextColor(Color.parseColor("#bba7d0"));
            holder.ordersListLayoutBinding.leftStrip.setBackgroundColor(Color.parseColor("#c0a7da"));
            holder.ordersListLayoutBinding.inProgressLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.notAllocatedLayout.setVisibility(View.VISIBLE);
            holder.ordersListLayoutBinding.requestLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.onHoldLayout.setVisibility(View.GONE);
        } else if (position == 2) {
            holder.ordersListLayoutBinding.status.setText("Request");
            holder.ordersListLayoutBinding.items.setText("9");
            holder.ordersListLayoutBinding.status.setTextColor(Color.parseColor("#ebb43e"));
            holder.ordersListLayoutBinding.leftStrip.setBackgroundColor(Color.parseColor("#f5b02b"));
            holder.ordersListLayoutBinding.inProgressLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.notAllocatedLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.requestLayout.setVisibility(View.VISIBLE);
            holder.ordersListLayoutBinding.onHoldLayout.setVisibility(View.GONE);
        } else if (position == 3) {
            holder.ordersListLayoutBinding.status.setText("Onhold");
            holder.ordersListLayoutBinding.items.setText("7");
            holder.ordersListLayoutBinding.status.setTextColor(Color.parseColor("#d59589"));
            holder.ordersListLayoutBinding.leftStrip.setBackgroundColor(Color.parseColor("#ee8679"));
            holder.ordersListLayoutBinding.inProgressLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.notAllocatedLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.requestLayout.setVisibility(View.GONE);
            holder.ordersListLayoutBinding.onHoldLayout.setVisibility(View.VISIBLE);
        }

        holder.ordersListLayoutBinding.parentLayout.setOnClickListener(v -> {
            mvpView.onClickOrder(position);
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrdersListLayoutBinding ordersListLayoutBinding;

        public ViewHolder(@NonNull AdapterOrdersListLayoutBinding ordersListLayoutBinding) {
            super(ordersListLayoutBinding.getRoot());
            this.ordersListLayoutBinding = ordersListLayoutBinding;
        }
    }
}
