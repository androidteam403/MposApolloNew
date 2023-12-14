package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPrescriptionsV3Binding;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.OpenOrdersV3MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.bumptech.glide.Glide;

import java.util.List;

public class PrescriptionsAdapter extends RecyclerView.Adapter<PrescriptionsAdapter.ViewHolder> {
    private final Context mContext;
    private final OpenOrdersV3MvpView mvpView;
    private final List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList;

    public PrescriptionsAdapter(Context mContext, OpenOrdersV3MvpView mvpView, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList) {
        this.mContext = mContext;
        this.mvpView = mvpView;
        this.prescriptionsList = prescriptionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPrescriptionsV3Binding prescriptionsV3Binding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.adapter_prescriptions_v3,
                parent,
                false
        );
        return new ViewHolder(prescriptionsV3Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL = prescriptionsList.get(position);
        holder.prescriptionsV3Binding.setMvpView(mvpView);
        holder.prescriptionsV3Binding.setOrderPrescriptionURL(orderPrescriptionURL);
        holder.prescriptionsV3Binding.setPrescriptionsList(prescriptionsList);
        holder.prescriptionsV3Binding.setPosition(position);
        Glide.with(mContext).load(orderPrescriptionURL.getPerscriptionurl()).into(holder.prescriptionsV3Binding.prescriptionImage);
    }

    @Override
    public int getItemCount() {
        return prescriptionsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPrescriptionsV3Binding prescriptionsV3Binding;

        public ViewHolder(@NonNull AdapterPrescriptionsV3Binding prescriptionsV3Binding) {
            super(prescriptionsV3Binding.getRoot());
            this.prescriptionsV3Binding = prescriptionsV3Binding;
        }
    }
}
