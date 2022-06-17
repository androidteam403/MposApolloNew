package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterEprescriptionMedicinedetailsVtwoBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterEprescriptionVtwoBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.adapter.EPrescriptionListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.customPackage.SubstituteDropDownAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionMedicineResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.model.EPrescriptionSubstituteModelResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class EPrescriptionMedicineDetailsAdapter extends RecyclerView.Adapter<EPrescriptionMedicineDetailsAdapter.ViewHolder> {

    Context context;
    EPrescriptionMedicineDetailsMvpView mvpView;
    private List<EPrescriptionMedicineResponse> filteredMedicineList = new ArrayList<>();
    SubstituteListAdapter substituteListAdapter;
    EPrescriptionSubstituteModelResponse substituteList;

    public EPrescriptionMedicineDetailsAdapter(Context applicationContext, EPrescriptionMedicineDetailsMvpView mvpView, List<EPrescriptionMedicineResponse> filteredMedicineList, EPrescriptionSubstituteModelResponse substituteList) {
        this.filteredMedicineList=filteredMedicineList;
        this.context=applicationContext;
        this.mvpView=mvpView;
        this.substituteList= substituteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_eprescription_medicinedetails_vtwo, parent, false);
        return new ViewHolder(adapterEprescriptionMedicinedetailsVtwoBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EPrescriptionMedicineResponse medicineResponse = filteredMedicineList.get(position);
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.prescriptionNo.setText(filteredMedicineList.get(position).getPrescriptionNo());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.artCode.setText(filteredMedicineList.get(position).getArtCode());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.artName.setText(filteredMedicineList.get(position).getArtName());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.qoh.setText(filteredMedicineList.get(position).getQty());
        holder.adapterEprescriptionMedicinedetailsVtwoBinding.mrp.setText(String.valueOf(filteredMedicineList.get(position).getPackMrp()));

        substituteSpinner(holder.adapterEprescriptionMedicinedetailsVtwoBinding, medicineResponse, position);
//        holder.adapterEprescriptionMedicinedetailsVtwoBinding.comment.setText("---");
//        mvpView.onClickDropDown();
//
//
//        holder.adapterEprescriptionMedicinedetailsVtwoBinding.substituteItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////               holder.adapterEprescriptionMedicinedetailsVtwoBinding.substituteRecycler.setVisibility(View.VISIBLE);
//                mvpView.onClickDropDown();
//
////                substituteListAdapter = new SubstituteListAdapter(context, mvpView, filteredMedicineList, position, substituteList.getSubstituteList());
////                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
////                holder.adapterEprescriptionMedicinedetailsVtwoBinding.substituteRecycler.setLayoutManager(mLayoutManager);
////                holder.adapterEprescriptionMedicinedetailsVtwoBinding.substituteRecycler.setAdapter(substituteListAdapter);
//
//            }
//        });

//            holder.adapterEprescriptionMedicinedetailsVtwoBinding.arrowSub.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(substituteList!=null && substituteList.getSubstituteList()!=null && substituteList.getSubstituteList().size()>0){
//                        ArrayList<String> items = new  ArrayList<String>();
//                        for(int i =0; i<substituteList.getSubstituteList().size();i++){
//                            if(filteredMedicineList.get(position).getArtCode().equals(substituteList.getSubstituteList().get(i).getArtCode())){
//                                items.add(substituteList.getSubstituteList().get(i).getSubstituteArtCode());
//                            }
//                        }
//                        mvpView.onClickDropDown(items);
//                    }else{
//                        Toast.makeText(context, "No Substitudes available!!" ,Toast.LENGTH_LONG).show();
//                    }
//
//                }
//            });
        }



    private void substituteSpinner(AdapterEprescriptionMedicinedetailsVtwoBinding adapterEprescriptionMedicinedetailsVtwoBinding, EPrescriptionMedicineResponse medicineResponse, int position) {
     List<EPrescriptionSubstituteModelResponse.Substitute> substituteLists = new ArrayList<>();
     if (substituteList.getSubstituteList()!= null && substituteList.getSubstituteList().size()>0) {
         adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setVisibility(View.VISIBLE);
         adapterEprescriptionMedicinedetailsVtwoBinding.nosubstitutesfound.setVisibility(View.GONE);
         for (EPrescriptionSubstituteModelResponse.Substitute substitute : substituteList.getSubstituteList()){
             if (substitute.getArtCode().equalsIgnoreCase(medicineResponse.getArtCode())){
                 substituteLists.add(substitute);
             }
         }
         SubstituteDropDownAdapter substituteDropDownAdapter = new SubstituteDropDownAdapter(context, substituteLists);
         adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setAdapter(substituteDropDownAdapter);
         adapterEprescriptionMedicinedetailsVtwoBinding.substitueId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                 if(mvpView!=null){
                   mvpView.onSubstituteSelectedItem(substituteLists.get(pos), position);
                 }
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {

             }
         });

     }else{
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
            this.adapterEprescriptionMedicinedetailsVtwoBinding=adapterEprescriptionMedicinedetailsVtwoBinding;
        }
    }
}
