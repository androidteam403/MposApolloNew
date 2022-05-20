package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickupVerificationPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineInfoEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;

import java.util.ArrayList;
import java.util.List;

public class PickUpVerificationAdapter extends RecyclerView.Adapter<PickUpVerificationAdapter.ViewHolder> {
    private Activity activity;
    private List<RackAdapter.RackBoxModel.ProductData> productDataList;
    List<SalesLineEntity> salesLineEntityList=new ArrayList<>();
    private PickUpVerificationMvpView pickUpVerificationMvpView;



    public PickUpVerificationAdapter(Activity activity, List<SalesLineEntity> salesLineEntityList, PickUpVerificationMvpView pickUpVerificationMvpView) {
        this.activity = activity;
        this.salesLineEntityList = salesLineEntityList;
        this.pickUpVerificationMvpView = pickUpVerificationMvpView;
    }

//
//    public PickUpVerificationAdapter(Activity activity, List<RackAdapter.RackBoxModel.ProductData> productDataList, PickUpVerificationMvpView pickUpVerificationMvpView) {
//        this.activity = activity;
//        this.productDataList = productDataList;
//        this.pickUpVerificationMvpView = pickUpVerificationMvpView;
//    }

    @NonNull
    @Override
    public PickUpVerificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPickupVerificationPBinding adapterPickupVerificationBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_pickup_verification_p, parent, false);
        return new PickUpVerificationAdapter.ViewHolder(adapterPickupVerificationBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PickUpVerificationAdapter.ViewHolder holder, int position) {
        SalesLineEntity item = salesLineEntityList.get(position);
        holder.adapterPickupVerificationBinding.productName.setText(item.getItemName());
//        RackAdapter.RackBoxModel.ProductData productData = productDataList.get(position);
//        holder.adapterPickupVerificationBinding.productName.setText(productData.getProductName());
//        if (productData.getCapturedQuantity() != null && !productData.getCapturedQuantity().equalsIgnoreCase("")) {
//            holder.adapterPickupVerificationBinding.capturesQty.setText(productData.getCapturedQuantity().toString() + "/");
//        }
//
//        holder.adapterPickupVerificationBinding.availableQty.setText(productData.getAvailableQuantity());
//        if (productData.getStatus().equalsIgnoreCase("Full")) {
//            holder.adapterPickupVerificationBinding.fullStatusColor.setVisibility(View.VISIBLE);
//        } else if (productData.getStatus().equalsIgnoreCase("Partial")) {
//            holder.adapterPickupVerificationBinding.partialStatusColor.setVisibility(View.VISIBLE);
//        } else if (productData.getStatus().equalsIgnoreCase("NotAvailable")) {
//            holder.adapterPickupVerificationBinding.notAvailableStatusColor.setVisibility(View.VISIBLE);
//        }
//        if (productData.getPackerStatus() != null) {
//            if (productData.getPackerStatus().equalsIgnoreCase("Full")) {
//                holder.adapterPickupVerificationBinding.pacerFullStatusColor.setVisibility(View.VISIBLE);
//                holder.adapterPickupVerificationBinding.packerStatus.setVisibility(View.GONE);
//            } else if (productData.getPackerStatus().equalsIgnoreCase("Partial")) {
//                holder.adapterPickupVerificationBinding.pacerPartialStatusColor.setVisibility(View.VISIBLE);
//                holder.adapterPickupVerificationBinding.packerStatus.setVisibility(View.GONE);
//            } else if (productData.getPackerStatus().equalsIgnoreCase("Not Available")) {
//                holder.adapterPickupVerificationBinding.pacerNotAvailableStatusColor.setVisibility(View.VISIBLE);
//                holder.adapterPickupVerificationBinding.packerStatus.setVisibility(View.GONE);
//          }
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pickUpVerificationMvpView.recyclerItemClickableStatus()) {
//                    pickUpVerificationMvpView.onItemClick(position, productData);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productDataList.size();
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
