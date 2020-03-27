package com.apollopharmacy.mpospharmacist.ui.home.ui.orders.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentOrderAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersMvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.OrdersMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

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
