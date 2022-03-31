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
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.ReadyForPickUpMvpView;

import java.util.List;

public class ReadyForPickUpAdapter extends RecyclerView.Adapter<ReadyForPickUpAdapter.ViewHolder> {

    private Activity activity;
    private List<ReadyForPickUpActivity.FullfillmentData> fullfillmentDataList;
    private ReadyForPickUpMvpView readyForPickUpMvpView;


    public ReadyForPickUpAdapter(Activity activity, List<ReadyForPickUpActivity.FullfillmentData> fullfillmentDataList, ReadyForPickUpMvpView readyForPickUpMvpView) {
        this.activity = activity;
        this.fullfillmentDataList = fullfillmentDataList;
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
        ReadyForPickUpActivity.FullfillmentData fullfillmentData = fullfillmentDataList.get(position);
        holder.adapterReadyForPickupBinding.filmentId.setText(fullfillmentData.getFullfillmentId());
        holder.adapterReadyForPickupBinding.totalItems.setText(fullfillmentData.getTotalItems());

        if (fullfillmentData.isTagBox()) {
            holder.adapterReadyForPickupBinding.tickMark.setVisibility(View.VISIBLE);
            holder.adapterReadyForPickupBinding.scanDelete.setVisibility(View.VISIBLE);
            holder.adapterReadyForPickupBinding.takePrint1.setVisibility(View.VISIBLE);

        } else {
            holder.adapterReadyForPickupBinding.tickMark.setVisibility(View.GONE);
            holder.adapterReadyForPickupBinding.scanDelete.setVisibility(View.GONE);
            holder.adapterReadyForPickupBinding.takePrint1.setVisibility(View.GONE);

        }
        holder.adapterReadyForPickupBinding.scanDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyForPickUpMvpView.onDeleteClick(position, fullfillmentData.getFullfillmentId());
            }
        });

        holder.adapterReadyForPickupBinding.tagBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fullfillmentData.isScanView()) {
                    readyForPickUpMvpView.onTagBoxClick(fullfillmentData.getFullfillmentId(), position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return fullfillmentDataList.size();
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
}
