package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderDetailsScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public class OrderDetailsScreenAdapter extends RecyclerView.Adapter<OrderDetailsScreenAdapter.ViewHolder> {
    private Context context;
    List<RacksDataResponse.FullfillmentDetail.Product> products;

    public OrderDetailsScreenAdapter(Context context, List<RacksDataResponse.FullfillmentDetail.Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public OrderDetailsScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderDetailsScreenPBinding adapterBillerOrdersScreenBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_order_details_screen_p, parent, false);
        return new ViewHolder(adapterBillerOrdersScreenBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsScreenAdapter.ViewHolder holder, int position) {
        RacksDataResponse.FullfillmentDetail.Product fullfillmentDetail = products.get(position);
        holder.adapterOrderDetailsScreenBinding.productName.setText(fullfillmentDetail.getProductName());
        holder.adapterOrderDetailsScreenBinding.quantity.setText(fullfillmentDetail.getRequiredQuantity() + "/10");
        holder.adapterOrderDetailsScreenBinding.rackId.setText(fullfillmentDetail.getRackId());
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

