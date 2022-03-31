package com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.FragmentOrderAdapterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.OrdersMvpView;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<CalculatePosTransactionRes> ordersModelArrayList;
    private OrdersMvpPresenter<OrdersMvpView> mPresenter;

    public OrdersAdapter(Activity activity, ArrayList<CalculatePosTransactionRes> ordersModelArrayList, OrdersMvpPresenter<OrdersMvpView> mPresenter) {
        this.activity = activity;
        this.ordersModelArrayList = ordersModelArrayList;
        this.mPresenter = mPresenter;
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
        CalculatePosTransactionRes item = ordersModelArrayList.get(position);
        holder.fragmentOrderAdapterBinding.setOrderDetails(item);
        holder.fragmentOrderAdapterBinding.setCallback(mPresenter);
        if (item.getTransType() == 2) {//order cancelled
            holder.fragmentOrderAdapterBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.thick_green));
            holder.fragmentOrderAdapterBinding.orderId.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.receiptId.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.orderDate.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.custName.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.amount.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.custNumber.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else if (item.getTransType() == 1) {//partial return
            holder.fragmentOrderAdapterBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.transaction_id_color));
            holder.fragmentOrderAdapterBinding.orderId.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.receiptId.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.orderDate.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.custName.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.amount.setTextColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.custNumber.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else if (item.getTransType() == 0) {//order placed
            holder.fragmentOrderAdapterBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            holder.fragmentOrderAdapterBinding.orderId.setTextColor(ContextCompat.getColor(activity, R.color.grey));
            holder.fragmentOrderAdapterBinding.receiptId.setTextColor(ContextCompat.getColor(activity, R.color.grey));
            holder.fragmentOrderAdapterBinding.orderDate.setTextColor(ContextCompat.getColor(activity, R.color.grey));
            holder.fragmentOrderAdapterBinding.custName.setTextColor(ContextCompat.getColor(activity, R.color.grey));
            holder.fragmentOrderAdapterBinding.amount.setTextColor(ContextCompat.getColor(activity, R.color.grey));
            holder.fragmentOrderAdapterBinding.custNumber.setTextColor(ContextCompat.getColor(activity, R.color.grey));
        }
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
