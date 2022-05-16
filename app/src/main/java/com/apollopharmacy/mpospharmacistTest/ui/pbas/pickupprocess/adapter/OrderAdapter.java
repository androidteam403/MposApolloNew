package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogItemStatusDropdownPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> implements StatusListAdapter.StatusListAdapterCallback, StatusUpdateCallback {
    private Context mContext;
    private List<RacksDataResponse.FullfillmentDetail> fullfillmentList;
    private FullfillmentProductListAdapter productListAdapter;
    private PickupProcessMvpView pickupProcessMvpView;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    private boolean firstAccessCheck;
    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;
    private int getOrderPos;
    private int getPos;
    private String status;


    public OrderAdapter(Context mContext, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, PickupProcessMvpView pickupProcessMvpView) {
        this.mContext = mContext;
        this.selectedOmsHeaderList = selectedOmsHeaderList;
        this.pickupProcessMvpView = pickupProcessMvpView;

    }


    public OrderAdapter(Context context, List<RacksDataResponse.FullfillmentDetail> fullfillmentList, PickupProcessMvpView pickupProcessMvpView, List<List<RackAdapter.RackBoxModel.ProductData>> fullfillmentListOfListFiltered, boolean acessCheck) {
        this.mContext = context;
        this.fullfillmentList = fullfillmentList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.listOfList = fullfillmentListOfListFiltered;
        this.firstAccessCheck = acessCheck;
    }

    public void setSelectedOmsHeaderList(List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList) {
        this.selectedOmsHeaderList = selectedOmsHeaderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderPBinding orderBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_order_p, parent, false);
        return new ViewHolder(orderBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = selectedOmsHeaderList.get(position);
        holder.orderBinding.fullfillmentID.setText(omsHeader.getRefno());
        holder.orderBinding.totalItems.setText(String.valueOf(omsHeader.getGetOMSTransactionResponse().getSalesLine().size()));
        holder.orderBinding.rightArrow.setOnClickListener(v -> {
            if (pickupProcessMvpView != null)
                pickupProcessMvpView.onClickOrderAdapterArrow(position);
        });

        if (omsHeader.getOrderPickup()) {
            holder.orderBinding.orderStatus.setText("Completed");
        } else {
            holder.orderBinding.orderStatus.setText("Pending");
        }

        holder.orderBinding.customerType.setText(omsHeader.getCustomerType());
        holder.orderBinding.orderSource.setText(omsHeader.getOrderSource());
        holder.orderBinding.orderDate.setText(omsHeader.getCreatedDateTime());
        holder.orderBinding.deliveryDate.setText(omsHeader.getDeliveryDate());
        holder.orderBinding.shippingMethodType.setText(omsHeader.getShippingMethod());
        holder.orderBinding.stockStatus.setText(omsHeader.getStockStatus());
        holder.orderBinding.paymentSource.setText(omsHeader.getPaymentSource());
        holder.orderBinding.orderType.setText(omsHeader.getOrderType());
        holder.orderBinding.customerName.setText(omsHeader.getGetOMSTransactionResponse().getCustomerName());
        holder.orderBinding.vendorId.setText(omsHeader.getVendorId());
        holder.orderBinding.mobileNumber.setText(omsHeader.getGetOMSTransactionResponse().getMobileNO());
//       holder.orderBinding.orderbillvalue.setText(omsHeader.getGetOMSTransactionResponse().getRoundedAmount());
        holder.orderBinding.doctorName.setText(omsHeader.getGetOMSTransactionResponse().getDoctorName());
        holder.orderBinding.statecode.setText(omsHeader.getGetOMSTransactionResponse().getState());
        holder.orderBinding.city.setText(omsHeader.getGetOMSTransactionResponse().getBillingCity());
        holder.orderBinding.address.setText(omsHeader.getGetOMSTransactionResponse().getCustAddress());
        holder.orderBinding.pincode.setText(omsHeader.getGetOMSTransactionResponse().getPincode());

        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("PARTIAL")) {
            holder.orderBinding.statusandicon.setVisibility(View.VISIBLE);
            holder.orderBinding.statusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.orderBinding.statusText.setText("PARTIAL");
        } else if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.orderBinding.statusandicon.setVisibility(View.VISIBLE);
            holder.orderBinding.statusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
            holder.orderBinding.statusText.setText("NOT AVAILABLE");
        } else if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("FULL")) {
            holder.orderBinding.statusImage.setRotation(0);
            holder.orderBinding.statusandicon.setVisibility(View.VISIBLE);
            holder.orderBinding.statusImage.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.orderBinding.statusText.setText("FULL");
        } else {
            holder.orderBinding.statusandicon.setVisibility(View.GONE);
        }


        NewSelectedOrderAdapter productListAdapter = new NewSelectedOrderAdapter(mContext, omsHeader.getGetOMSTransactionResponse().getSalesLine(), pickupProcessMvpView, this, position, omsHeader.getRefno(), selectedOmsHeaderList.get(position));
        new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, true);
        holder.orderBinding.productListRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        holder.orderBinding.productListRecycler.setAdapter(productListAdapter);

        if (omsHeader.isExpanded()) {
            holder.orderBinding.rightArrow.setImageResource(R.drawable.ic_arrow_drop_up);
            holder.orderBinding.rightArrow.setRotation(0);
            holder.orderBinding.rackChild2Layout.setVisibility(View.VISIBLE);
        } else {
            holder.orderBinding.rightArrow.setImageResource(R.drawable.right_arrow_black);
            holder.orderBinding.rightArrow.setRotation(90);
            holder.orderBinding.rackChild2Layout.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(view -> {
            if (pickupProcessMvpView != null)
                pickupProcessMvpView.onClickOrderItem(position, omsHeader);
        });

        holder.orderBinding.itemStatusDropdown.setOnClickListener(view -> {
            BottomSheetDialog itemStatusDropdownDialog = new BottomSheetDialog(mContext);
            DialogItemStatusDropdownPBinding itemStatusDropdownBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_item_status_dropdown_p, null, false);
            itemStatusDropdownDialog.setContentView(itemStatusDropdownBinding.getRoot());

            List<String> statusList = new ArrayList<>();
            statusList.add("Partially Filled");
            statusList.add("Fully Filled");
            statusList.add("Not Available");
            statusList.add("Skipped");

            StatusListAdapter statusListAdapter = new StatusListAdapter(mContext, statusList, this, holder.orderBinding, itemStatusDropdownDialog);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            itemStatusDropdownBinding.statusListRecycler.setLayoutManager(mLayoutManager);
            itemStatusDropdownBinding.statusListRecycler.setAdapter(statusListAdapter);

            itemStatusDropdownDialog.show();
        });

    }

    private void goToNextCheck(int position, List<RackAdapter.RackBoxModel.ProductData> productDataList) {
        if (fullfillmentList.get(position).getExpandStatus() == 0) {
            fullfillmentList.get(position).setExpandStatus(1);
            notifyDataSetChanged();
        } else if (fullfillmentList.get(position).getExpandStatus() == 2) {
            fullfillmentList.get(position).setExpandStatus(1);
            notifyDataSetChanged();
        } else if (fullfillmentList.get(position).getExpandStatus() == 1) {
            multipleStatusCheckDummy(position);
        } else if (fullfillmentList.get(position).getExpandStatus() == 3) {
            fullfillmentList.get(position).setExpandStatus(4);
            notifyDataSetChanged();
        } else if (fullfillmentList.get(position).getExpandStatus() == 4) {
            multipleStatusCheckDummy(position);
        } else if (fullfillmentList.get(position).getExpandStatus() == 5) {
            fullfillmentList.get(position).setExpandStatus(6);
            notifyDataSetChanged();
        } else if (fullfillmentList.get(position).getExpandStatus() == 6) {
            multipleStatusCheckDummy(position);
        } else if (fullfillmentList.get(position).getExpandStatus() == 7) {
            fullfillmentList.get(position).setExpandStatus(8);
            notifyDataSetChanged();
        } else if (fullfillmentList.get(position).getExpandStatus() == 8) {
            multipleStatusCheckDummy(position);
        }
    }

    private void multipleStatusCheckDummy(int position) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < pickupProcessMvpView.productsNextPosReturn().size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(pickupProcessMvpView.productsNextPosReturn().get(j).getProductId())) {
                            if (pickupProcessMvpView.productsNextPosReturn().get(j).getStatus() != null) {
                                if (pickupProcessMvpView.productsNextPosReturn().get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    pickupProcessMvpView.productsNextPosReturn().get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < pickupProcessMvpView.productsNextPosReturn().size(); i++) {
            if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus() != null) {
                if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == pickupProcessMvpView.productsNextPosReturn().size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (pickupProcessMvpView.productsNextPosReturn().get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == pickupProcessMvpView.productsNextPosReturn().size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

        if (partial) {
            fullfillmentList.get(position).setExpandStatus(3);
            notifyDataSetChanged();
        } else if (notAvailable) {
            fullfillmentList.get(position).setExpandStatus(5);
            notifyDataSetChanged();
        } else if (full) {
            fullfillmentList.get(position).setExpandStatus(7);
            notifyDataSetChanged();
        } else {
            if (fullfillmentList.get(position).getExpandStatus() == 9) {
                fullfillmentList.get(position).setExpandStatus(8);
            } else {
                fullfillmentList.get(position).setExpandStatus(2);
            }
            notifyDataSetChanged();
        }
    }

    private void multipleStatusCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;


        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (productDataList.get(j).getStatus() != null) {
                                if (productDataList.get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    productDataList.get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productDataList.size(); i++) {
            if (productDataList.get(i).getStatus() != null) {
                if (productDataList.get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == productDataList.size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == productDataList.size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

        if (partial) {
            fullfillmentList.get(position).setExpandStatus(3);
            notifyDataSetChanged();
        } else if (notAvailable) {
            fullfillmentList.get(position).setExpandStatus(5);
            notifyDataSetChanged();
        } else if (full) {
            fullfillmentList.get(position).setExpandStatus(7);
            notifyDataSetChanged();
        } else {
            if (fullfillmentList.get(position).getExpandStatus() == 9) {
                fullfillmentList.get(position).setExpandStatus(8);
            } else {
                fullfillmentList.get(position).setExpandStatus(2);
            }
            notifyDataSetChanged();
        }
    }

    private void firstTimeMultipleStatusCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position, AdapterOrderPBinding rackBinding) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (productDataList.get(j).getStatus() != null) {
                                if (productDataList.get(j).getStatus().equalsIgnoreCase(pickupProcessMvpView.productList().get(i).get(k).getStatus()))
                                    productDataList.get(j).setStatus(pickupProcessMvpView.productList().get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productDataList.size(); i++) {
            if (productDataList.get(i).getStatus() != null) {
                if (productDataList.get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == productDataList.size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == productDataList.size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

//        if (partial) {
//            fullfillmentList.get(position).setExpandStatus(3);
//            rackBinding.rackChild2Layout.setBackground(mContext.getResources().getDrawable(R.drawable.square_stroke_bg));
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Partial");
//            rackBinding.statusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_partial));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//            rackBinding.rackChild2Layout.setVisibility(View.GONE);
//            rackBinding.rackChild2Layout.setBackground(null);
//        } else if (notAvailable) {
//            fullfillmentList.get(position).setExpandStatus(5);
//            rackBinding.rackChild2Layout.setBackgroundColor(mContext.getResources().getColor(R.color.trans_red));
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Not Available");
//            rackBinding.statusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//            rackBinding.rackChild2Layout.setVisibility(View.GONE);
//            rackBinding.rackChild2Layout.setBackground(null);
//        } else if (full) {
//            fullfillmentList.get(position).setExpandStatus(7);
//            rackBinding.rackChild2Layout.setBackground(mContext.getResources().getDrawable(R.drawable.square_stroke_green));
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Full");
//            rackBinding.statusIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//            rackBinding.rackChild2Layout.setVisibility(View.GONE);
//            rackBinding.rackChild2Layout.setBackground(null);
//
//        }
    }

//    private void completedViewCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position, AdapterOrderPBinding orderBinding) {
//        int completedFlag = 0;
//        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
//            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
//                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
//                    for (int j = 0; j < productDataList.size(); j++) {
//                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
//                            if (pickupProcessMvpView.productList().get(i).get(k).isProductStatusFillingUpdate()) {
//                                productDataList.get(j).setProductStatusFillingUpdate(pickupProcessMvpView.productList().get(i).get(k).isProductStatusFillingUpdate());
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
//            for (int i = 0; i < productDataList.size(); i++) {
//                if (productDataList.get(i).isProductStatusFillingUpdate()) {
//                    completedFlag = completedFlag + 1;
//                }
//            }
//
//            if (completedFlag == productDataList.size()) {
//                if (fullfillmentList.get(position).getExpandStatus() != 5 && fullfillmentList.get(position).getExpandStatus() != 6 && fullfillmentList.get(position).getExpandStatus() != 7) {
//                    fullfillmentList.get(position).setExpandStatus(5);
//                    orderBinding.orderChildLayout.setBackground(mContext.getResources().getDrawable(R.drawable.square_stroke_green));
//                    orderBinding.start.setVisibility(View.GONE);
//                    orderBinding.status.setText("Completed");
//                    orderBinding.statusIcon.setVisibility(View.VISIBLE);
//                    orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                    orderBinding.rackChild2Layout.setVisibility(View.GONE);
//                    orderBinding.rackChild2Layout.setBackground(null);
//                }
//            }
//        }
//
//    }

    @Override
    public int getItemCount() {
        return selectedOmsHeaderList.size();
    }

    @Override
    public void onClickStatusListItem(String status, AdapterOrderPBinding adapterOrderBinding, BottomSheetDialog itemStatusDropdownDialog) {
        switch (status) {
            case "Partially Filled":
                adapterOrderBinding.itemStatusText.setText(status);
                adapterOrderBinding.icItemStatus.setRotation(270);
                adapterOrderBinding.icItemStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
                itemStatusDropdownDialog.dismiss();
                break;
            case "Fully Filled":
                adapterOrderBinding.itemStatusText.setText(status);
                adapterOrderBinding.icItemStatus.setRotation(0);
                adapterOrderBinding.icItemStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_circle_tick));
                itemStatusDropdownDialog.dismiss();
                break;
            case "Not Available":
                adapterOrderBinding.itemStatusText.setText(status);
                adapterOrderBinding.icItemStatus.setRotation(270);
                adapterOrderBinding.icItemStatus.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_available));
                itemStatusDropdownDialog.dismiss();
                break;
            case "Skipped":
                itemStatusDropdownDialog.dismiss();
                break;
            default:
        }
    }

    @Override
    public void onClickUpdate(int orderAdapterPos, int newSelectedOrderAdapterPos, String itemId) {
        if (pickupProcessMvpView != null) {
            pickupProcessMvpView.onClickItemStatusUpdate(orderAdapterPos, newSelectedOrderAdapterPos, status);
                    ;
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrderPBinding orderBinding;

        public ViewHolder(@NonNull AdapterOrderPBinding orderBinding) {
            super(orderBinding.getRoot());
            this.orderBinding = orderBinding;
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
            private String status;

            private boolean productStatusFillingUpdate;

            private boolean finalStatusUpdate;

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
