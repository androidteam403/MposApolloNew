package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFulfilmentDetailsV3Binding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.OpenOrdersV3MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public class FulfilmentDetailsAdapter extends RecyclerView.Adapter<FulfilmentDetailsAdapter.ViewHolder> {
    public List<RacksDataResponse.FullfillmentDetail.Product> products;
    public List<GetOMSTransactionResponse.SalesLine> salesLineList;
    public Context context;
    private OpenOrdersV3MvpView mvpView;
    public DialogUpdateStatusPBinding updateStatusBinding;
    private int fullFillmentPos;

    public FulfilmentDetailsAdapter(Context context, List<RacksDataResponse.FullfillmentDetail.Product> products, OpenOrdersV3MvpView mvpView, int fullFillmentPos, List<GetOMSTransactionResponse.SalesLine> salesLineList) {
        this.products = products;
        this.context = context;
        this.mvpView = mvpView;
        this.fullFillmentPos = fullFillmentPos;
        this.salesLineList = salesLineList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFulfilmentDetailsV3Binding fulfilmentDetailsV3Binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.adapter_fulfilment_details_v3,
                parent,
                false
        );
        return new ViewHolder(fulfilmentDetailsV3Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.fulfilmentDetailsV3Binding.productName.setText(salesLine.getItemName());
        holder.fulfilmentDetailsV3Binding.prefferedBatchNo.setText(salesLine.getPreferredBatch());
        holder.fulfilmentDetailsV3Binding.apolloMrp.setText(String.valueOf(salesLine.getPrice()));
        holder.fulfilmentDetailsV3Binding.rackId.setText(salesLine.getRackId());
        holder.fulfilmentDetailsV3Binding.quantity.setText(String.valueOf(salesLine.getQty()));
        holder.fulfilmentDetailsV3Binding.stripMrp.setText("-");
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFulfilmentDetailsV3Binding fulfilmentDetailsV3Binding;

        public ViewHolder(@NonNull AdapterFulfilmentDetailsV3Binding fulfilmentDetailsV3Binding) {
            super(fulfilmentDetailsV3Binding.getRoot());
            this.fulfilmentDetailsV3Binding = fulfilmentDetailsV3Binding;
        }
    }
}
