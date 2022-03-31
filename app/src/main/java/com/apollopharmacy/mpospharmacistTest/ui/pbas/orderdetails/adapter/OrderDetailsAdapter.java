package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderdetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderDetailsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private final Context context;
    List<RacksDataResponse.FullfillmentDetail.Product> products;

    public OrderDetailsAdapter(List<RacksDataResponse.FullfillmentDetail.Product> products, Context context) {
        this.context = context;
        this.products = products;

    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        AdapterOrderDetailsPBinding adapterOrderDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_order_details_p, parent, false);
        return new ViewHolder(adapterOrderDetailsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder, int position) {


        RacksDataResponse.FullfillmentDetail.Product fullfillmentDetail = products.get(position);
        holder.adapterOrderDetailsBinding.productName.setText(fullfillmentDetail.getProductName());
        holder.adapterOrderDetailsBinding.quantity.setText(fullfillmentDetail.getAvailableQuantity());
        holder.adapterOrderDetailsBinding.rackId.setText(fullfillmentDetail.getRackId());


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrderDetailsPBinding adapterOrderDetailsBinding;

        public ViewHolder(@NonNull AdapterOrderDetailsPBinding adapterOrderDetailsBinding) {
            super(adapterOrderDetailsBinding.getRoot());
            this.adapterOrderDetailsBinding = adapterOrderDetailsBinding;
        }
    }
}
