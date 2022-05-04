package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFullfillmentProductListPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;

import java.util.List;

public class FullfillmentProductListAdapter extends RecyclerView.Adapter<FullfillmentProductListAdapter.ViewHolder> {
    private Context context;
    private List<RackAdapter.RackBoxModel.ProductData> productListModelList;
    private PickupProcessMvpView pickupProcessMvpView;
    private boolean isRackFlow;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    String fullfillmentId;

    public FullfillmentProductListAdapter(Context context, List<RackAdapter.RackBoxModel.ProductData> productListModelList, PickupProcessMvpView pickupProcessMvpView, boolean isRackFlow, List<List<RackAdapter.RackBoxModel.ProductData>> listOfList) {
        this.context = context;
        this.productListModelList = productListModelList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.isRackFlow = isRackFlow;
        this.listOfList = listOfList;
        this.fullfillmentId = fullfillmentId;
    }

    @NonNull
    @Override
    public FullfillmentProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFullfillmentProductListPBinding fullfillmentProductListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_fullfillment_product_list_p, parent, false);
        return new FullfillmentProductListAdapter.ViewHolder(fullfillmentProductListBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull FullfillmentProductListAdapter.ViewHolder holder, int position) {
        RackAdapter.RackBoxModel.ProductData productListModel = productListModelList.get(position);
        pickupProcessMvpView.productsNextPosReturn(productListModelList);

    }


    @Override
    public int getItemCount() {
        return productListModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFullfillmentProductListPBinding fullfillmentProductListBinding;

        public ViewHolder(@NonNull AdapterFullfillmentProductListPBinding fullfillmentProductListBinding) {
            super(fullfillmentProductListBinding.getRoot());
            this.fullfillmentProductListBinding = fullfillmentProductListBinding;
        }
    }
}
