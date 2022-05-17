package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterProductListPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<RackAdapter.RackBoxModel.ProductData> productListModelList;
    private PickupProcessMvpView pickupProcessMvpView;
    private boolean isRackFlow;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;

    public ProductListAdapter(Context context, List<RackAdapter.RackBoxModel.ProductData> productListModelList, PickupProcessMvpView pickupProcessMvpView, boolean isRackFlow, List<List<RackAdapter.RackBoxModel.ProductData>> listOfList) {
        this.context = context;
        this.productListModelList = productListModelList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.isRackFlow = isRackFlow;
        this.listOfList = listOfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterProductListPBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_product_list_p, parent, false);
        return new ProductListAdapter.ViewHolder(productListBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        RackAdapter.RackBoxModel.ProductData productListModel = productListModelList.get(position);
        pickupProcessMvpView.productsNextPosReturn(productListModelList);
    }

    @Override
    public int getItemCount() {
        return productListModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterProductListPBinding productListBinding;

        public ViewHolder(@NonNull AdapterProductListPBinding productListBinding) {
            super(productListBinding.getRoot());
            this.productListBinding = productListBinding;
        }
    }
}
