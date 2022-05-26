package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PickPackReservation;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.CustomerDataResBean;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public class   OrderDetailsScreenAdapter extends RecyclerView.Adapter<OrderDetailsScreenAdapter.ViewHolder> {
    private Context context;
    List<SalesLineEntity> products;
    List<PickPackReservation> responseList;

    public OrderDetailsScreenAdapter(Context context, List<SalesLineEntity> products,List<PickPackReservation> responseList) {
        this.context = context;
        this.products = products;
        this.responseList=responseList;
    }

    @NonNull
    @Override
    public OrderDetailsScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderDetailsScreenPBinding adapterBillerOrdersScreenBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_order_details_screen_p, parent, false);
        return new ViewHolder(adapterBillerOrdersScreenBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsScreenAdapter.ViewHolder holder, int position) {
        SalesLineEntity fullfillmentDetail = products.get(position);

        holder.adapterOrderDetailsScreenBinding.productName.setText(fullfillmentDetail.getItemName());
//        holder.adapterOrderDetailsScreenBinding.quantity.setText(fullfillmentDetail.getRequiredQuantity() + "/10");
        holder.adapterOrderDetailsScreenBinding.batchNo.setText(fullfillmentDetail.getInventBatchId());
        holder.adapterOrderDetailsScreenBinding.apolloMrp.setText("-");
        holder.adapterOrderDetailsScreenBinding.rackId.setText(fullfillmentDetail.getRackId());
        holder.adapterOrderDetailsScreenBinding.stripMrp.setText(String.valueOf(fullfillmentDetail.getMRP()));
        int qty= (int) fullfillmentDetail.getQty();
        holder.adapterOrderDetailsScreenBinding.availableQty.setText("/"+ String.valueOf(qty));
        if (responseList!=null){
            int cqty= (int) responseList.get(position).getPickupQty();
           holder.adapterOrderDetailsScreenBinding.capturesQty.setText(String.valueOf(cqty ));

        }



        if (responseList!=null && fullfillmentDetail !=null) {
            if (responseList.get(position).getPickupQty() >= fullfillmentDetail.getQty()) {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setRotation(0);
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            } else if (responseList.get(position).getPickupQty() > 0 && responseList.get(position).getPickupQty() < fullfillmentDetail.getQty()) {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));

            } else {
                holder.adapterOrderDetailsScreenBinding.pickerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));

            }
        }

    }

    @Override
    public int getItemCount() {
        return  products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrderDetailsScreenPBinding adapterOrderDetailsScreenBinding;
        public ViewHolder(@NonNull  AdapterOrderDetailsScreenPBinding adapterOrderDetailsScreenBinding) {
            super(adapterOrderDetailsScreenBinding.getRoot());
            this.adapterOrderDetailsScreenBinding=adapterOrderDetailsScreenBinding;
        }
    }
}

