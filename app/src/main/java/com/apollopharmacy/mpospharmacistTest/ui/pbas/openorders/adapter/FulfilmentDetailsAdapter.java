package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFulfilmentDetailsBinding;
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
        this.salesLineList = salesLineList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFulfilmentDetailsBinding fulfilmentDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_fulfilment_details, parent, false);
        return new FulfilmentDetailsAdapter.ViewHolder(fulfilmentDetailsBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.fulfilmentDetailsBinding.productName.setText(salesLine.getItemName());
        holder.fulfilmentDetailsBinding.batchNo.setText("-");
        holder.fulfilmentDetailsBinding.stripMrp.setText(String.valueOf(salesLine.getPrice()));
        holder.fulfilmentDetailsBinding.rackId.setText(salesLine.getRackId());
        holder.fulfilmentDetailsBinding.quantity.setText(String.valueOf(salesLine.getQty()));
        holder.fulfilmentDetailsBinding.apolloMrp.setText("-");

    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFulfilmentDetailsBinding fulfilmentDetailsBinding;

        public ViewHolder(@NonNull AdapterFulfilmentDetailsBinding fulfilmentDetailsBinding) {
            super(fulfilmentDetailsBinding.getRoot());
            this.fulfilmentDetailsBinding = fulfilmentDetailsBinding;
        }
    }
}
