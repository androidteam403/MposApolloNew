package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSummaryProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummaryMvpView;

import java.util.List;

public class SummaryProductsAdapter extends RecyclerView.Adapter<SummaryProductsAdapter.ViewHolder> {
    private Context context;
    private List<RackAdapter.RackBoxModel.ProductData> productListModelList;
    private PickUpSummaryMvpView pickupProcessMvpView;
    private boolean isRackFlow;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;

    public SummaryProductsAdapter(Context context, List<RackAdapter.RackBoxModel.ProductData> productListModelList, PickUpSummaryMvpView pickupProcessMvpView, boolean isRackFlow, List<List<RackAdapter.RackBoxModel.ProductData>> listOfList) {
        this.context = context;
        this.productListModelList = productListModelList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.isRackFlow = isRackFlow;
        this.listOfList = listOfList;
    }

    @NonNull
    @Override
    public SummaryProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSummaryProductsPBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_summary_products_p, parent, false);
        return new SummaryProductsAdapter.ViewHolder(productListBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull SummaryProductsAdapter.ViewHolder holder, int position) {
        RackAdapter.RackBoxModel.ProductData productListModel = productListModelList.get(position);
//        if (isRackFlow)
//            holder.productListBinding.rackIdLayout.setVisibility(View.GONE);
//        else
//            holder.productListBinding.rackIdLayout.setVisibility(View.VISIBLE);

        for (int k = 0; k < listOfList.size(); k++) {
            for (int l = 0; l < listOfList.get(k).size(); l++) {
                if (productListModel.getProductId().equalsIgnoreCase(listOfList.get(k).get(l).getProductId())) {
                    productListModel.setFinalStatusUpdate(listOfList.get(k).get(l).isFinalStatusUpdate());
                }
            }
        }

        if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("Full")) {
            statusHandlings(holder.productListBinding);
            holder.productListBinding.fullStatusColor.setVisibility(View.VISIBLE);
        } else if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("Partial")) {
            statusHandlings(holder.productListBinding);
            holder.productListBinding.partialStatusColor.setVisibility(View.VISIBLE);
        } else if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("NotAvailable")) {
            statusHandlings(holder.productListBinding);
            holder.productListBinding.notAvailableStatusColor.setVisibility(View.VISIBLE);
        }

        if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
            holder.productListBinding.capturesQty.setText(productListModel.getCapturedQuantity().toString() + "/");
        }
        if (productListModel.getBoxId() != null) {
            holder.productListBinding.rackRowId.setText(productListModel.getBoxId());
        } else {
            holder.productListBinding.rackBoxLay.setVisibility(View.GONE);
            holder.productListBinding.view.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 0.625f;
            params.setMarginStart(15);
            holder.productListBinding.proLay.setLayoutParams(params);
        }
        holder.productListBinding.productName.setText(productListModel.getProductName());
        holder.productListBinding.availableQty.setText(productListModel.getAvailableQuantity());
        holder.productListBinding.rackId.setText(productListModel.getRackId());

    }

    private void statusHandlings(AdapterSummaryProductsPBinding productListBinding) {
        productListBinding.statusIcon.setVisibility(View.GONE);
        productListBinding.fullStatusColor.setVisibility(View.GONE);
        productListBinding.partialStatusColor.setVisibility(View.GONE);
        productListBinding.notAvailableStatusColor.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return productListModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterSummaryProductsPBinding productListBinding;

        public ViewHolder(@NonNull AdapterSummaryProductsPBinding productListBinding) {
            super(productListBinding.getRoot());
            this.productListBinding = productListBinding;
        }
    }
}