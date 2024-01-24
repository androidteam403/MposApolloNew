package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSummaryFullfillmentPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummary.PickUpSummaryMvpView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SummaryFullfillmentAdapter extends RecyclerView.Adapter<SummaryFullfillmentAdapter.ViewHolder> {
    private Context context;
    private List<RacksDataResponse.FullfillmentDetail> fullfillmentList;
    private SummaryProductsAdapter productListAdapter;
    private PickUpSummaryMvpView pickupProcessMvpView;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    private List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList;
    private boolean firstAccessCheck;
    int full = 0, par = 0, not = 0;
    String itemStatus;

    public SummaryFullfillmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> selectedOmsHeaderList, PickUpSummaryMvpView pickupProcessMvpView) {
        this.context = context;
        this.selectedOmsHeaderList = selectedOmsHeaderList;
        this.pickupProcessMvpView = pickupProcessMvpView;
    }

    @NonNull
    @Override
    public SummaryFullfillmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSummaryFullfillmentPBinding orderBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_summary_fullfillment_p, parent, false);
        return new SummaryFullfillmentAdapter.ViewHolder(orderBinding);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SummaryFullfillmentAdapter.ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = selectedOmsHeaderList.get(position);
        holder.orderBinding.fullfillmentID.setText(omsHeader.getRefno());
        holder.orderBinding.boxId.setText(omsHeader.getScannedBarcode());
        AtomicReference<List<TransactionHeaderResponse.OMSHeader>> omsHeaderList = new AtomicReference<>();
        AtomicReference<String> selectedStatus = new AtomicReference<>("");
        Map<String, Long> groupByItemStatus = selectedOmsHeaderList.stream().collect(
                Collectors.groupingBy(TransactionHeaderResponse.OMSHeader::getItemStatus, Collectors.counting())
        );
        if (position == 0 || !selectedOmsHeaderList.get(position).getItemStatus().equalsIgnoreCase(selectedOmsHeaderList.get(position - 1).getItemStatus())) {
            holder.orderBinding.statusHeader.setVisibility(View.VISIBLE);
            groupByItemStatus.forEach((k, v) -> {
                if (omsHeader.getItemStatus().equalsIgnoreCase(k)) {
                    holder.orderBinding.itemCount.setText("(" + v + ")");
                }
            });
            holder.orderBinding.scannerLayout.setOnClickListener(view -> {
                for (Map.Entry<String, Long> entry : groupByItemStatus.entrySet()) {
                    if (omsHeader.getItemStatus().equalsIgnoreCase(entry.getKey())) {
                       omsHeaderList.set(selectedOmsHeaderList.stream().filter(omsHeader1 -> omsHeader.getItemStatus().equalsIgnoreCase(entry.getKey())).collect(Collectors.toList()));
                       selectedStatus.set(entry.getKey());
                    }
                }
//                pickupProcessMvpView.onClickEnableBtn(omsHeaderList.get());
                pickupProcessMvpView.onClickItem(position, omsHeaderList.get(), selectedStatus.get(), "");
            });
            if (omsHeader.getItemStatus().equalsIgnoreCase("FULL")) {
                holder.orderBinding.status.setText("Fully Available");
                holder.orderBinding.headerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.rounded_background_green));
            } else if (omsHeader.getItemStatus().equalsIgnoreCase("PARTIAL")) {
                holder.orderBinding.status.setText("Partially Available");
                holder.orderBinding.headerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
            } else {
                holder.orderBinding.status.setText("Not Available");
                holder.orderBinding.headerStatusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.rounded_background_red));
            }
        } else {
            holder.orderBinding.statusHeader.setVisibility(View.GONE);
        }
//        holder.orderBinding.totalItems.setText(String.valueOf(selectedOmsHeaderList.get(position).getGetOMSTransactionResponse().getSalesLine().size()));
//        holder.orderBinding.boxId.setText("-");
        if (omsHeader.getScannedBarcode() != null && !omsHeader.getScannedBarcode().isEmpty()) {
//            holder.orderBinding.taggedboxnumber.setText(selectedOmsHeaderList.get(position).getScannedBarcode());
//            holder.orderBinding.taggedboxnumber.setText(lastFiveDigits(String.valueOf(selectedOmsHeaderList.get(position).getScannedBarcode())));
        } else {
//            holder.orderBinding.taggedboxnumber.setText("-");
        }

//        if (omsHeader.isScanned()){
//            holder.orderBinding.deleteIcon.setVisibility(View.VISIBLE);
//            holder.orderBinding.scannedTick.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.orderBinding.deleteIcon.setVisibility(View.GONE);
//            holder.orderBinding.scannedTick.setVisibility(View.GONE);
//        }
        holder.orderBinding.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pickupProcessMvpView != null) {
                    pickupProcessMvpView.ondeleteItem(position,selectedOmsHeaderList);
                }

                }
        });

        holder.orderBinding.scanIcon.setOnClickListener(view -> {
            if (pickupProcessMvpView != null) {
                if (omsHeader.isEnabled()) {
                    pickupProcessMvpView.onClickItem(position,selectedOmsHeaderList, "", selectedOmsHeaderList.get(position).getItemStatus());
                }

//                for (int i=0;i<selectedOmsHeaderList.size();i++){
//                    if (!selectedOmsHeaderList.get(i).isScanned()){
//                        pickupProcessMvpView.onClickItem(i,selectedOmsHeaderList);
//
//                    }
//                }
            }
        });

        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//            holder.orderBinding.statusText.setText("NOT AVAILABLE");
//            holder.orderBinding.statuss.setText("Not Available");
            holder.orderBinding.greenTick.setVisibility(View.GONE);
            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.red));
            omsHeader.setOverallOrderStatus("3");

        } else if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("FULL")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//            holder.orderBinding.statusText.setText("FULL");
            holder.orderBinding.statusIcon.setRotation(0);
//            holder.orderBinding.statuss.setText("Full");
            holder.orderBinding.greenTick.setVisibility(View.VISIBLE);
            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.black));
            omsHeader.setOverallOrderStatus("1");

        }
        if (omsHeader.getItemStatus() != null && omsHeader.getItemStatus().equalsIgnoreCase("PARTIAL")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
//            holder.orderBinding.statusText.setText("PARTIAL");
//            holder.orderBinding.statuss.setText("Partial");
            holder.orderBinding.greenTick.setVisibility(View.VISIBLE);
            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.black));
            omsHeader.setOverallOrderStatus("2");

        }
        if (omsHeader.isEnabled()) {
            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.black));
        } else {
            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }
        if (omsHeader.isScanned()){
            holder.orderBinding.deleteIcon.setVisibility(View.VISIBLE);
            holder.orderBinding.scannedTick.setVisibility(View.VISIBLE);
//            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        else {
            holder.orderBinding.deleteIcon.setVisibility(View.GONE);
            holder.orderBinding.scannedTick.setVisibility(View.GONE);
//            holder.orderBinding.fullfillmentID.setTextColor(ContextCompat.getColor(context, R.color.grey));
        }

//        if (holder.orderBinding.statusText.getText().toString().equalsIgnoreCase("FULL")) {
//            full = full + 1;
//            pickupProcessMvpView.fullCount(String.valueOf(full));
//        } else if (holder.orderBinding.statusText.getText().toString().equalsIgnoreCase("PARTIAL")) {
//            par = par + 1;
//            pickupProcessMvpView.partialCount(String.valueOf(par));
//        } else if (holder.orderBinding.statusText.getText().toString().equalsIgnoreCase("NOT AVAILABLE")) {
//            not = not + 1;
//            pickupProcessMvpView.notAvailable(String.valueOf(not));
//        }

//        holder.orderBinding.onHold.setOnClickListener(view -> {
//            if (pickupProcessMvpView != null) {
//                pickupProcessMvpView.onClickOnHold(omsHeader);
//            }
//        });
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

    private void firstTimeMultipleStatusCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position, AdapterSummaryFullfillmentPBinding rackBinding) {
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
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
        } else if (notAvailable) {
            fullfillmentList.get(position).setExpandStatus(5);
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
        } else if (full) {
            fullfillmentList.get(position).setExpandStatus(7);
            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
        }
    }

    private void completedViewCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position, AdapterOrderPBinding orderBinding) {
        int completedFlag = 0;
        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < pickupProcessMvpView.productList().size(); i++) {
                for (int k = 0; k < pickupProcessMvpView.productList().get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (pickupProcessMvpView.productList().get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (pickupProcessMvpView.productList().get(i).get(k).isProductStatusFillingUpdate()) {
                                productDataList.get(j).setProductStatusFillingUpdate(pickupProcessMvpView.productList().get(i).get(k).isProductStatusFillingUpdate());
                            }
                        }
                    }
                }
            }
        }
        if (pickupProcessMvpView.productList() != null && pickupProcessMvpView.productList().size() > 0) {
            for (int i = 0; i < productDataList.size(); i++) {
                if (productDataList.get(i).isProductStatusFillingUpdate()) {
                    completedFlag = completedFlag + 1;
                }
            }

            if (completedFlag == productDataList.size()) {
                if (fullfillmentList.get(position).getExpandStatus() != 5 && fullfillmentList.get(position).getExpandStatus() != 6 && fullfillmentList.get(position).getExpandStatus() != 7) {
                    fullfillmentList.get(position).setExpandStatus(5);
                    orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
//                    orderBinding.start.setVisibility(View.GONE);
//                    orderBinding.status.setText("Completed");
//                    orderBinding.statusIcon.setVisibility(View.VISIBLE);
//                    orderBinding.statusLayout.setVisibility(View.VISIBLE);
                    orderBinding.rackChild2Layout.setVisibility(View.GONE);
                    orderBinding.rackChild2Layout.setBackground(null);
                }
            }
        }

    }

    public static String lastFiveDigits(String data) {
        String lastFourDigits = "";   //substring containing last 4 characters

        if (data.length() > 5) {
            lastFourDigits = data.substring(data.length() - 5);
        } else {
            lastFourDigits = data;
        }

        return lastFourDigits;
    }

    @Override
    public int getItemCount() {
        return selectedOmsHeaderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterSummaryFullfillmentPBinding orderBinding;

        public ViewHolder(@NonNull AdapterSummaryFullfillmentPBinding orderBinding) {
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
