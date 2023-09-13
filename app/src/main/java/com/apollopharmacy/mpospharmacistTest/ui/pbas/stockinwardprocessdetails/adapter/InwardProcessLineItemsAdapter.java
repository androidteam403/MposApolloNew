package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterInwardProcessLineItemsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.StockInwardProcessDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;

import java.util.List;

public class InwardProcessLineItemsAdapter extends RecyclerView.Adapter<InwardProcessLineItemsAdapter.ViewHolder> {
    List<GetInventoryTransactionDetailsResponse.InventoryDatum.InventoryDetail> inventoryDetails;
    StockInwardProcessDetailsMvpView stockInwardProcessDetailsMvpView;
    String dropDownItem;

    boolean dropDownClicked;
    public InwardProcessLineItemsAdapter(List<GetInventoryTransactionDetailsResponse.InventoryDatum.InventoryDetail> inventoryDetails, StockInwardProcessDetailsMvpView stockInwardProcessDetailsMvpView, String dropDownItem, boolean dropDownClicked) {
        this.inventoryDetails=inventoryDetails;
        this.stockInwardProcessDetailsMvpView=stockInwardProcessDetailsMvpView;
        this.dropDownItem=dropDownItem;
        this.dropDownClicked=dropDownClicked;
    }

    @NonNull
    @Override
    public InwardProcessLineItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_inward_process_line_items, parent, false);
        return new InwardProcessLineItemsAdapter.ViewHolder(inwardProcessLineItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull InwardProcessLineItemsAdapter.ViewHolder holder, int position) {
        GetInventoryTransactionDetailsResponse.InventoryDatum.InventoryDetail inventoryDetail= inventoryDetails.get(position);
        holder.inwardProcessLineItemsBinding.itemId.setText(inventoryDetail.getItemId());
        holder.inwardProcessLineItemsBinding.itemName.setText(inventoryDetail.getItemName());
        holder.inwardProcessLineItemsBinding.batchId.setText(inventoryDetail.getInventbatchId());
        holder.inwardProcessLineItemsBinding.qty.setText(inventoryDetail.getQty().toString());
        int mrp = (int) inventoryDetail.getMrp();
        holder.inwardProcessLineItemsBinding.mrp.setText(String.valueOf(mrp));
        if(inventoryDetails.get(position).dropDownLineList!=null && inventoryDetails.get(position).dropDownLineList.getText()!=null && !inventoryDetails.get(position).dropDownLineList.getText().isEmpty()){
            holder.inwardProcessLineItemsBinding.remarks.setText(inventoryDetail.dropDownLineList.getText());
        }else{
            holder.inwardProcessLineItemsBinding.remarks.setText("");
        }
        holder.inwardProcessLineItemsBinding.physicalExp.setOnClickListener(v -> {

        });
        holder.inwardProcessLineItemsBinding.topLayout.setOnClickListener(v -> {

                if (holder.inwardProcessLineItemsBinding.extraLayout.getVisibility() == View.VISIBLE) {
                    holder.inwardProcessLineItemsBinding.extraLayout.setVisibility(View.GONE);
                    holder.inwardProcessLineItemsBinding.dropDownIcon.animate().rotation(90).setDuration(150).start();

                } else {

                    holder.inwardProcessLineItemsBinding.extraLayout.setVisibility(View.VISIBLE);
                    holder.inwardProcessLineItemsBinding.dropDownIcon.animate().rotation(270).setDuration(150).start();
                }


        });

        holder.inwardProcessLineItemsBinding.dropDownIconRemarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockInwardProcessDetailsMvpView.onClickDropDown(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return inventoryDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding;

        public ViewHolder(@NonNull AdapterInwardProcessLineItemsBinding inwardProcessLineItemsBinding) {
            super(inwardProcessLineItemsBinding.getRoot());
            this.inwardProcessLineItemsBinding = inwardProcessLineItemsBinding;
        }
    }
}
