package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickupSummaryDetailsProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.List;

public class PickUpSummaryDetailsProductsAdapter extends RecyclerView.Adapter<PickUpSummaryDetailsProductsAdapter.ViewHolder> {
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    public Context context;
    private String itemStatus;

    public PickUpSummaryDetailsProductsAdapter(Context context, List<GetOMSTransactionResponse.SalesLine> salesLineList) {
        this.context = context;
        this.salesLineList = salesLineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPickupSummaryDetailsProductsPBinding pickupSummaryDetailsProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_pickup_summary_details_products_p, parent, false);
        return new ViewHolder(pickupSummaryDetailsProductsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.pickupSummaryDetailsProductsBinding.productName.setText(salesLine.getItemName());
        holder.pickupSummaryDetailsProductsBinding.rackId.setText(salesLine.getRackId());
//        holder.pickupSummaryDetailsProductsBinding.batchNo.setText("-");
        holder.pickupSummaryDetailsProductsBinding.stripMrp.setText(String.valueOf(salesLine.getPrice()));
        holder.pickupSummaryDetailsProductsBinding.quantity.setText(String.valueOf(salesLine.getQty()));
        holder.pickupSummaryDetailsProductsBinding.apolloMrp.setText("-");
//


        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
        }
        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("FULL")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setRotation(0);
        }
        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("PARTIAL")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        }
        if (salesLine.getGetBatchInfoRes() != null) {
            holder.pickupSummaryDetailsProductsBinding.headings.setVisibility(View.VISIBLE);
            PickUpSummaryBatchesAdapter pickUpSummaryBatchesAdapter = new PickUpSummaryBatchesAdapter(context, salesLine.getGetBatchInfoRes().getBatchList(), salesLine);
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.pickupSummaryDetailsProductsBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.pickupSummaryDetailsProductsBinding.selectedbatchesRecycler.setAdapter(pickUpSummaryBatchesAdapter);
        }
    }


    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public AdapterPickupSummaryDetailsProductsPBinding pickupSummaryDetailsProductsBinding;

        public ViewHolder(@NonNull AdapterPickupSummaryDetailsProductsPBinding pickupSummaryDetailsProductsBinding) {
            super(pickupSummaryDetailsProductsBinding.getRoot());
            this.pickupSummaryDetailsProductsBinding = pickupSummaryDetailsProductsBinding;
        }
    }
}
