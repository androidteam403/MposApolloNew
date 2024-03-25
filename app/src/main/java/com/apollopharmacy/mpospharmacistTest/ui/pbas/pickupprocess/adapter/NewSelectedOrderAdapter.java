
package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedPickupProcessProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;

import java.util.List;

public class NewSelectedOrderAdapter extends RecyclerView.Adapter<NewSelectedOrderAdapter.ViewHolder> implements NewSelectedOrderAdapterCallback {
    private PickupProcessMvpView pickupProcessMvpView;
    private boolean isRackFlow;
    String fullfillmentId;
    int fullFillmentPos;
    private StatusUpdateCallback mCallback;
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    public List<RackAdapter.RackBoxModel.ProductData> racksDataResponse;
    public Context context;
    double reqqty;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private OrderAdapter orderAdapter;
    String refNo;
    GetOMSTransactionResponse.SalesLine position;
    private int orderAdapterPos;
    TransactionHeaderResponse.OMSHeader omsHeader;
    List<TransactionHeaderResponse.OMSHeader> omsHeaderList;
    List<GetBatchInfoRes.BatchListObj> batchList;


    public NewSelectedOrderAdapter(Context context, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickupProcessMvpView pickupProcessMvpView, StatusUpdateCallback mCallback, int orderAdapterPos, String refno, List<TransactionHeaderResponse.OMSHeader> omsHeaderList) {
        this.context = context;
        this.salesLineList = salesLineList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.mCallback = mCallback;
        this.orderAdapterPos = orderAdapterPos;
        this.refNo = refno;
        this.omsHeader = this.omsHeader;
        this.omsHeaderList = omsHeaderList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_pickup_process_products_p, parent, false);
        return new NewSelectedOrderAdapter.ViewHolder(pickupSummaryDetailsProductsBinding);
    }

    int adapterPosition;

    @Override
    public void onBindViewHolder(@NonNull NewSelectedOrderAdapter.ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        this.position = salesLineList.get(position);

        if (position == salesLineList.size() - 1) {
            holder.pickupSummaryDetailsProductsBinding.bottomStripLine.setVisibility(View.GONE);
        }
        holder.pickupSummaryDetailsProductsBinding.productName.setText(salesLine.getItemName());
//        holder.pickupSummaryDetailsProductsBinding.rackId.setText(salesLine.getRackId());

//        holder.pickupSummaryDetailsProductsBinding.batchNo.setText(salesLine.getInventBatchId());
        holder.pickupSummaryDetailsProductsBinding.stripMrp.setText(String.valueOf(salesLine.getPrice()));
//        holder.pickupSummaryDetailsProductsBinding.quantity.setText(String.valueOf(salesLine.getQty()));
        holder.pickupSummaryDetailsProductsBinding.apolloMrp.setText("-");
        this.reqqty = salesLine.getQty();


        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("PARTIAL")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
            holder.pickupSummaryDetailsProductsBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
            holder.pickupSummaryDetailsProductsBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("FULL")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setRotation(0);
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
            holder.pickupSummaryDetailsProductsBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else {
            holder.pickupSummaryDetailsProductsBinding.itemLayout.setBackgroundColor(Color.TRANSPARENT);
        }


        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pickupProcessMvpView != null) {
                    pickupProcessMvpView.getBatchDetailsApiCall(salesLine, refNo, orderAdapterPos, position, omsHeader);
                }
            }
        });

        holder.pickupSummaryDetailsProductsBinding.start.setOnClickListener(view -> {
            if (pickupProcessMvpView != null) {
                Dialog dialog = new Dialog(context);
                DialogUpdateBinding dialogUpdateBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update, null, false);
                dialog.setContentView(dialogUpdateBinding.getRoot());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogUpdateBinding.closeButton.setOnClickListener(v1 -> {
                    dialog.dismiss();
                });
                dialogUpdateBinding.postButton.setOnClickListener(v -> {
                    dialog.dismiss();
                    pickupProcessMvpView.getBatchDetailsApiCall(salesLine, refNo, orderAdapterPos, position, omsHeader);
                });
                dialog.setCancelable(false);
                dialog.show();
//                pickupProcessMvpView.getBatchDetailsApiCall(salesLine, refNo, orderAdapterPos, position, omsHeader);
            }
//            Dialog statusUpdateDialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
//            dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update_status_p, null, false);
//            dialogUpdateStatusBinding.setCallback(NewSelectedOrderAdapter.this);
//            statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
//            statusUpdateDialog.setCancelable(false);
//            dialogUpdateStatusBinding.fullfillmentId.setText(refNo);
//            dialogUpdateStatusBinding.boxId.setText(salesLine.getRackId());
//            dialogUpdateStatusBinding.productName.setText(salesLine.getItemName());
//            pickupProcessMvpView.onClickStart(position);
//            dialogUpdateStatusBinding.dismissDialog.setOnClickListener(vie -> statusUpdateDialog.dismiss());
//            dialogUpdateStatusBinding.update.setOnClickListener(view1 -> {
//
//                if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()) {
//                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//                    if (mCallback != null)
//                        mCallback.onClickUpdate(orderAdapterPos,position,"FULL");
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setRotation(0);
//                    statusUpdateDialog.dismiss();
//                } else if (dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()) {
//
//                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
//                    if (mCallback != null)
//                        mCallback.onClickUpdate(orderAdapterPos,position, salesLine.getItemId());
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
//                    statusUpdateDialog.dismiss();
//                } else if (dialogUpdateStatusBinding.notAvailableRadio.isChecked()) {
//                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
//                    if (mCallback != null)
//                        mCallback.onClickUpdate(orderAdapterPos,position, salesLine.getItemId());
//                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//                    statusUpdateDialog.dismiss();
//                } else if (dialogUpdateStatusBinding.skipRadioBtn.isChecked()) {
//                    statusUpdateDialog.dismiss();
//                }
//            });
//            statusUpdateDialog.show();
        });


        /*if (salesLine.getGetBatchInfoRes() != null) {
            holder.pickupSummaryDetailsProductsBinding.headings.setVisibility(View.VISIBLE);
            SelectedBatchListAdapter selectedBatchListAdapter = new SelectedBatchListAdapter(context, salesLine.getGetBatchInfoRes().getBatchList(), salesLine);
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.pickupSummaryDetailsProductsBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.pickupSummaryDetailsProductsBinding.selectedbatchesRecycler.setAdapter(selectedBatchListAdapter);
        }*/
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    @Override
    public void onClickBatchDetails() {
        if (pickupProcessMvpView != null)
            pickupProcessMvpView.onClickBatchDetails(orderAdapterPos, position, adapterPosition);
//        startActivity(BatchListActivity.getStartIntent(context));
//        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

    }

    @Override
    public void onClickFullPicked() {
        checkAllFalse();
        dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
        dialogUpdateStatusBinding.fullDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickStausIcon() {

    }

    @Override
    public void onClickPartialPicked() {
        checkAllFalse();
        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
        dialogUpdateStatusBinding.partialDetails.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClickNotAvailable() {
        checkAllFalse();
        dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);


    }

    @Override
    public void onClickSkip() {
        checkAllFalse();
        dialogUpdateStatusBinding.skipRadioBtn.setChecked(true);

    }


    private void checkAllFalse() {
        dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
        dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
        dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
        dialogUpdateStatusBinding.skipRadioBtn.setChecked(false);

        dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
        dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding;

        public ViewHolder(@NonNull AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding) {
            super(pickupSummaryDetailsProductsBinding.getRoot());
            this.pickupSummaryDetailsProductsBinding = pickupSummaryDetailsProductsBinding;
        }
    }

}
