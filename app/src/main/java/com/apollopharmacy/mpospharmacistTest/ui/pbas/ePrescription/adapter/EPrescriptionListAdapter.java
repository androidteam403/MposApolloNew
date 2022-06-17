package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterEprescriptionVtwoBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFullfilmentPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.EPrescriptionActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.EPrescriptionMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter.FullfilmentAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EPrescriptionListAdapter  extends RecyclerView.Adapter<EPrescriptionListAdapter.ViewHolder> {

    Context context;
    List<EPrescriptionModelClassResponse> prescriptionLine;
    private List<EPrescriptionModelClassResponse> prescriptionList = new ArrayList<>();
    private List<EPrescriptionModelClassResponse> filteredPrescriptionList = new ArrayList<>();
    private List<EPrescriptionModelClassResponse> filteredList = new ArrayList<>();
    private  EPrescriptionMvpView mvpView;

    public EPrescriptionListAdapter(Context context, List<EPrescriptionModelClassResponse> prescriptionLine, EPrescriptionMvpView mvpView) {
        this.context=context;
        this.prescriptionList=prescriptionLine;
        this.prescriptionLine = prescriptionLine;
        this.filteredPrescriptionList = prescriptionLine;
        this.mvpView=mvpView;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterEprescriptionVtwoBinding adapterEprescriptionVtwoBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_eprescription_vtwo, parent, false);
        return new ViewHolder(adapterEprescriptionVtwoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EPrescriptionModelClassResponse ePrescription = filteredPrescriptionList.get(position);
        holder.adapterEprescriptionVtwoBinding.prescriptionNo.setText(filteredPrescriptionList.get(position).getPrescriptionNo());
        holder.adapterEprescriptionVtwoBinding.orderDate.setText(filteredPrescriptionList.get(position).getPresDate());
        holder.adapterEprescriptionVtwoBinding.paymentmode.setText(String.valueOf(filteredPrescriptionList.get(position).getShippingmethod()));
//        holder.adapterEprescriptionVtwoBinding.receiptId.setText(filteredPrescriptionList.get(position).getPrescriptionNo());
        holder.adapterEprescriptionVtwoBinding.patientName.setText(filteredPrescriptionList.get(position).getPatientName());
        holder.adapterEprescriptionVtwoBinding.doctorName.setText(filteredPrescriptionList.get(position).getDoctorName());
        holder.adapterEprescriptionVtwoBinding.customerno.setText(filteredPrescriptionList.get(position).getPhoneNo());
        holder.adapterEprescriptionVtwoBinding.amount.setText(String.valueOf(filteredPrescriptionList.get(position).getOrderbillvalue()));
//            receiptid, orderdate, patientname, doctorname, paymentmode, fulfilmentid, cxno.


        holder.adapterEprescriptionVtwoBinding.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mvpView.onClickRightArrow(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (filteredPrescriptionList != null && filteredPrescriptionList.size() > 0) {
            count = filteredPrescriptionList.size();
        }
        return count;
    }



    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toUpperCase();
                if (charString.isEmpty()) {
                    filteredPrescriptionList = prescriptionList;
                } else {
                    filteredList.clear();
                    for (EPrescriptionModelClassResponse row : prescriptionList) {
                        if (!filteredList.contains(row) && (row.getPrescriptionNo().contains(charString))) {
                            filteredList.add(row);
                        } else if(!filteredList.contains(row) && (row.getPatientName().contains(charString))){
                            filteredList.add(row);
                        }else if(!filteredList.contains(row) && (row.getDoctorName().contains(charString))){
                            filteredList.add(row);
                        }else if(!filteredList.contains(row) && (row.getPhoneNo().contains(charString))){
                            filteredList.add(row);
                        }

//                       orderno, docname, patiname, phoneno.

                    }
                    filteredPrescriptionList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredPrescriptionList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filteredPrescriptionList != null && !filteredPrescriptionList.isEmpty()) {
                    filteredPrescriptionList = (ArrayList<EPrescriptionModelClassResponse>) filterResults.values;
                    try {
                        mvpView.noOrderFound(filteredPrescriptionList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    mvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterEprescriptionVtwoBinding adapterEprescriptionVtwoBinding;
        public ViewHolder(@NonNull AdapterEprescriptionVtwoBinding adapterEprescriptionVtwoBinding) {
            super(adapterEprescriptionVtwoBinding.getRoot());
            this.adapterEprescriptionVtwoBinding=adapterEprescriptionVtwoBinding;
        }
    }
}
