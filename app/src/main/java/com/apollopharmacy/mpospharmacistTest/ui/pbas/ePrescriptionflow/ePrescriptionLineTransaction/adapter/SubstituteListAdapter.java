package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterEprescriptionMedicinedetailsVtwoBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSubstititelistBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;

import java.util.ArrayList;
import java.util.List;

public class SubstituteListAdapter extends  RecyclerView.Adapter<SubstituteListAdapter.ViewHolder> {
    Context context;
    EPrescriptionMedicineDetailsMvpView mvpView;
//    private List<EPrescriptionMedicineResponse> filteredMedicineList;
    List<EPrescriptionSubstituteModelResponse.Substitute> substituteList;


    public SubstituteListAdapter(Context context, EPrescriptionMedicineDetailsMvpView mvpView, List<EPrescriptionMedicineResponse> filteredMedicineList, int position, List<EPrescriptionSubstituteModelResponse.Substitute> substituteList) {
        this.context=context;
        this.mvpView=mvpView;
        this.substituteList=substituteList;

    }

    public SubstituteListAdapter(EPrescriptionMedicineDetailsActivity ePrescriptionMedicineDetailsActivity, int simple_spinner_item, EPrescriptionSubstituteModelResponse substituteList) {
    }

    @NonNull
    @Override
    public SubstituteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSubstititelistBinding adapterSubstititelistBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_substititelist, parent, false);
        return new ViewHolder(adapterSubstititelistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SubstituteListAdapter.ViewHolder holder, int position) {
        EPrescriptionSubstituteModelResponse.Substitute substitute = substituteList.get(position);
        holder.adapterSubstititelistBinding.substituteItem.setText(substituteList.get(position).getSubstituteArtCode());
    }

    @Override
    public int getItemCount() {
        return substituteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterSubstititelistBinding adapterSubstititelistBinding;
        public ViewHolder(@NonNull AdapterSubstititelistBinding adapterSubstititelistBinding) {
            super(adapterSubstititelistBinding.getRoot());
            this.adapterSubstititelistBinding=adapterSubstititelistBinding;
        }
    }
}
