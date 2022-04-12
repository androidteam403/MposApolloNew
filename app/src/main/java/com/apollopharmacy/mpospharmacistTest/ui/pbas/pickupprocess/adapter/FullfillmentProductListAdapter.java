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
//        if (isRackFlow)
//            holder.productListBinding.rackIdLayout.setVisibility(View.GONE);
//        else
//            holder.productListBinding.rackIdLayout.setVisibility(View.VISIBLE);
//
//        for (int k = 0; k < listOfList.size(); k++) {
//            for (int l = 0; l < listOfList.get(k).size(); l++) {
//                if (productListModel.getProductId().equalsIgnoreCase(listOfList.get(k).get(l).getProductId())) {
//                    productListModel.setFinalStatusUpdate(listOfList.get(k).get(l).isFinalStatusUpdate());
//                }
//            }
//        }
//
//        if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("Full")) {
//            statusHandlings(holder.productListBinding);
//            holder.productListBinding.fullStatusColor.setVisibility(View.VISIBLE);
//        } else if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("Partial")) {
//            statusHandlings(holder.productListBinding);
//            holder.productListBinding.partialStatusColor.setVisibility(View.VISIBLE);
//        } else if (productListModel.getStatus() != null && productListModel.getStatus().equalsIgnoreCase("NotAvailable")) {
//            statusHandlings(holder.productListBinding);
//            holder.productListBinding.notAvailableStatusColor.setVisibility(View.VISIBLE);
//        }
//
//        if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//            holder.productListBinding.capturesQty.setText(productListModel.getCapturedQuantity().toString() + "/");
//        }
//        if (productListModel.getBoxId() != null) {
//            holder.productListBinding.rackBoxLay.setVisibility(View.GONE);
//            holder.productListBinding.view.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = 0.625f;
//            params.setMarginStart(15);
//            holder.productListBinding.proLay.setLayoutParams(params);
//            //            holder.productListBinding.rackRowId.setText(productListModel.getBoxId());
//        } else {
//            holder.productListBinding.rackBoxLay.setVisibility(View.GONE);
//            holder.productListBinding.view.setVisibility(View.VISIBLE);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    0, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.weight = 0.625f;
//            params.setMarginStart(15);
//            holder.productListBinding.proLay.setLayoutParams(params);
//        }
//        holder.productListBinding.productName.setText(productListModel.getProductName());
//        holder.productListBinding.availableQty.setText(productListModel.getAvailableQuantity());
//        holder.productListBinding.rackId.setText(productListModel.getRackId());
//        holder.productListBinding.statusIcon.setOnClickListener(v -> {
//            if (pickupProcessMvpView != null) {
//                Dialog dialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
//                DialogUpdateStatusBinding updateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
//                        R.layout.dialog_update_status, null, false);
//                fullQty = false;
//                partialQty = false;
//                notAvailableQty = false;
//
//                if (Integer.parseInt(productListModel.getAvailableQuantity()) >= Integer.parseInt(productListModel.getRequiredQuantity())) {
//                    updateStatusBinding.qtyEditFull.setText(productListModel.getRequiredQuantity());
//                    updateStatusBinding.qtyEditPartial.setText(productListModel.getRequiredQuantity());
//                } else {
//                    updateStatusBinding.qtyEditFull.setText(productListModel.getAvailableQuantity());
//                    updateStatusBinding.qtyEditPartial.setText(productListModel.getAvailableQuantity());
//                }
//
//
//                dialog.setContentView(updateStatusBinding.getRoot());
//                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//                updateStatusBinding.id.setText(fullfillmentId);
//                updateStatusBinding.boxId.setText(productListModel.getBoxId());
//                updateStatusBinding.productName.setText(productListModel.getProductName());
//
//                radioButtonListener(updateStatusBinding, productListModel, position, productListModel, updateStatusBinding);
//                updateStatusBinding.dismissDialog.setOnClickListener(v1 -> {
//                    fullQty = false;
//                    partialQty = false;
//                    notAvailableQty = false;
//                    dialog.dismiss();
//                    productListModel.setProductStatusFillingUpdate(false);
//                    productListModel.setFinalStatusUpdate(false);
//                });
//                updateStatusBinding.update.setOnClickListener(v12 -> {
//                    if (fullQty) {
//                        productListModelList.get(position).setCapturedQuantity(updateStatusBinding.qtyEditFull.getText().toString());
//                        productListModel.setStatus("Full");
//                        productListModel.setCapturedQuantity(updateStatusBinding.qtyEditFull.getText().toString());
//                        holder.productListBinding.capturesQty.setText(updateStatusBinding.qtyEditFull.getText().toString() + "/");
//                        statusHandlings(holder.productListBinding);
//                        holder.productListBinding.fullStatusColor.setVisibility(View.VISIBLE);
//                        productListModel.setProductStatusFillingUpdate(true);
//                        productListModel.setFinalStatusUpdate(true);
//                    } else if (partialQty) {
//                        productListModelList.get(position).setCapturedQuantity(updateStatusBinding.qtyEditPartial.getText().toString());
//                        productListModel.setStatus("Partial");
//                        productListModel.setCapturedQuantity(updateStatusBinding.qtyEditPartial.getText().toString());
//                        holder.productListBinding.capturesQty.setText(updateStatusBinding.qtyEditPartial.getText().toString() + "/");
//                        statusHandlings(holder.productListBinding);
//                        holder.productListBinding.partialStatusColor.setVisibility(View.VISIBLE);
//                        productListModel.setProductStatusFillingUpdate(true);
//                        productListModel.setFinalStatusUpdate(true);
//                    } else if (notAvailableQty) {
//                        statusHandlings(holder.productListBinding);
//                        productListModel.setStatus("NotAvailable");
//                        holder.productListBinding.notAvailableStatusColor.setVisibility(View.VISIBLE);
//                        productListModel.setProductStatusFillingUpdate(true);
//                        productListModel.setFinalStatusUpdate(true);
//                    }
//                    dialog.dismiss();
//                    listOfList.add(productListModelList);
//                    pickupProcessMvpView.onRackStatusUpdate(listOfList);
//
//                });
//                dialog.show();
//            }
//        });
//        holder.productListBinding.fullStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fullQty = true;
//                partialQty = false;
//                notAvailableQty = false;
//                editViewHandllings(productListModel, position, holder.productListBinding, fullQty, partialQty, notAvailableQty);
//            }
//        });
//
//        holder.productListBinding.partialStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fullQty = false;
//                partialQty = true;
//                notAvailableQty = false;
//                editViewHandllings(productListModel, position, holder.productListBinding, fullQty, partialQty, notAvailableQty);
//            }
//        });
//
//        holder.productListBinding.notAvailableStatusColor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fullQty = false;
//                partialQty = false;
//                notAvailableQty = true;
//                editViewHandllings(productListModel, position, holder.productListBinding, fullQty, partialQty, notAvailableQty);
//            }
//        });
//    }
//
//    private void editViewHandllings(RackAdapter.RackBoxModel.ProductData productListModel, int position, AdapterProductListBinding productListBinding, boolean fullQty, boolean partialQty, boolean notAvailableQty) {
//        if (pickupProcessMvpView != null) {
//            Dialog dialog = new Dialog(context, R.style.fadeinandoutcustomDialog);
//            DialogUpdateStatusBinding updateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
//                    R.layout.dialog_update_status, null, false);
//            this.fullQty = fullQty;
//            this.partialQty = partialQty;
//            this.notAvailableQty = notAvailableQty;
//
//            if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//                if (fullQty) {
//                    updateStatusBinding.qtyEditFull.setText(productListModel.getCapturedQuantity());
//                } else if (partialQty) {
//                    updateStatusBinding.qtyEditPartial.setText(productListModel.getCapturedQuantity());
//                }
//            } else {
//                if (Integer.parseInt(productListModel.getAvailableQuantity()) >= Integer.parseInt(productListModel.getRequiredQuantity())) {
//                    updateStatusBinding.qtyEditFull.setText(productListModel.getRequiredQuantity());
//                    updateStatusBinding.qtyEditPartial.setText(productListModel.getRequiredQuantity());
//                } else {
//                    updateStatusBinding.qtyEditFull.setText(productListModel.getAvailableQuantity());
//                    updateStatusBinding.qtyEditPartial.setText(productListModel.getAvailableQuantity());
//                }
//            }
//
//            dialog.setContentView(updateStatusBinding.getRoot());
//            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//            updateStatusBinding.id.setText(fullfillmentId);
//            updateStatusBinding.boxId.setText(productListModel.getBoxId());
//            updateStatusBinding.productName.setText(productListModel.getProductName());
//
//            radioButtonListener(updateStatusBinding, productListModel, position, productListModel, updateStatusBinding);
//            updateStatusBinding.dismissDialog.setOnClickListener(v1 -> {
//                this.fullQty = fullQty;
//                this.partialQty = partialQty;
//                this.notAvailableQty = notAvailableQty;
//                dialog.dismiss();
//                productListModel.setFinalStatusUpdate(false);
//            });
//            updateStatusBinding.update.setOnClickListener(v12 -> {
//                if (this.fullQty) {
//                    productListModelList.get(position).setCapturedQuantity(updateStatusBinding.qtyEditFull.getText().toString());
//                    productListModel.setStatus("Full");
//                    productListModel.setCapturedQuantity(updateStatusBinding.qtyEditFull.getText().toString());
//                    productListBinding.capturesQty.setText(updateStatusBinding.qtyEditFull.getText().toString() + "/");
//                    statusHandlings(productListBinding);
//                    productListBinding.fullStatusColor.setVisibility(View.VISIBLE);
//                    productListModel.setFinalStatusUpdate(true);
//                } else if (this.partialQty) {
//                    productListModelList.get(position).setCapturedQuantity(updateStatusBinding.qtyEditPartial.getText().toString());
//                    productListModel.setStatus("Partial");
//                    productListModel.setCapturedQuantity(updateStatusBinding.qtyEditPartial.getText().toString());
//                    productListBinding.capturesQty.setText(updateStatusBinding.qtyEditPartial.getText().toString() + "/");
//                    statusHandlings(productListBinding);
//                    productListBinding.partialStatusColor.setVisibility(View.VISIBLE);
//                    productListModel.setFinalStatusUpdate(true);
//                } else if (this.notAvailableQty) {
//                    statusHandlings(productListBinding);
//                    productListModel.setStatus("NotAvailable");
//                    productListModel.setCapturedQuantity("");
//                    productListBinding.capturesQty.setText("");
//                    productListBinding.notAvailableStatusColor.setVisibility(View.VISIBLE);
//                    productListModel.setFinalStatusUpdate(true);
//                }
//                dialog.dismiss();
//                listOfList.add(productListModelList);
//                pickupProcessMvpView.onRackStatusUpdate(listOfList);
//
//            });
//            dialog.show();
//        }
    }

//    private void statusHandlings(AdapterProductListBinding productListBinding) {
//        productListBinding.statusIcon.setVisibility(View.GONE);
//        productListBinding.fullStatusColor.setVisibility(View.GONE);
//        productListBinding.partialStatusColor.setVisibility(View.GONE);
//        productListBinding.notAvailableStatusColor.setVisibility(View.GONE);
//    }
//
//    private boolean fullQty, partialQty, notAvailableQty;
//
//    private void radioButtonListener(DialogUpdateStatusBinding dialogUpdateStatusBinding, RackAdapter.RackBoxModel.ProductData productListQty, int position, RackAdapter.RackBoxModel.ProductData productListModel, DialogUpdateStatusBinding updateStatusBinding) {
//        dialogUpdateStatusBinding.availableProFull.setText(productListQty.getAvailableQuantity());
//        dialogUpdateStatusBinding.requiredProFull.setText(productListQty.getRequiredQuantity());
//
//        dialogUpdateStatusBinding.availableProPartial.setText(productListQty.getAvailableQuantity());
//        dialogUpdateStatusBinding.requiredProPartial.setText(productListQty.getRequiredQuantity());
//
//
//        if (productListQty.getStatus() != null) {
//            if (productListQty.getStatus().equalsIgnoreCase("Full")) {
//                dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
//                dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                dialogUpdateStatusBinding.fullDetails.setVisibility(View.VISIBLE);
//                dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//            } else if (productListQty.getStatus().equalsIgnoreCase("Partial")) {
//                dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
//                dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                dialogUpdateStatusBinding.partialDetails.setVisibility(View.VISIBLE);
//            } else if (productListQty.getStatus().equalsIgnoreCase("NotAvailable")) {
//                dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
//                dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//            }
//        }
//
//
//        dialogUpdateStatusBinding.fullPickedRadioOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.fullPickedRadio.isChecked()) {
//                    fullQty = true;
//                    partialQty = false;
//                    notAvailableQty = false;
//
//                    if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//                        if (fullQty) {
//                            updateStatusBinding.qtyEditFull.setText(productListModel.getCapturedQuantity());
//                        } else if (partialQty) {
//                            updateStatusBinding.qtyEditPartial.setText(productListModel.getCapturedQuantity());
//                        }
//                    }
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.VISIBLE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.fullPickedRadio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.fullPickedRadio.isChecked()) {
//                    fullQty = true;
//                    partialQty = false;
//                    notAvailableQty = false;
//
//                    if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//                        if (fullQty) {
//                            updateStatusBinding.qtyEditFull.setText(productListModel.getCapturedQuantity());
//                        } else if (partialQty) {
//                            updateStatusBinding.qtyEditPartial.setText(productListModel.getCapturedQuantity());
//                        }
//                    }
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(true);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.VISIBLE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.partiallyPickedRadioTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()) {
//                    fullQty = false;
//                    partialQty = true;
//                    notAvailableQty = false;
//
//                    if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//                        if (fullQty) {
//                            updateStatusBinding.qtyEditFull.setText(productListModel.getCapturedQuantity());
//                        } else if (partialQty) {
//                            updateStatusBinding.qtyEditPartial.setText(productListModel.getCapturedQuantity());
//                        }
//                    }
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.partiallyPickedRadio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.partiallyPickedRadio.isChecked()) {
//                    fullQty = false;
//                    partialQty = true;
//                    notAvailableQty = false;
//
//                    if (productListModel.getCapturedQuantity() != null && !productListModel.getCapturedQuantity().isEmpty() && !productListModel.getCapturedQuantity().equalsIgnoreCase("")) {
//                        if (fullQty) {
//                            updateStatusBinding.qtyEditFull.setText(productListModel.getCapturedQuantity());
//                        } else if (partialQty) {
//                            updateStatusBinding.qtyEditPartial.setText(productListModel.getCapturedQuantity());
//                        }
//                    }
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(true);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(false);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.notAvailableRadioThree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!dialogUpdateStatusBinding.notAvailableRadio.isChecked()) {
//                    fullQty = false;
//                    partialQty = false;
//                    notAvailableQty = true;
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.notAvailableRadio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogUpdateStatusBinding.notAvailableRadio.isChecked()) {
//                    fullQty = false;
//                    partialQty = false;
//                    notAvailableQty = true;
//                    dialogUpdateStatusBinding.fullPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.partiallyPickedRadio.setChecked(false);
//                    dialogUpdateStatusBinding.notAvailableRadio.setChecked(true);
//                    dialogUpdateStatusBinding.fullDetails.setVisibility(View.GONE);
//                    dialogUpdateStatusBinding.partialDetails.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        dialogUpdateStatusBinding.qtyDecreamentFull.setOnClickListener(v -> {
//            if (Integer.parseInt(dialogUpdateStatusBinding.qtyEditFull.getText().toString().trim()) > 1) {
//                dialogUpdateStatusBinding.qtyEditFull.setText(String.valueOf(Integer.parseInt(dialogUpdateStatusBinding.qtyEditFull.getText().toString().trim()) - 1));
//            }
//        });
//        dialogUpdateStatusBinding.qtyIncreamentFull.setOnClickListener(v -> {
//            dialogUpdateStatusBinding.qtyEditFull.setText(String.valueOf(Integer.parseInt(dialogUpdateStatusBinding.qtyEditFull.getText().toString().trim()) + 1));
//        });
//
//        dialogUpdateStatusBinding.qtyDecreamentPartial.setOnClickListener(v -> {
//            if (Integer.parseInt(dialogUpdateStatusBinding.qtyEditPartial.getText().toString().trim()) > 1) {
//                dialogUpdateStatusBinding.qtyEditPartial.setText(String.valueOf(Integer.parseInt(dialogUpdateStatusBinding.qtyEditPartial.getText().toString().trim()) - 1));
//            }
//        });
//        dialogUpdateStatusBinding.qtyIncreamentPartial.setOnClickListener(v -> {
//            dialogUpdateStatusBinding.qtyEditPartial.setText(String.valueOf(Integer.parseInt(dialogUpdateStatusBinding.qtyEditPartial.getText().toString().trim()) + 1));
//        });
//    }

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
