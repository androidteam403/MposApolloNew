package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPrescriptionsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.bumptech.glide.Glide;

import java.util.List;

public class PrescriptionsAdapter extends RecyclerView.Adapter<PrescriptionsAdapter.ViewHolder> {
    private final Context mContext;
    private final OpenOrdersMvpView mvpView;
    private final List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList;

    public PrescriptionsAdapter(Context mContext, OpenOrdersMvpView mvpView, List<GetOMSTransactionResponse.OrderPrescriptionURL> prescriptionsList) {
        this.mContext = mContext;
        this.mvpView = mvpView;
        this.prescriptionsList = prescriptionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPrescriptionsBinding adapterPrescriptionsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_prescriptions, parent, false);
        return new ViewHolder(adapterPrescriptionsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionResponse.OrderPrescriptionURL orderPrescriptionURL = prescriptionsList.get(position);
        holder.adapterPrescriptionsBinding.setMvpView(mvpView);
        holder.adapterPrescriptionsBinding.setOrderPrescriptionURL(orderPrescriptionURL);
        holder.adapterPrescriptionsBinding.setPrescriptionsList(prescriptionsList);
        holder.adapterPrescriptionsBinding.setPosition(position);
        Glide.with(mContext).load(orderPrescriptionURL.getPerscriptionurl()).into(holder.adapterPrescriptionsBinding.prescriptionImage);
    }

    @Override
    public int getItemCount() {
        return prescriptionsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPrescriptionsBinding adapterPrescriptionsBinding;

        public ViewHolder(@NonNull AdapterPrescriptionsBinding adapterPrescriptionsBinding) {
            super(adapterPrescriptionsBinding.getRoot());
            this.adapterPrescriptionsBinding = adapterPrescriptionsBinding;
        }
    }
}
