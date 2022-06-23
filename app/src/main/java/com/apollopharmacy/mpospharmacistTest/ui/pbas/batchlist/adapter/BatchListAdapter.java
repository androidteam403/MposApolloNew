package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogBatchAlertBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogPhysicalBatchIdBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListMvpView;

import java.util.ArrayList;
import java.util.List;

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<GetBatchInfoRes.BatchListObj> batchListModelListl = new ArrayList<>();
    private List<GetBatchInfoRes.BatchListObj> omsHeaderList = new ArrayList<>();
    private List<GetBatchInfoRes.BatchListObj> filteredList = new ArrayList<>();
    private BatchListMvpView batchListMvpView;
    boolean batchSelected = false;
    double reqqty;
    double Reservedqty;
    boolean alertcheck = true;

    public BatchListAdapter(Context mContext, List<GetBatchInfoRes.BatchListObj> batchListModelList1, BatchListMvpView mvpView) {
        this.mContext = mContext;
        this.batchListModelListl = batchListModelList1;
        this.omsHeaderList = batchListModelList1;
        this.batchListMvpView = mvpView;
        this.reqqty = reqqty;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBatchlistPBinding adapterBatchlistBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_batchlist_p, parent, false);
        return new BatchListAdapter.ViewHolder(adapterBatchlistBinding);
    }

    String requiredQty;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListModel = batchListModelListl.get(position);
        holder.adapterBatchlistBinding.batchno.setText(batchListModel.getBatchNo());
        holder.adapterBatchlistBinding.qohCoount.setText(batchListModel.getQ_O_H());
        holder.adapterBatchlistBinding.expiryDate.setText(batchListModel.getExpDate());
        holder.adapterBatchlistBinding.phisicalbatchEdit.setText(batchListModel.getPhysicalBatchID() != null && !batchListModel.getPhysicalBatchID().isEmpty() ? batchListModel.getPhysicalBatchID() : batchListModel.getBatchNo());
        holder.adapterBatchlistBinding.mrp.setText(String.valueOf(batchListModel.getMRP()));
        if (batchListModel.isSelected()) {
            holder.adapterBatchlistBinding.requiredQuantity.setEnabled(false);
        } else {
            holder.adapterBatchlistBinding.requiredQuantity.setEnabled(true);
        }

        holder.adapterBatchlistBinding.requiredQuantity.setText(String.valueOf(batchListModel.getREQQTY()).substring(0, String.valueOf(batchListModel.getREQQTY()).indexOf(".")));

        holder.adapterBatchlistBinding.batchPickupStatus.setOnClickListener(v -> {
            if (holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString() != null && !holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString().isEmpty()) {
                if (holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString().length() > 2) {
                    if (holder.adapterBatchlistBinding.requiredQuantity.getText().toString() != null && !holder.adapterBatchlistBinding.requiredQuantity.getText().toString().isEmpty()) {
                        if (Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()) == 0 || holder.adapterBatchlistBinding.requiredQuantity.getText().toString().isEmpty()) {
                            Dialog dialog = new Dialog(mContext, R.style.Theme_AppCompat_DayNight_NoActionBar);
                            DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_batch_alert, null, false);
                            dialog.setContentView(dialogBatchAlertBinding.getRoot());
                            dialogBatchAlertBinding.dialogMessage.setText("Please enter request qty");
                            dialog.setCancelable(false);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                            dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                            dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> dialog.dismiss());
                            dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
                        } else if (Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()) <= Double.parseDouble(batchListModel.getQ_O_H())) {
                            batchListModel.setSelected(true);
                            batchListModel.setBatchNo(batchListModelListl.get(position).getBatchNo());
                            batchListModel.setREQQTY(Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()));
                            Dialog physicalBatchIdDialog = new Dialog(mContext, R.style.Theme_AppCompat_DayNight_NoActionBar);
                            DialogPhysicalBatchIdBinding dialogPhysicalBatchIdBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_physical_batch_id, null, false);
                            physicalBatchIdDialog.setContentView(dialogPhysicalBatchIdBinding.getRoot());
                            physicalBatchIdDialog.setCancelable(false);
                            physicalBatchIdDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                            dialogPhysicalBatchIdBinding.dialogButtonNO.setOnClickListener(view -> physicalBatchIdDialog.dismiss());
                            dialogPhysicalBatchIdBinding.dialogButtonOK.setOnClickListener(v3 -> {
                                if (dialogPhysicalBatchIdBinding.physicalBatchCompareText.getText().toString() != null && !dialogPhysicalBatchIdBinding.physicalBatchCompareText.getText().toString().isEmpty() && dialogPhysicalBatchIdBinding.physicalBatchCompareText.getText().toString().length() > 2) {
                                    if (dialogPhysicalBatchIdBinding.physicalBatchCompareText.getText().toString().substring(dialogPhysicalBatchIdBinding.physicalBatchCompareText.getText().toString().length() - 3).equalsIgnoreCase(holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString().substring(holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString().length() - 3))) {
                                        if (batchListMvpView != null) {
                                            physicalBatchIdDialog.dismiss();
                                            batchListModel.setPhysicalBatchID(holder.adapterBatchlistBinding.phisicalbatchEdit.getText().toString());
                                            batchListMvpView.onClickSelectedBatch(batchListModel);
                                        } else {
                                            Toast.makeText(mContext, "Batch list mvp view is null", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        dialogPhysicalBatchIdBinding.physicalBatchCompareText.setError("Enter valid last 3 digits of Physical Batch");
                                        dialogPhysicalBatchIdBinding.physicalBatchCompareText.requestFocus();
                                    }
                                } else {
                                    dialogPhysicalBatchIdBinding.physicalBatchCompareText.setError("Enter last 3 digits of Physical Batch");
                                    dialogPhysicalBatchIdBinding.physicalBatchCompareText.requestFocus();
                                }
                            });
                            dialogPhysicalBatchIdBinding.dialogButtonNot.setOnClickListener(v3 -> physicalBatchIdDialog.dismiss());
                            physicalBatchIdDialog.show();

                        } else if (Double.parseDouble(holder.adapterBatchlistBinding.requiredQuantity.getText().toString()) > Double.parseDouble(batchListModel.getQ_O_H())) {
                            Dialog dialog = new Dialog(mContext, R.style.Theme_AppCompat_DayNight_NoActionBar);
                            DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_batch_alert, null, false);
                            dialog.setContentView(dialogBatchAlertBinding.getRoot());
                            dialogBatchAlertBinding.dialogMessage.setText("You have entered more than batch qty.");
                            dialog.setCancelable(false);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                            dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                            dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> dialog.dismiss());
                            dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
                        }
                    } else {
                        Dialog dialog = new Dialog(mContext, R.style.Theme_AppCompat_DayNight_NoActionBar);
                        DialogBatchAlertBinding dialogBatchAlertBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.dialog_batch_alert, null, false);
                        dialog.setContentView(dialogBatchAlertBinding.getRoot());
                        dialogBatchAlertBinding.dialogMessage.setText("Please enter request qty");
                        dialog.setCancelable(false);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                        dialogBatchAlertBinding.dialogButtonNO.setVisibility(View.GONE);
                        dialogBatchAlertBinding.dialogButtonOK.setOnClickListener(v1 -> dialog.dismiss());
                        dialogBatchAlertBinding.dialogButtonNot.setOnClickListener(v1 -> dialog.dismiss());
                    }


                } else {
                    holder.adapterBatchlistBinding.phisicalbatchEdit.setError("Physical batch id must be greater then 2 digits");
                    holder.adapterBatchlistBinding.phisicalbatchEdit.requestFocus();
                }
            } else {
                holder.adapterBatchlistBinding.phisicalbatchEdit.setError("Physical batch id should not be empty");
                holder.adapterBatchlistBinding.phisicalbatchEdit.requestFocus();
            }
        });

        holder.adapterBatchlistBinding.iconPointsAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                batchListModel.setSelected(false);
                batchListModel.setREQQTY(0);
                if (batchListMvpView != null) {
                    batchListMvpView.onClickSelectedBatch(batchListModel);
                }
            }
        });

        if (batchListModelListl.get(position).isSelected()) {
            holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.GONE);
            holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.VISIBLE);
            holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.Light_green);
        } else {
            holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.GONE);
            holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.VISIBLE);
            holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.white);
        }
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (batchListModelListl != null && batchListModelListl.size() > 0) {
            count = batchListModelListl.size();
        }
        return count;
    }


    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    batchListModelListl = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (GetBatchInfoRes.BatchListObj row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getBatchNo().toLowerCase().contains(charString.toLowerCase()))) {
                            filteredList.add(row);
                        }

                    }
                    batchListModelListl = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = batchListModelListl;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (batchListModelListl != null && !batchListModelListl.isEmpty()) {
                    batchListModelListl = (List<GetBatchInfoRes.BatchListObj>) filterResults.values;
                    try {
                        batchListMvpView.noOrderFound(batchListModelListl.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    batchListMvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBatchlistPBinding adapterBatchlistBinding;

        public ViewHolder(@NonNull AdapterBatchlistPBinding adapterBatchlistBinding) {
            super(adapterBatchlistBinding.getRoot());
            this.adapterBatchlistBinding = adapterBatchlistBinding;
        }
    }
}
