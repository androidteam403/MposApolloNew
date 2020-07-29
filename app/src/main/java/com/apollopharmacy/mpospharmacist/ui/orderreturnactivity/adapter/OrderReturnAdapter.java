package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.adapter;

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

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.OrderReturnAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.ExitInfoDialog;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacist.ui.orderreturnactivity.OrderReturnActivity;

import java.util.ArrayList;

public class OrderReturnAdapter extends RecyclerView.Adapter<OrderReturnAdapter.ViewHolder> {
    private Activity activity;
    OrderReturnAdapterBinding orderReturnAdapterBinding;
    private ArrayList<SalesLineEntity> orderReturnModelArrayList;
    private OrderReturnActivity orderReturnActivity;

    public OrderReturnAdapter(Activity activity, ArrayList<SalesLineEntity> orderReturnModelArrayList) {
        this.activity = activity;
        this.orderReturnModelArrayList = orderReturnModelArrayList;
    }

    @NonNull
    @Override
    public OrderReturnAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderReturnAdapterBinding orderReturnAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.order_return_adapter, parent, false);
        return new OrderReturnAdapter.ViewHolder(orderReturnAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderReturnAdapter.ViewHolder holder, int position) {
        SalesLineEntity item = orderReturnModelArrayList.get(position);
        holder.orderReturnAdapterBinding.setModel(item);
        if (item.getCategoryCode().equalsIgnoreCase("P")) {
            holder.orderReturnAdapterBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_pharma));
        } else if (item.getCategoryCode().equalsIgnoreCase("F")) {
            holder.orderReturnAdapterBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_fmcg));
        } else if (item.getCategoryCode().equalsIgnoreCase("A")) {
            holder.orderReturnAdapterBinding.itemIcon.setImageDrawable(activity.getDrawable(R.drawable.ic_h_b));
        }
        holder.orderReturnAdapterBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                orderReturnModelArrayList.get(position).setChecked(b);
                if (orderReturnModelArrayList.get(position).isChecked()) {
                    holder.orderReturnAdapterBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.orderReturnAdapterBinding.mrp.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.orderReturnAdapterBinding.qty.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.orderReturnAdapterBinding.tax.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                    holder.orderReturnAdapterBinding.returnQuntitiyEdit.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
                } else {
                    holder.orderReturnAdapterBinding.itemLayout.setBackgroundColor(Color.WHITE);
                    holder.orderReturnAdapterBinding.mrp.setBackgroundColor(ContextCompat.getColor(activity, R.color.storesetupbackgroundcolor));
                    holder.orderReturnAdapterBinding.qty.setBackgroundColor(ContextCompat.getColor(activity, R.color.storesetupbackgroundcolor));
                    holder.orderReturnAdapterBinding.tax.setBackgroundColor(ContextCompat.getColor(activity, R.color.light_grey));
                    holder.orderReturnAdapterBinding.returnQuntitiyEdit.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.rounded_corners_white));
                }
            }
        });
        holder.orderReturnAdapterBinding.returnQuntitiyEdit.setSelection(holder.orderReturnAdapterBinding.returnQuntitiyEdit.getText().toString().length());
        holder.orderReturnAdapterBinding.returnQuntitiyEdit.addTextChangedListener(new TextWatcher() {
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
                                holder.orderReturnAdapterBinding.returnQuntitiyEdit.setText("0");
                            }
                        });
                        if (!isDisplayDialog) {
                            isDisplayDialog = true;
                            dialogView.show();
                        }
                    }
                    holder.orderReturnAdapterBinding.returnQuntitiyEdit.setSelection(holder.orderReturnAdapterBinding.returnQuntitiyEdit.getText().toString().length());
                }
            }
        });
    }

    private boolean isDisplayDialog = false;

    private void displayDialog() {

    }

//    public void itemColorChange(){
//        orderReturnAdapterBinding.itemLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
//        orderReturnAdapterBinding.mrp.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
//        orderReturnAdapterBinding.qty.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
//        orderReturnAdapterBinding.tax.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
//        orderReturnAdapterBinding.returnQuntitiyEdit.setBackgroundColor(ContextCompat.getColor(activity, R.color.red));
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public OrderReturnAdapterBinding orderReturnAdapterBinding;

        public ViewHolder(@NonNull OrderReturnAdapterBinding orderReturnAdapterBinding) {
            super(orderReturnAdapterBinding.getRoot());
            this.orderReturnAdapterBinding = orderReturnAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return orderReturnModelArrayList.size();
    }
}
