package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedPickupProcessProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogUpdateStatusPBinding;
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
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private OrderAdapter orderAdapter;
private int orderAdapterPos;
    public NewSelectedOrderAdapter(Context context, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickupProcessMvpView pickupProcessMvpView, StatusUpdateCallback mCallback, int orderAdapterPos ) {
        this.context = context;
        this.salesLineList = salesLineList;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.mCallback = mCallback;
        this.orderAdapterPos = orderAdapterPos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_pickup_process_products_p, parent, false);
        return new NewSelectedOrderAdapter.ViewHolder(pickupSummaryDetailsProductsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewSelectedOrderAdapter.ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
//        RackAdapter.RackBoxModel.ProductData dataResponse = racksDataResponse.get(position);
//        pickupProcessMvpView.productsNextPosReturn(racksDataResponse);
        holder.pickupSummaryDetailsProductsBinding.productName.setText(salesLine.getItemName());
        holder.pickupSummaryDetailsProductsBinding.rackId.setText(salesLine.getRackId());
        holder.pickupSummaryDetailsProductsBinding.batchNo.setText("-");
        holder.pickupSummaryDetailsProductsBinding.stripMrp.setText(String.valueOf(salesLine.getPrice()));
        holder.pickupSummaryDetailsProductsBinding.quantity.setText(String.valueOf(salesLine.getQty()));
        holder.pickupSummaryDetailsProductsBinding.apolloMrp.setText("-");

        if(salesLine.getStatus()!=null &&salesLine.getStatus().equalsIgnoreCase("PARTIAL")){
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
        }else if(salesLine.getStatus()!=null && salesLine.getStatus().equalsIgnoreCase("NOT AVAILABLE")){
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
        }else if(salesLine.getStatus()!=null && salesLine.getStatus().equalsIgnoreCase("FULL")) {
            holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
        }

        holder.pickupSummaryDetailsProductsBinding.start.setOnClickListener(view -> {
            Dialog statusUpdateDialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
            dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update_status_p, null, false);
            dialogUpdateStatusBinding.setCallback(NewSelectedOrderAdapter.this);
            statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
            statusUpdateDialog.setCancelable(false);
            dialogUpdateStatusBinding.dismissDialog.setOnClickListener(vie -> statusUpdateDialog.dismiss());
            dialogUpdateStatusBinding.update.setOnClickListener(view1 -> {
                if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()) {
                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                    if (mCallback != null)
                        mCallback.onClickUpdate(orderAdapterPos,position, "FULL");
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setRotation(0);
                    statusUpdateDialog.dismiss();
                } else if (dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()) {
                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
                    if (mCallback != null)
                        mCallback.onClickUpdate(orderAdapterPos,position, "PARTIAL");
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
                    statusUpdateDialog.dismiss();
                } else if (dialogUpdateStatusBinding.notAvailableRadio.isChecked()) {
                    holder.pickupSummaryDetailsProductsBinding.start.setVisibility(View.GONE);
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setVisibility(View.VISIBLE);
                    if (mCallback != null)
                        mCallback.onClickUpdate(orderAdapterPos,position, "NOT AVAILABLE");
                    holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                    statusUpdateDialog.dismiss();
                } else if (dialogUpdateStatusBinding.skipRadioBtn.isChecked()) {
                    statusUpdateDialog.dismiss();
                }
            });
            statusUpdateDialog.show();
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickupProcessMvpView.onClickSalesLine(position, salesLine.getStatus());
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    @Override
    public void onClickBatchDetails() {
        if (pickupProcessMvpView != null) {
            pickupProcessMvpView.onClickBatchDetails();
        }
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
