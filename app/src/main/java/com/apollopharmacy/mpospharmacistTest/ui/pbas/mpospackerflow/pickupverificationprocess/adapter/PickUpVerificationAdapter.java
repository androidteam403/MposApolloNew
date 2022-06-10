package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickupVerificationPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class PickUpVerificationAdapter extends RecyclerView.Adapter<PickUpVerificationAdapter.ViewHolder> {
    private Context mContext;
    private List<GetOMSTransactionResponse> transactionList = new ArrayList<>();
    private PickUpVerificationMvpView pickUpVerificationMvpView;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    List<GetOMSTransactionResponse.SalesLine> salesLineList;

    public PickUpVerificationAdapter(Context mContext, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickUpVerificationMvpView pickUpVerificationMvpView) {
        this.mContext = mContext;
        this.salesLineList = salesLineList;
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
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.adapterPickupVerificationBinding.productName.setText(salesLine.getItemName());
        holder.adapterPickupVerificationBinding.capturesQty.setText(salesLine.getPickedQty());
        holder.adapterPickupVerificationBinding.availableQty.setText("/" + String.valueOf(salesLine.getQty()));

        if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("FULL")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("PARTIAL")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        } else if (salesLine.getPickerStatus() != null && salesLine.getPickerStatus().equals("NOT AVAILABLE")) {
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.pickerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
        } else {

        }

        if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("FULL")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(0);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);

        } else if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("PARTIAL")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);
        } else if (salesLine.getPackerStatus() != null && salesLine.getPackerStatus().equals("NOT AVAILABLE")) {
            holder.adapterPickupVerificationBinding.packerStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
            holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setRotation(90);
            holder.adapterPickupVerificationBinding.packerStatusIcon.setVisibility(View.VISIBLE);
        } else {

        }

        holder.adapterPickupVerificationBinding.packerStatusIcon.setOnClickListener(view -> {
            if (pickUpVerificationMvpView != null) {
                pickUpVerificationMvpView.onClickItemUpdate(salesLine, position);
            }
        });
        holder.adapterPickupVerificationBinding.update.setOnClickListener(view -> {
            if (pickUpVerificationMvpView != null) {
                pickUpVerificationMvpView.onClickItemUpdate(salesLine, position);
            }
        });

//        if (customerData.getStockStatus() != null && customerData.getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE")) {
//
//            holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
//
//            holder.adapterPickupVerificationBinding.statusIcon.setVisibility(View.VISIBLE);
//
//        } else if (customerData.getStockStatus() != null && customerData.getStockStatus().equalsIgnoreCase("NOT AVAILABLE")) {
//            holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//
//            holder.adapterPickupVerificationBinding.statusIcon.setVisibility(View.VISIBLE);
//
//        } else if (customerData.getStockStatus() != null && customerData.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
//            holder.adapterPickupVerificationBinding.statusIcon.setRotation(0);
//            holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//
//            holder.adapterPickupVerificationBinding.statusIcon.setVisibility(View.VISIBLE);
//        }

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
        return salesLineList.size();
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
