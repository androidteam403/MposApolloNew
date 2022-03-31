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
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;

import java.util.List;

public class NewSelectedOrderAdapter extends RecyclerView.Adapter<NewSelectedOrderAdapter.ViewHolder> implements NewSelectedOrderAdapterCallback {
    private PickupProcessMvpView pickupProcessMvpView;
    private boolean isRackFlow;
    String fullfillmentId;
    int fullFillmentPos;

    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    public List<RackAdapter.RackBoxModel.ProductData> racksDataResponse;
    public Context context;
    private DialogUpdateStatusPBinding dialogUpdateStatusBinding;

    public NewSelectedOrderAdapter(Context context, List<RackAdapter.RackBoxModel.ProductData> racksDataResponse, PickupProcessMvpView pickupProcessMvpView, boolean isRackFlow, List<List<RackAdapter.RackBoxModel.ProductData>> listOfList, String fullfillmentId) {
        this.context = context;
        this.racksDataResponse = racksDataResponse;
        this.pickupProcessMvpView = pickupProcessMvpView;
        this.isRackFlow = isRackFlow;
        this.listOfList = listOfList;
        this.fullfillmentId = fullfillmentId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedPickupProcessProductsPBinding pickupSummaryDetailsProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_pickup_process_products_p, parent, false);
        return new NewSelectedOrderAdapter.ViewHolder(pickupSummaryDetailsProductsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewSelectedOrderAdapter.ViewHolder holder, int position) {
        RackAdapter.RackBoxModel.ProductData dataResponse = racksDataResponse.get(position);
        pickupProcessMvpView.productsNextPosReturn(racksDataResponse);
        holder.pickupSummaryDetailsProductsBinding.productName.setText(dataResponse.getProductName());
        holder.pickupSummaryDetailsProductsBinding.rackId.setText(dataResponse.getRackId());
        holder.pickupSummaryDetailsProductsBinding.batchNo.setText(dataResponse.getBatchId());

        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dialog statusUpdateDialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
                dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_update_status_p, null, false);
                dialogUpdateStatusBinding.setCallback(NewSelectedOrderAdapter.this);
                statusUpdateDialog.setContentView(dialogUpdateStatusBinding.getRoot());
                statusUpdateDialog.setCancelable(false);
                dialogUpdateStatusBinding.dismissDialog.setOnClickListener(vie -> statusUpdateDialog.dismiss());
                dialogUpdateStatusBinding.update.setOnClickListener(view1 -> {
                    if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()) {
                        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                        statusUpdateDialog.dismiss();
                    } else if (dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()) {
                        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
                        statusUpdateDialog.dismiss();
                    } else if (dialogUpdateStatusBinding.notAvailableRadio.isChecked()) {
                        holder.pickupSummaryDetailsProductsBinding.statusUpdateIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
                        statusUpdateDialog.dismiss();
                    } else if (dialogUpdateStatusBinding.skipRadioBtn.isChecked()) {
                        statusUpdateDialog.dismiss();
                    }
                });
                statusUpdateDialog.show();


//                pickupProcessMvpView.onClickStausIcon();
            }
        });


    }

    @Override
    public int getItemCount() {
        return racksDataResponse.size();
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
