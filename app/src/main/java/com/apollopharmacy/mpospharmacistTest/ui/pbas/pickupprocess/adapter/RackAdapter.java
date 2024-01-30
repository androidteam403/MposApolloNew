package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterRackBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RackWiseSortedData;

import java.util.List;

public class RackAdapter extends RecyclerView.Adapter<RackAdapter.ViewHolder> {

    private Context mContext;
    private List<RackWiseSortedData> rackWiseSortedDataList;
    private PickupProcessMvpView mvpView;

    public RackAdapter(Context mContext, List<RackWiseSortedData> rackWiseSortedDataList, PickupProcessMvpView mvpView) {
        this.mContext = mContext;
        this.rackWiseSortedDataList = rackWiseSortedDataList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public RackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRackBinding rackBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_rack, parent, false);
        return new RackAdapter.ViewHolder(rackBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull RackAdapter.ViewHolder holder, int position) {
        RackWiseSortedData rackWiseSortedData = rackWiseSortedDataList.get(position);
        holder.rackBinding.rackId.setText(rackWiseSortedData.getRackId());

        if (rackWiseSortedData.getRackStatus() != null && rackWiseSortedData.getRackStatus().equalsIgnoreCase("PARTIAL")) {
            holder.rackBinding.rackStatusIcon.setRotation(270);
            holder.rackBinding.rackStatus.setVisibility(View.VISIBLE);
            holder.rackBinding.rackStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.rackBinding.rackStatusText.setText("PARTIAL");
        } else if (rackWiseSortedData.getRackStatus() != null && rackWiseSortedData.getRackStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.rackBinding.rackStatusIcon.setRotation(0);
            holder.rackBinding.rackStatus.setVisibility(View.VISIBLE);
            holder.rackBinding.rackStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
            holder.rackBinding.rackStatusText.setText("NOT AVAILABLE");
        } else if (rackWiseSortedData.getRackStatus() != null && rackWiseSortedData.getRackStatus().equalsIgnoreCase("FULL")) {
            holder.rackBinding.rackStatusIcon.setRotation(0);
            holder.rackBinding.rackStatus.setVisibility(View.VISIBLE);
            holder.rackBinding.rackStatusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.rackBinding.rackStatusText.setText("FULL");
        } else {
            holder.rackBinding.rackStatus.setVisibility(View.GONE);
        }
        ProductListAdapter productListAdapter = new ProductListAdapter(mContext, rackWiseSortedData.getGetOMSTransactionResponse().getSalesLine(), mvpView, rackWiseSortedData.getBoxIdList());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        holder.rackBinding.productListRecycler.setLayoutManager(mLayoutManager);
        holder.rackBinding.productListRecycler.setAdapter(productListAdapter);


        RackRowAdapter rackRowAdapter = new RackRowAdapter(mContext, rackWiseSortedData.getBoxIdList(), rackWiseSortedData.getGetOMSTransactionResponse().getSalesLine(), productListAdapter);
        GridLayoutManager horizontalLayoutManagaer = new GridLayoutManager(mContext, 4);
        holder.rackBinding.rackRowRecycler.setLayoutManager(horizontalLayoutManagaer);
        holder.rackBinding.rackRowRecycler.setAdapter(rackRowAdapter);



        if (rackWiseSortedDataList.get(position).isExpanded()) {
            holder.rackBinding.dropdown.setImageResource(R.drawable.right_arrow_black);
            holder.rackBinding.dropdown.setRotation(90);
            holder.rackBinding.productDetailsLayout.setVisibility(View.VISIBLE);
        } else {
            holder.rackBinding.dropdown.setImageResource(R.drawable.right_arrow_black);
            holder.rackBinding.dropdown.setRotation(0);
            holder.rackBinding.productDetailsLayout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.onClickRackAdapterOne(position);
                // mvpView.onClickRackAdapter(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rackWiseSortedDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRackBinding rackBinding;

        public ViewHolder(@NonNull AdapterRackBinding rackBinding) {
            super(rackBinding.getRoot());
            this.rackBinding = rackBinding;
        }
    }

    public static class RackBoxModel {
        private String rackBoxId;
        private int productsCuont;

        public int getProductsCuont() {
            return productsCuont;
        }

        public void setProductsCuont(int productsCuont) {
            this.productsCuont = productsCuont;
        }

        public String getRackBoxId() {
            return rackBoxId;
        }

        public void setRackBoxId(String rackBoxId) {
            this.rackBoxId = rackBoxId;
        }

        public static class ProductData {

            private String productId;
            private String productName;
            private String rackId;
            private String boxId;
            private String availableQuantity;
            private String requiredQuantity;
            private String capturedQuantity;
            private String batchId;
            private String itemStatus;
            private String status;

            private boolean productStatusFillingUpdate;

            private boolean finalStatusUpdate;

            private String packerStatus;

            public String getItemStatus() {
                return itemStatus;
            }

            public void setItemStatus(String itemStatus) {
                this.itemStatus = itemStatus;
            }

            public String getPackerStatus() {
                return packerStatus;
            }

            public void setPackerStatus(String packerStatus) {
                this.packerStatus = packerStatus;
            }

            public boolean isFinalStatusUpdate() {
                return finalStatusUpdate;
            }

            public void setFinalStatusUpdate(boolean finalStatusUpdate) {
                this.finalStatusUpdate = finalStatusUpdate;
            }

            public boolean isProductStatusFillingUpdate() {
                return productStatusFillingUpdate;
            }

            public void setProductStatusFillingUpdate(boolean productStatusFillingUpdate) {
                this.productStatusFillingUpdate = productStatusFillingUpdate;
            }

            public String getBoxId() {
                return boxId;
            }

            public void setBoxId(String boxId) {
                this.boxId = boxId;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getRackId() {
                return rackId;
            }

            public void setRackId(String rackId) {
                this.rackId = rackId;
            }

            public String getAvailableQuantity() {
                return availableQuantity;
            }

            public void setAvailableQuantity(String availableQuantity) {
                this.availableQuantity = availableQuantity;
            }

            public String getRequiredQuantity() {
                return requiredQuantity;
            }

            public void setRequiredQuantity(String requiredQuantity) {
                this.requiredQuantity = requiredQuantity;
            }

            public String getCapturedQuantity() {
                return capturedQuantity;
            }

            public void setCapturedQuantity(String capturedQuantity) {
                this.capturedQuantity = capturedQuantity;
            }

            public String getBatchId() {
                return batchId;
            }

            public void setBatchId(String batchId) {
                this.batchId = batchId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}