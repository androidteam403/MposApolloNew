package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterEprescriptionMedicinedetailsVtwoBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBatchAlertBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.customPackage.SubstituteDropDownAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;

import java.util.ArrayList;
import java.util.List;

public class EPrescriptionMedicineDetailsAdapter extends RecyclerView.Adapter<EPrescriptionMedicineDetailsAdapter.ViewHolder> {

    Context context;
    EPrescriptionMedicineDetailsMvpView mvpView;
    private List<EPrescriptionMedicineResponse> filteredMedicineList = new ArrayList<>();
    SubstituteListAdapter substituteListAdapter;
    EPrescriptionSubstituteModelResponse substituteList;

    public EPrescriptionMedicineDetailsAdapter(Context applicationContext, EPrescriptionMedicineDetailsMvpView mvpView, List<EPrescriptionMedicineResponse> filteredMedicineList, EPrescriptionSubstituteModelResponse substituteList) {
        this.filteredMedicineList = filteredMedicineList;
        this.context = applicationContext;
        this.mvpView = mvpView;
        this.substituteList = substituteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_eprescription_medicinedetails_vtwo, parent, false);
        return new ViewHolder(adapterEprescriptionMedicinedetailsVtwoBinding);


    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EPrescriptionMedicineResponse medicineResponse = filteredMedicineList.get(position);
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.prescriptionNo.setText(filteredMedicineList.get(position).getPrescriptionNo());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.artCode.setText(filteredMedicineList.get(position).getArtCode());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.artName.setText(filteredMedicineList.get(position).getArtName());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.qoh.setText(filteredMedicineList.get(position).getQty());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.mrp.setText("₹" + String.valueOf(filteredMedicineList.get(position).getPackMrp()));
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.reqQty.setText(filteredMedicineList.get(position).getQty());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.reqQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString() != null && !editable.toString().isEmpty()) {
                    if (mvpView != null){
                        medicineResponse.setReqQty(Integer.parseInt(editable.toString()));
                        mvpView.onReqQtyUpdate(medicineResponse);
                    }
                    if (Integer.parseInt(editable.toString()) > Integer.parseInt(filteredMedicineList.get(position).getQty())) {
                        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_DayNight_NoActionBar);
                        DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_batch_alert, null, false);
                        dialog.setContentView(dialogBatchAlertBinding.getRoot());
                        dialogBatchAlertBinding.dialogMessage.setText("You have entered more than required qty");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                        dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            if (mvpView != null){
                                medicineResponse.setReqQty(Integer.parseInt(editable.toString()));
                                mvpView.onReqQtyUpdate(medicineResponse);
                            }
                            holder.adapterEprescriptionMedicinedetailsVtwoBinding.reqQty.setText(filteredMedicineList.get(position).getQty());
                        });
                        dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
                    } else if (Integer.parseInt(editable.toString()) == 0) {
                        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_DayNight_NoActionBar);
                        DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_batch_alert, null, false);
                        dialog.setContentView(dialogBatchAlertBinding.getRoot());
                        dialogBatchAlertBinding.dialogMessage.setText("Please enter valid qty");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                        dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> {
                            dialog.dismiss();
                            if (mvpView != null){
                                medicineResponse.setReqQty(Integer.parseInt(editable.toString()));
                                mvpView.onReqQtyUpdate(medicineResponse);
                            }
                            holder.adapterEprescriptionMedicinedetailsVtwoBinding.reqQty.setText(filteredMedicineList.get(position).getQty());
                        });
                        dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
                    }
                }

            }
        });
        substituteSpinner(holder.adapterEprescriptionMedicinedetailsVtwoBinding, medicineResponse, position);
    }


    private void substituteSpinner(AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding, EPrescriptionMedicineResponse medicineResponse, int position) {
        List<EPrescriptionSubstituteModelResponse.Substitute> substituteLists = new ArrayList<>();
        EPrescriptionSubstituteModelResponse.Substitute substitutes = new EPrescriptionSubstituteModelResponse.Substitute();
        substitutes.setSubstituteArtCode("Select");
        substituteLists.add(substitutes);
        if (substituteList.getSubstituteList() != null && substituteList.getSubstituteList().size() > 0) {
            adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setVisibility(View.VISIBLE);
            adapterEprescriptionMedicinedetailsVtwoBinding.nosubstitutesfound.setVisibility(View.GONE);
            for (EPrescriptionSubstituteModelResponse.Substitute substitute : substituteList.getSubstituteList()) {
                if (substitute.getArtCode().equalsIgnoreCase(medicineResponse.getArtCode())) {
                    substituteLists.add(substitute);
                }
            }
            SubstituteDropDownAdapter substituteDropDownAdapter = new SubstituteDropDownAdapter(context, substituteLists);
            adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setAdapter(substituteDropDownAdapter);
            adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    if (mvpView != null) {
                        if (pos != 0)
                            mvpView.onSubstituteSelectedItem(substituteLists.get(pos), position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        } else {
            adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setVisibility(View.GONE);
            adapterEprescriptionMedicinedetailsVtwoBinding.nosubstitutesfound.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public int getItemCount() {
        return filteredMedicineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding;

        public ViewHolder(@NonNull AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding) {
            super(adapterEprescriptionMedicinedetailsVtwoBinding.getRoot());
            this.adapterEprescriptionMedicinedetailsVtwoBinding = adapterEprescriptionMedicinedetailsVtwoBinding;
        }
    }
}
