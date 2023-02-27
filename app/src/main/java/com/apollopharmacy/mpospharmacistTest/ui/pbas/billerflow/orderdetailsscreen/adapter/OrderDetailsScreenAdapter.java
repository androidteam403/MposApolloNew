package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsScreenAdapter extends RecyclerView.Adapter<OrderDetailsScreenAdapter.ViewHolder> {
    private Context context;
    List<SalesLineEntity> products;
    List<PickPackReservation> responseList;
    List<GetBatchInfoRes.BatchListObj> selectedBatchList;
    private CustomerDataResBean customerDataResBean;

    public OrderDetailsScreenAdapter(Context context, List<SalesLineEntity> products, List<PickPackReservation> responseList, List<GetBatchInfoRes.BatchListObj> selectedBatchList, CustomerDataResBean customerDataResBean) {
        this.context = context;
        this.products = products;
        this.responseList = responseList;
        this.selectedBatchList = selectedBatchList;
        this.customerDataResBean = customerDataResBean;
    }

    @NonNull
    @Override
    public OrderDetailsScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderDetailsScreenPBinding adapterBillerOrdersScreenBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_order_details_screen_p, parent, false);
        return new ViewHolder(adapterBillerOrdersScreenBinding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailsScreenAdapter.ViewHolder holder, int position) {
        SalesLineEntity fullfillmentDetail = products.get(position);
        holder.adapterOrderDetailsScreenBinding.productName.setText(fullfillmentDetail.getItemName());
        holder.adapterOrderDetailsScreenBinding.rackId.setText(fullfillmentDetail.getRackId());
        holder.adapterOrderDetailsScreenBinding.availableQty.setText("/" + Math.round(fullfillmentDetail.getQty()));
        if (responseList != null) {
            double pickedUpQty = 0.0;
            for (PickPackReservation pickPackReservation : responseList) {
                if (pickPackReservation.getPickupItemId().equals(fullfillmentDetail.getItemId())) {
                    pickedUpQty = pickedUpQty + pickPackReservation.getPickupQty();
                }
            }
            holder.adapterOrderDetailsScreenBinding.capturesQty.setText(String.valueOf(Math.round(pickedUpQty)));

            if (pickedUpQty >= fullfillmentDetail.getQty()) {
                fullfillmentDetail.setItemStatus(3);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(0);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            } else if (pickedUpQty > 0 && pickedUpQty < fullfillmentDetail.getQty()) {
                fullfillmentDetail.setItemStatus(2);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(90);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            } else {
                fullfillmentDetail.setItemStatus(1);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(0);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            }
        }
//        if (responseList != null && fullfillmentDetail != null) {
//            if (responseList.get(position).getPickupQty() >= fullfillmentDetail.getQty()) {
//                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(0);
//                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//            } else if (responseList.get(position).getPickupQty() > 0 && responseList.get(position).getPickupQty() < fullfillmentDetail.getQty()) {
//                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
//
//            } else {
//                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//
//            }
//        }

//        if (selectedBatchList != null && selectedBatchList.size() > 0) {
//            List<GetBatchInfoRes.BatchListObj> selectedBatchListTemp = new ArrayList<>();
//            for (GetBatchInfoRes.BatchListObj batchListObj : selectedBatchList) {
//                if (batchListObj.getItemID().equals(products.get(position).getItemId())) {
//                    for (PickPackReservation pickPackReservation : customerDataResBean.getPickPackReservation()) {
//                        if (pickPackReservation.getPickupItemId().equals(batchListObj.getItemID())
//                                && pickPackReservation.getPickupInventBatchId().equals(batchListObj.getBatchNo())) {
//                            batchListObj.setREQQTY(pickPackReservation.getPickupQty());
//                            selectedBatchListTemp.add(batchListObj);
//                        }
//                    }
//                }
//            }
//            List<GetBatchInfoRes.BatchListObj> newList = selectedBatchListTemp.stream()
//                    .distinct()
//                    .collect(Collectors.toList());


        List<PickPackReservation> pickPackReservationList = new ArrayList<>();
        for (PickPackReservation pickPackReservation : customerDataResBean.getPickPackReservation()) {
            if (pickPackReservation.getPickupItemId().equalsIgnoreCase(fullfillmentDetail.getItemId())) {
                pickPackReservationList.add(pickPackReservation);
            }
        }
        if (pickPackReservationList.size() > 0)
            holder.adapterOrderDetailsScreenBinding.headings.setVisibility(View.VISIBLE);
        else holder.adapterOrderDetailsScreenBinding.headings.setVisibility(View.GONE);
        SelectedBatchesListAdapterr selectedBatchesListAdapterr = new SelectedBatchesListAdapterr(context, pickPackReservationList, products);
        new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
        holder.adapterOrderDetailsScreenBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
        holder.adapterOrderDetailsScreenBinding.selectedbatchesRecycler.setAdapter(selectedBatchesListAdapterr);
//    }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrderDetailsScreenPBinding adapterOrderDetailsScreenBinding;

        public ViewHolder(@NonNull AdapterOrderDetailsScreenPBinding adapterOrderDetailsScreenBinding) {
            super(adapterOrderDetailsScreenBinding.getRoot());
            this.adapterOrderDetailsScreenBinding = adapterOrderDetailsScreenBinding;
        }
    }
}

