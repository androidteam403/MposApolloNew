package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.MedicineInfoEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.PickUpVerificationMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;

import java.util.ArrayList;
import java.util.List;

public class PickUpVerificationAdapter extends RecyclerView.Adapter<PickUpVerificationAdapter.ViewHolder> {
    private Activity activity;
    private Context context;
    private List<RackAdapter.RackBoxModel.ProductData> productDataList;
    List<GetOMSTransactionResponse> salesLineEntityList ;
    private  List<GetOMSTransactionResponse> transactionList=new ArrayList<>();
    private PickUpVerificationMvpView pickUpVerificationMvpView;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;

    public PickUpVerificationAdapter(Context context, Activity activity, List<GetOMSTransactionResponse> transactionList,  PickUpVerificationMvpView pickUpVerificationMvpView) {
        this.context = context;
        this.activity = activity;
        this.transactionList = transactionList;

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
        GetOMSTransactionResponse customerData = transactionList.get(position);
        holder.adapterPickupVerificationBinding.productName.setText(customerData.getSalesLine().get(position).getItemName());

        if (customerData.getPickPackReservation() == null) {
            holder.adapterPickupVerificationBinding.capturesQty.setText(0 + "/");

        } else if(customerData.getPickPackReservation()!=null){
            holder.adapterPickupVerificationBinding.capturesQty.setText(customerData.getPickPackReservation().getPickupQty() + "/");
        }
        holder.adapterPickupVerificationBinding.availableQty.setText(String.valueOf(customerData.getSalesLine().get(position).getQty()));


        if (customerData.getPickPackReservation()!=null){
        if (customerData.getPickPackReservation().getPickupQty() >= customerData.getSalesLine().get(position).getQty()) {
            holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
        }
        } else if (customerData.getPickPackReservation()!=null) {
            if (customerData.getPickPackReservation().getPickupQty() > 0  && customerData.getSalesLine().get(position).getQty() > 0) {
                if (customerData.getPickPackReservation().getPickupQty() < customerData.getSalesLine().get(position).getQty()) {

                    holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
                }
            }
        } else if( customerData.getPickPackReservation()==null ) {
            holder.adapterPickupVerificationBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));

        }

        holder.adapterPickupVerificationBinding.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog statusUpdateDialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
                dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update_status_p, null, false);
                statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
                statusUpdateDialog.setCancelable(false);
                dialogUpdateStatusBinding.fullfillmentId.setText(transactionList.get(position).getRefno());
                dialogUpdateStatusBinding.productName.setText(transactionList.get(position).getSalesLine().get(position).getItemName());

                dialogUpdateStatusBinding.fullPickedRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
                    }
                });

                dialogUpdateStatusBinding.partiallyPickedRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
                    }
                });

                dialogUpdateStatusBinding.notAvailableRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
                    }
                });
                dialogUpdateStatusBinding.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        statusUpdateDialog.dismiss();
                        pickUpVerificationMvpView.onClickUpdate(position,transactionList.get(position).getRefno());
                        holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
                        holder.adapterPickupVerificationBinding.status.setVisibility(View.VISIBLE);
                        if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                            customerData.getSalesLine().get(position).setPackerStatus("STOCK AVAILABLE");

                        }
                        else if(dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
                            customerData.getSalesLine().get(position).setPackerStatus("Partial AVAILABLE");
                        }
                        else if(dialogUpdateStatusBinding.notAvailableRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                            customerData.getSalesLine().get(position).setPackerStatus("Not AVAILABLE");

                        }

                    }
                });

                dialogUpdateStatusBinding.dismissDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        statusUpdateDialog.dismiss();
                    }
                });
                statusUpdateDialog.show();


//                pickUpVerificationMvpView.onClickItemStatusUpdate(position);
            }

        });
        holder.adapterPickupVerificationBinding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog statusUpdateDialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
                dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update_status_p, null, false);
                statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
                statusUpdateDialog.setCancelable(false);
                dialogUpdateStatusBinding.fullfillmentId.setText(transactionList.get(position).getRefno());
                dialogUpdateStatusBinding.productName.setText(transactionList.get(position).getSalesLine().get(position).getItemName());

                dialogUpdateStatusBinding.fullPickedRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
                    }
                });

                dialogUpdateStatusBinding.partiallyPickedRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
                    }
                });

                dialogUpdateStatusBinding.notAvailableRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
                        dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
                    }
                });
                dialogUpdateStatusBinding.update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pickUpVerificationMvpView.onClickUpdate(position,transactionList.get(position).getRefno());
                        statusUpdateDialog.dismiss();
                        holder.adapterPickupVerificationBinding.update.setVisibility(View.GONE);
                        holder.adapterPickupVerificationBinding.status.setVisibility(View.VISIBLE);
                        if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                            customerData.getSalesLine().get(position).setPackerStatus("STOCK AVAILABLE");

                        }
                        else if(dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
                            customerData.getSalesLine().get(position).setPackerStatus("Partial AVAILABLE");

                        }
                        else if(dialogUpdateStatusBinding.notAvailableRadio.isChecked()){
                            holder.adapterPickupVerificationBinding.status.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                            customerData.getSalesLine().get(position).setPackerStatus("Not AVAILABLE");

                        }

                    }
                });

                dialogUpdateStatusBinding.dismissDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        statusUpdateDialog.dismiss();
                    }
                });
                statusUpdateDialog.show();


//                pickUpVerificationMvpView.onClickItemStatusUpdate(position);
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
        return transactionList.size();
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
