package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterReadyForPickupPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpMvpView;

import java.util.List;

public class ReadyForPickUpAdapter extends RecyclerView.Adapter<ReadyForPickUpAdapter.ViewHolder> {

    private Activity activity;

    private List<ReadyForPickUpActivity.FullfillmentData> fullfillmentDataList;
    private ReadyForPickUpMvpView readyForPickUpMvpView;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;

    public ReadyForPickUpAdapter(Activity activity, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, ReadyForPickUpMvpView readyForPickUpMvpView) {
        this.activity = activity;
        this.selectedOmsHeaderList = selectedOmsHeaderList;
        this.readyForPickUpMvpView = readyForPickUpMvpView;


    }

    @NonNull
    @Override
    public ReadyForPickUpAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterReadyForPickupPBinding adapterReadyForPickupBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_ready_for_pickup_p, parent, false);
        return new ReadyForPickUpAdapter.ViewHolder(adapterReadyForPickupBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ReadyForPickUpAdapter.ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = selectedOmsHeaderList.get(position);
        holder.adapterReadyForPickupBinding.filmentId.setText(omsHeader.getRefno());
        if (omsHeader.getScannedBarcode() != null && !omsHeader.getScannedBarcode().isEmpty()) {
            holder.adapterReadyForPickupBinding.scannedCode.setText(omsHeader.getScannedBarcode());
//            holder.adapterReadyForPickupBinding.scannedCode.setText(lastFiveDigits(String.valueOf(omsHeader.getScannedBarcode())));
        } else {
            holder.adapterReadyForPickupBinding.scannedCode.setText("");
        }

//  holder.adapterReadyForPickupBinding.filmentIdNum.setText(lastFourDigits(String.valueOf(omsHeader.getFulfilId())));


        holder.adapterReadyForPickupBinding.totalItems.setText(String.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().size()));


        if (omsHeader.isTagBox()) {
            holder.adapterReadyForPickupBinding.tickMark.setVisibility(View.VISIBLE);
            holder.adapterReadyForPickupBinding.scanDelete.setVisibility(View.VISIBLE);
//            holder.adapterReadyForPickupBinding.takePrint1.setVisibility(View.VISIBLE);
        } else {
            holder.adapterReadyForPickupBinding.tickMark.setVisibility(View.GONE);
            holder.adapterReadyForPickupBinding.scanDelete.setVisibility(View.GONE);
//            holder.adapterReadyForPickupBinding.takePrint1.setVisibility(View.GONE);
        }

//        if (omsHeader.isOverAllStatusfromList()) {
//            holder.adapterReadyForPickupBinding.tickMark.setVisibility(View.VISIBLE);
//        }
        holder.adapterReadyForPickupBinding.scanDelete.setOnClickListener(v -> {
            readyForPickUpMvpView.onDeleteClick(position, omsHeader.getRefno(), lastFiveDigits(String.valueOf(omsHeader.getScannedBarcode())));
        });

        holder.adapterReadyForPickupBinding.tagBox.setOnClickListener(v -> {
            if (!omsHeader.isScanView()) {
                readyForPickUpMvpView.onTagBoxClick(omsHeader.getRefno(), position);
            }
        });
//        holder.adapterReadyForPickupBinding.takePrint1.setOnClickListener(view -> {
//            if (readyForPickUpMvpView != null) {
//                readyForPickUpMvpView.onClickTakePrint(omsHeader);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return selectedOmsHeaderList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterReadyForPickupPBinding adapterReadyForPickupBinding;

        public ViewHolder(@NonNull AdapterReadyForPickupPBinding adapterReadyForPickupBinding) {
            super(adapterReadyForPickupBinding.getRoot());
            this.adapterReadyForPickupBinding = adapterReadyForPickupBinding;
        }
    }

    public static String lastFiveDigits(String data) {
        String lastFourDigits = "";   //substring containing last 4 characters

        if (data.length() > 5) {
            lastFourDigits = data.substring(data.length() - 5);
        } else {
            lastFourDigits = data;
        }

        return lastFourDigits;
    }
}
