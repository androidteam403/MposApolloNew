package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnbillprint.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderReturnBillPrintPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;

import java.util.ArrayList;

public class OrderReturnBillPrintAdapter extends RecyclerView.Adapter<OrderReturnBillPrintAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<SalesLineEntity> orderReturnModelArrayList;

    public OrderReturnBillPrintAdapter(Activity activity, ArrayList<SalesLineEntity> orderReturnModelArrayList) {
        this.activity = activity;
        this.orderReturnModelArrayList = orderReturnModelArrayList;
    }

    @NonNull
    @Override
    public OrderReturnBillPrintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderReturnBillPrintPBinding adapterOrderReturnBillPrintPBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_order_return_bill_print_p, parent, false);
        return new OrderReturnBillPrintAdapter.ViewHolder(adapterOrderReturnBillPrintPBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderReturnBillPrintAdapter.ViewHolder holder, int position) {
        SalesLineEntity item = orderReturnModelArrayList.get(position);
        holder.adapterOrderReturnBillPrintPBinding.setModel(item);
        if (item.getCategoryCode().equalsIgnoreCase("P")) {
            holder.adapterOrderReturnBillPrintPBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_pharma));
        } else if (item.getCategoryCode().equalsIgnoreCase("F")) {
            holder.adapterOrderReturnBillPrintPBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_fmcg));
        } else if (item.getCategoryCode().equalsIgnoreCase("A")) {
            holder.adapterOrderReturnBillPrintPBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_h_b));
        }
        holder.adapterOrderReturnBillPrintPBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                orderReturnModelArrayList.get(position).setChecked(b);
                if (orderReturnModelArrayList.get(position).isChecked()) {
                    holder.adapterOrderReturnBillPrintPBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.adapterOrderReturnBillPrintPBinding.mrp.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.adapterOrderReturnBillPrintPBinding.qty.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.adapterOrderReturnBillPrintPBinding.tax.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                } else {
                    holder.adapterOrderReturnBillPrintPBinding.itemLayout.setBackgroundColor(Color.WHITE);
                    holder.adapterOrderReturnBillPrintPBinding.mrp.setBackgroundColor(ContextCompat.getColor(activity, R.color.storesetupbackgroundcolor));
                    holder.adapterOrderReturnBillPrintPBinding.qty.setBackgroundColor(ContextCompat.getColor(activity, R.color.storesetupbackgroundcolor));
                    holder.adapterOrderReturnBillPrintPBinding.tax.setBackgroundColor(ContextCompat.getColor(activity, R.color.light_grey));
                    holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.rounded_corners_white));
                }
            }
        });
        holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setSelection(holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.getText().toString().length());
        holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable)) {
                    if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                        editable.delete(0, 1);
                    }
                    if (item.getRemainingQty() >= Double.parseDouble(editable.toString())) {
                        if (item.getQty() >= Double.parseDouble(editable.toString())) {
                            orderReturnModelArrayList.get(position).setReturnQty(Double.parseDouble(editable.toString()));
                        } else {
                            ExitInfoDialog dialogView = new ExitInfoDialog(activity);
                            dialogView.setTitle("Alert");
                            dialogView.setPositiveLabel("OK");
                            dialogView.setDialogDismiss();
                            dialogView.setSubtitle("Return Quantity must be smaller or equal to Ordered Quantity");
                            dialogView.setPositiveListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogView.dismiss();
                                    isDisplayDialog = false;
                                    holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setText("0");
                                }
                            });
                            if (!isDisplayDialog) {
                                isDisplayDialog = true;
                                dialogView.show();
                            }
                        }
                    } else {
                        ExitInfoDialog dialogView = new ExitInfoDialog(activity);
                        dialogView.setTitle("Alert");
                        dialogView.setPositiveLabel("OK");
                        dialogView.setDialogDismiss();
                        dialogView.setSubtitle("Quantity should be greater than 0 !");
                        dialogView.setPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogView.dismiss();
                                isDisplayDialog = false;
                                holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setText("0");
                            }
                        });
                        if (!isDisplayDialog) {
                            isDisplayDialog = true;
                            dialogView.show();
                        }
                    }
                    holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.setSelection(holder.adapterOrderReturnBillPrintPBinding.returnQuntitiyEdit.getText().toString().length());
                }
            }
        });
    }

    private boolean isDisplayDialog = false;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterOrderReturnBillPrintPBinding adapterOrderReturnBillPrintPBinding;

        public ViewHolder(@NonNull AdapterOrderReturnBillPrintPBinding adapterOrderReturnBillPrintPBinding) {
            super(adapterOrderReturnBillPrintPBinding.getRoot());
            this.adapterOrderReturnBillPrintPBinding = adapterOrderReturnBillPrintPBinding;
        }
    }

    @Override
    public int getItemCount() {
        return orderReturnModelArrayList.size();
    }
}
