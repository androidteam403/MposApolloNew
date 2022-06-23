package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickupVerificationPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PickUpVerificationAdapter extends RecyclerView.Adapter<PickUpVerificationAdapter.ViewHolder> {
    private Context mContext;
    private List<GetOMSTransactionResponse> transactionList = new ArrayList<>();
    private PickUpVerificationMvpView pickUpVerificationMvpView;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private GetOMSTransactionResponse getOMSTransactionResponse;
    private List<GetBatchInfoRes.BatchListObj> selectedBatchList;

    public PickUpVerificationAdapter(Context mContext, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickUpVerificationMvpView pickUpVerificationMvpView, List<GetBatchInfoRes.BatchListObj> selectedBatchList, GetOMSTransactionResponse getOMSTransactionResponse) {
        this.mContext = mContext;
        this.salesLineList = salesLineList;
        this.pickUpVerificationMvpView = pickUpVerificationMvpView;
        this.getOMSTransactionResponse = getOMSTransactionResponse;
        this.selectedBatchList = selectedBatchList;
    }

    @NonNull
    @Override
    public PickUpVerificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPickupVerificationPBinding adapterPickupVerificationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_pickup_verification_p, parent, false);
        return new PickUpVerificationAdapter.ViewHolder(adapterPickupVerificationBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PickUpVerificationAdapter.ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.adapterPickupVerificationBinding.productName.setText(salesLine.getItemName() != null && !salesLine.getItemName().isEmpty() ? salesLine.getItemName() : "-");
        holder.adapterPickupVerificationBinding.capturesQty.setText(salesLine.getPickedQty() != null && !salesLine.getPickedQty().isEmpty() ? salesLine.getPickedQty() : "0");
        holder.adapterPickupVerificationBinding.availableQty.setText("/" + String.valueOf(salesLine.getQty()));

        if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("FULL")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("PARTIAL")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("NOT AVAILABLE")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
        } else {

        }

        if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("FULL")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);
        } else if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("PARTIAL")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);
        } else if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("NOT AVAILABLE")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);
        } else {

        }

        holder.adapterPickupVerificationBinding.packerStatusIcon.setOnClickListener(view -> {
            if (pickUpVerificationMvpView != null) {
                pickUpVerificationMvpView.onClickItemUpdate(salesLine, position);
            }
        });
        holder.adapterPickupVerificationBinding.update.setOnClickListener(view -> {
            if (pickUpVerificationMvpView != null) {
                pickUpVerificationMvpView.onClickItemUpdate(salesLine, position);
            }
        });

        if (selectedBatchList != null && selectedBatchList.size() > 0) {
            List<GetBatchInfoRes.BatchListObj> selectedBatchListTemp = new ArrayList<>();
            for (GetBatchInfoRes.BatchListObj batchListObj : selectedBatchList) {
                if (batchListObj.getItemID().equals(salesLine.getItemId())) {
                    for (GetOMSTransactionResponse.PickPackReservation pickPackReservation : getOMSTransactionResponse.getPickPackReservation()) {
                        if (pickPackReservation.getPickupItemId().equals(batchListObj.getItemID())
                                && pickPackReservation.getPickupInventBatchId().equals(batchListObj.getBatchNo())) {
                            batchListObj.setREQQTY(pickPackReservation.getPickupQty());
                            selectedBatchListTemp.add(batchListObj);
                        }
                    }
                }
            }
            List<GetBatchInfoRes.BatchListObj> newList = selectedBatchListTemp.stream()
                    .distinct()
                    .collect(Collectors.toList());
            if (newList != null && newList.size() > 0)
                holder.adapterPickupVerificationBinding.headings.setVisibility(View.VISIBLE);
            else
                holder.adapterPickupVerificationBinding.headings.setVisibility(View.GONE);
            PackerBatchDetailsAdapter packerBatchDetailsAdapter = new PackerBatchDetailsAdapter(mContext, newList);
            new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
            holder.adapterPickupVerificationBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(mContext));
            holder.adapterPickupVerificationBinding.selectedbatchesRecycler.setAdapter(packerBatchDetailsAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPickupVerificationPBinding adapterPickupVerificationBinding;

        public ViewHolder(@NonNull AdapterPickupVerificationPBinding adapterPickupVerificationBinding) {
            super(adapterPickupVerificationBinding.getRoot());
            this.adapterPickupVerificationBinding = adapterPickupVerificationBinding;
        }
    }
}
