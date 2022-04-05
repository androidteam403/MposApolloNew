package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedPickupProcessProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public class FulfilmentDetailsAdapter extends RecyclerView.Adapter<FulfilmentDetailsAdapter.ViewHolder> {
    public List<RacksDataResponse.FullfillmentDetail.Product> products;
    public List<GetOMSTransactionResponse.SalesLine> salesLineList;
    public Context context;
    private OpenOrdersMvpView mvpView;
    public DialogUpdateStatusPBinding updateStatusBinding;

    private int fullFillmentPos;

    public FulfilmentDetailsAdapter(Context context, List<RacksDataResponse.FullfillmentDetail.Product> products, OpenOrdersMvpView mvpView, int fullFillmentPos, List<GetOMSTransactionResponse.SalesLine> salesLineList) {
        this.products = products;
        this.context = context;
        this.mvpView = mvpView;
        this.fullFillmentPos = fullFillmentPos;
        this.salesLineList=salesLineList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_pickup_process_products_p, parent, false);
        return new FulfilmentDetailsAdapter.ViewHolder(pickupSummaryDetailsProductsBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        RacksDataResponse.FullfillmentDetail.Product dataResponse = products.get(position);
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);


        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setOnClickListener(view -> {
            mvpView.onClickStausIcon(fullFillmentPos, position);
        });

//
//        if (dataResponse.getItemStatus().equals("FULL VERIFIED")) {
//            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.GONE);
////                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.));
//        } else if (dataResponse.getItemStatus().equals("PARTIAL VERIFIED")) {
//            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.GONE);
//        } else if (dataResponse.getItemStatus().equals("NOT AVAILABLE")) {
//            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
//

//        }


        holder.pickupSummaryDetailsProductsBinding.productName.setText(salesLineList.get(position).getItemName());
        holder.pickupSummaryDetailsProductsBinding.batchNo.setText("-");
        holder.pickupSummaryDetailsProductsBinding.stripMrp.setText(String.valueOf(salesLineList.get(position).getPrice()));
        holder.pickupSummaryDetailsProductsBinding.rackId.setText(salesLineList.get(position).getRackId());
        holder.pickupSummaryDetailsProductsBinding.quantity.setText(String.valueOf(salesLineList.get(position).getQty()));
        holder.pickupSummaryDetailsProductsBinding.apolloMrp.setText("-");

    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding;
        public DialogUpdateStatusPBinding updateStatusBinding;

        public ViewHolder(@NonNull AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding) {
            super(pickupSummaryDetailsProductsBinding.getRoot());
            this.pickupSummaryDetailsProductsBinding = pickupSummaryDetailsProductsBinding;
        }
    }
}
