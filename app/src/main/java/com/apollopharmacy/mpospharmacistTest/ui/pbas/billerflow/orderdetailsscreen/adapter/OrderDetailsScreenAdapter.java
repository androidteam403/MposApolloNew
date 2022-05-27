package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderDetailsScreenAdapter.ViewHolder holder, int position) {
        SalesLineEntity fullfillmentDetail = products.get(position);

        holder.adapterOrderDetailsScreenBinding.productName.setText(fullfillmentDetail.getItemName());
//        holder.adapterOrderDetailsScreenBinding.quantity.setText(fullfillmentDetail.getRequiredQuantity() + "/10");
//        holder.adapterOrderDetailsScreenBinding.batchNo.setText(fullfillmentDetail.getInventBatchId());
//        holder.adapterOrderDetailsScreenBinding.apolloMrp.setText("-");
        holder.adapterOrderDetailsScreenBinding.rackId.setText(fullfillmentDetail.getRackId());
//        holder.adapterOrderDetailsScreenBinding.stripMrp.setText(String.valueOf(fullfillmentDetail.getMRP()));

        holder.adapterOrderDetailsScreenBinding.availableQty.setText("/" + Math.round(fullfillmentDetail.getQty()));
        if (responseList != null) {
            holder.adapterOrderDetailsScreenBinding.capturesQty.setText(String.valueOf(Math.round(responseList.get(position).getPickupQty())));

        }


        if (responseList != null && fullfillmentDetail != null) {
            if (responseList.get(position).getPickupQty() >= fullfillmentDetail.getQty()) {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(0);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            } else if (responseList.get(position).getPickupQty() > 0 && responseList.get(position).getPickupQty() < fullfillmentDetail.getQty()) {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));

            } else {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));

            }
        }

        if (selectedBatchList != null && selectedBatchList.size() > 0) {
            List<GetBatchInfoRes.BatchListObj> selectedBatchListTemp = new ArrayList<>();
            for (GetBatchInfoRes.BatchListObj batchListObj : selectedBatchList) {
                if (batchListObj.getItemID().equals(products.get(position).getItemId())) {
                    for (PickPackReservation pickPackReservation : customerDataResBean.getPickPackReservation()) {
                        if (pickPackReservation.getPickupItemId().equals(batchListObj.getItemID())
                                && pickPackReservation.getPickupInventBatchId().equals(batchListObj.getBatchNo())) {
                            batchListObj.setREQQTY(pickPackReservation.getPickupQty());
                            selectedBatchListTemp.add(batchListObj);
                        }
                    }
                }
            }

            holder.adapterOrderDetailsScreenBinding.headings.setVisibility(View.VISIBLE);
            SelectedBatchesListAdapterr selectedBatchesListAdapterr = new SelectedBatchesListAdapterr(context, selectedBatchListTemp, products);
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.adapterOrderDetailsScreenBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.adapterOrderDetailsScreenBinding.selectedbatchesRecycler.setAdapter(selectedBatchesListAdapterr);
        }


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

