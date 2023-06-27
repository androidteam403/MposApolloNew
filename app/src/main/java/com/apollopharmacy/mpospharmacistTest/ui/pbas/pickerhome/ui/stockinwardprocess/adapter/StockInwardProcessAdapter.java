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
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;

import java.util.List;

public class StockInwardProcessAdapter extends RecyclerView.Adapter<StockInwardProcessAdapter.ViewHolder> {
    private Context context;
    private StockInwardProcessMvpView mvpView;
    private List<GetInventoryTransactionDetailsResponse.InventoryDatum> inventoryData;

    public StockInwardProcessAdapter(Context context, StockInwardProcessMvpView mvpView, List<GetInventoryTransactionDetailsResponse.InventoryDatum> inventoryData) {
        this.context = context;
        this.mvpView = mvpView;
        this.inventoryData = inventoryData;
    }

    @NonNull
    @Override
    public StockInwardProcessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterStockInwardProcessBinding adapterStockInwardProcessBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_stock_inward_process, parent, false);
        return new StockInwardProcessAdapter.ViewHolder(adapterStockInwardProcessBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StockInwardProcessAdapter.ViewHolder holder, int position) {
        GetInventoryTransactionDetailsResponse.InventoryDatum getInventory = inventoryData.get(position);
        holder.adapterStockInwardProcessBinding.referenceId.setText(getInventory.getReferenceId());
        if (!getInventory.getFromInventSiteId().isEmpty()) {
            holder.adapterStockInwardProcessBinding.fromSiteId.setText(getInventory.getFromInventSiteId());
        } else {
            holder.adapterStockInwardProcessBinding.fromSiteId.setText("-");
        }

        if (!getInventory.getVendGroup().isEmpty()) {
            holder.adapterStockInwardProcessBinding.vendGroup.setText(getInventory.getVendGroup());
        } else {
            holder.adapterStockInwardProcessBinding.vendGroup.setText("-");
            ;
        }
        if (!getInventory.getInventSiteId().isEmpty()) {
            holder.adapterStockInwardProcessBinding.siteId.setText(getInventory.getInventSiteId());
        } else {
            holder.adapterStockInwardProcessBinding.siteId.setText("-");
        }

        if (!getInventory.getTicketId().isEmpty()) {
            holder.adapterStockInwardProcessBinding.ticketId.setText(getInventory.getTicketId());
        } else {
            holder.adapterStockInwardProcessBinding.ticketId.setText("-");
        }

        if (!getInventory.getDatePhysical().isEmpty()) {
            holder.adapterStockInwardProcessBinding.date.setText(getInventory.getDatePhysical());
        } else {
            holder.adapterStockInwardProcessBinding.date.setText("-");
        }

        holder.adapterStockInwardProcessBinding.setCallback(mvpView);

    }

    @Override
    public int getItemCount() {
        return inventoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterStockInwardProcessBinding adapterStockInwardProcessBinding;

        public ViewHolder(@NonNull AdapterStockInwardProcessBinding adapterStockInwardProcessBinding) {
            super(adapterStockInwardProcessBinding.getRoot());
            this.adapterStockInwardProcessBinding = adapterStockInwardProcessBinding;
        }
    }
}
