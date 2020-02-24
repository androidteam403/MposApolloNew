package com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.MedicineDetailsAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.medicinedetailsactivity.model.MedicineDetailsModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;

public class MedicinesDetailAdapter extends RecyclerView.Adapter<MedicinesDetailAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<GetItemDetailsRes.Items> medicineDetailsModelArrayList;

    public MedicinesDetailAdapter(Activity activity, ArrayList<GetItemDetailsRes.Items> medicineDetailsModelArrayList) {
        this.activity = activity;
        this.medicineDetailsModelArrayList = medicineDetailsModelArrayList;
    }

    @NonNull
    @Override
    public MedicinesDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicineDetailsAdapterBinding medicineDetailsAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.medicine_details_adapter, parent, false);
        return new MedicinesDetailAdapter.ViewHolder(medicineDetailsAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesDetailAdapter.ViewHolder holder, int position) {
        GetItemDetailsRes.Items item = medicineDetailsModelArrayList.get(position);
        holder.medicineDetailsAdapterBinding.setProduct(item);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public MedicineDetailsAdapterBinding medicineDetailsAdapterBinding;

        public ViewHolder(@NonNull MedicineDetailsAdapterBinding medicineDetailsAdapterBinding) {
            super(medicineDetailsAdapterBinding.getRoot());
            this.medicineDetailsAdapterBinding = medicineDetailsAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return medicineDetailsModelArrayList.size();
    }
}
