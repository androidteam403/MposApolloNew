package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterRackRowPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RackWiseSortedData;

import java.util.List;

public class RackRowAdapter extends RecyclerView.Adapter<RackRowAdapter.ViewHolder> {
    private Context context;
    private List<RackWiseSortedData.BoxIdModel> boxIdModelList;

    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private ProductListAdapter productListAdapter;

    public RackRowAdapter(Context context, List<RackWiseSortedData.BoxIdModel> boxIdModelList, List<GetOMSTransactionResponse.SalesLine> salesLineList, ProductListAdapter productListAdapter) {
        this.context = context;
        this.boxIdModelList = boxIdModelList;
        this.salesLineList = salesLineList;
        this.productListAdapter = productListAdapter;
    }

    @NonNull
    @Override
    public RackRowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRackRowPBinding rackRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_rack_row_p, parent, false);
        return new RackRowAdapter.ViewHolder(rackRowBinding);
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull RackRowAdapter.ViewHolder holder, int position) {
        RackWiseSortedData.BoxIdModel boxIdModel = boxIdModelList.get(position);
        holder.rackRowBinding.orderItemNo.setText(boxIdModel.getOrderItemNo());
        if (boxIdModel.getBoxId() != null) {
            if (boxIdModel.getBoxId().length() > 5)
                holder.rackRowBinding.rackBoxId.setText(boxIdModel.getBoxId().substring(boxIdModel.getBoxId().length() - 5));
            else
                holder.rackRowBinding.rackBoxId.setText(boxIdModel.getBoxId());
        } else {
            holder.rackRowBinding.rackBoxId.setText("---");
        }

        if (boxIdModel.isSelected()) {
            holder.rackRowBinding.parentLayout.setBackgroundResource(R.drawable.rack_row_bg_selected);
//            holder.rackRowBinding.orderItemNo.setBackgroundResource(R.drawable.rack_row_order_iten_bg_selected);
//            holder.rackRowBinding.orderItemNo.setTextColor(Color.parseColor("#00a99e"));
        } else {
            holder.rackRowBinding.parentLayout.setBackgroundResource(R.drawable.rack_row_bg);
//            holder.rackRowBinding.orderItemNo.setBackgroundResource(R.drawable.rack_row_order_item_bg);
//            holder.rackRowBinding.orderItemNo.setTextColor(context.getResources().getColor(R.color.white));

        }
        holder.itemView.setOnClickListener(v -> {
            for (RackWiseSortedData.BoxIdModel bm : boxIdModelList) {
                if (bm.getOrderItemNo().equals(boxIdModel.getOrderItemNo())) {
                    bm.setSelected(!boxIdModel.isSelected());
                } else {
                    bm.setSelected(false);
                }
            }

            for (GetOMSTransactionResponse.SalesLine salesLine : salesLineList) {
                if (boxIdModel.getOrderItemNo().equals(salesLine.getOrderItemNo())) {
                    salesLine.setOrderItemNoSelected(boxIdModel.isSelected());
                } else {
                    salesLine.setOrderItemNoSelected(false);
                }
            }
            notifyDataSetChanged();
            if (productListAdapter != null) {
                productListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return boxIdModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRackRowPBinding rackRowBinding;

        public ViewHolder(@NonNull AdapterRackRowPBinding rackRowBinding) {
            super(rackRowBinding.getRoot());
            this.rackRowBinding = rackRowBinding;
        }
    }
}
