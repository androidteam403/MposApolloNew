package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterStockInwardProcessBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess.StockInwardProcessMvpView;

public class StockInwardProcessAdapter extends RecyclerView.Adapter<StockInwardProcessAdapter.ViewHolder> {
    private Context context;
    private StockInwardProcessMvpView mvpView;

    public StockInwardProcessAdapter(Context context, StockInwardProcessMvpView mvpView) {
        this.context = context;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public StockInwardProcessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterStockInwardProcessBinding adapterStockInwardProcessBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_stock_inward_process, parent, false);
        return new StockInwardProcessAdapter.ViewHolder(adapterStockInwardProcessBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockInwardProcessAdapter.ViewHolder holder, int position) {
        holder.adapterStockInwardProcessBinding.setCallback(mvpView);
    }

    @Override
    public int getItemCount() {
        return 50;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterStockInwardProcessBinding adapterStockInwardProcessBinding;

        public ViewHolder(@NonNull AdapterStockInwardProcessBinding adapterStockInwardProcessBinding) {
            super(adapterStockInwardProcessBinding.getRoot());
            this.adapterStockInwardProcessBinding = adapterStockInwardProcessBinding;
        }
    }
}
