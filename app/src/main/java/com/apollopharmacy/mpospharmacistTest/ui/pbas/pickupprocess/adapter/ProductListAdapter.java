package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterProductListPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private PickupProcessMvpView mvpView;

    public ProductListAdapter(Context context, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickupProcessMvpView mvpView) {
        this.context = context;
        this.salesLineList = salesLineList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterProductListPBinding productListBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_product_list_p, parent, false);
        return new ProductListAdapter.ViewHolder(productListBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        GetOMSTransactionResponse.SalesLine salesLine = salesLineList.get(position);
        holder.productListBinding.orderItemNo.setText(salesLine.getOrderItemNo());
        holder.productListBinding.productName.setText(salesLine.getItemName());
        holder.productListBinding.productMrp.setText(String.valueOf(salesLine.getMrp()));
        holder.productListBinding.productQty.setText(String.valueOf(salesLine.getQty()));
        holder.productListBinding.fullfillmentId.setText(salesLine.getFullfillmentId());

        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("PARTIAL")) {
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#70FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#70FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("FULL")) {
            holder.productListBinding.productStatus.setRotation(0);
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#70FFFF00"));
        } else {
            holder.productListBinding.itemLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        if (salesLine.getGetBatchInfoRes() != null) {
            SelectedBatchListAdapter selectedBatchListAdapter = new SelectedBatchListAdapter(context, salesLine.getGetBatchInfoRes().getBatchList());
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.productListBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.productListBinding.selectedbatchesRecycler.setAdapter(selectedBatchListAdapter);
            holder.productListBinding.headings.setVisibility(View.VISIBLE);
            holder.productListBinding.selectedbatchesRecycler.setVisibility(View.VISIBLE);
        }
        holder.productListBinding.start.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.onClickRackItemStart(salesLine);
            }
        });
        holder.productListBinding.productStatus.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.onClickRackItemStart(salesLine);
            }
        });
    }

    @Override
    public int getItemCount() {
        return salesLineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterProductListPBinding productListBinding;

        public ViewHolder(@NonNull AdapterProductListPBinding productListBinding) {
            super(productListBinding.getRoot());
            this.productListBinding = productListBinding;
        }
    }
}
