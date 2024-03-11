package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterProductListPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.PickupProcessMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RackWiseSortedData;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private List<GetOMSTransactionResponse.SalesLine> salesLineList;
    private PickupProcessMvpView mvpView;
    List<RackWiseSortedData.BoxIdModel> boxIdList;

    public ProductListAdapter(Context context, List<GetOMSTransactionResponse.SalesLine> salesLineList, PickupProcessMvpView mvpView, List<RackWiseSortedData.BoxIdModel> boxIdList) {
        this.context = context;
        this.salesLineList = salesLineList;
        this.mvpView = mvpView;
        this.boxIdList = boxIdList;
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
        if (boxIdList.get(position).getBoxId() != null) {
            if (boxIdList.get(position).getBoxId().length() > 5)
                holder.productListBinding.orderItemNo.setText(boxIdList.get(position).getBoxId().substring(boxIdList.get(position).getBoxId().length() - 5));
            else
                holder.productListBinding.orderItemNo.setText(boxIdList.get(position).getBoxId());
        } else {
            holder.productListBinding.orderItemNo.setText("---");
        }
        holder.productListBinding.productName.setText(salesLine.getItemName());
        holder.productListBinding.productMrp.setText(String.valueOf(salesLine.getMrp()));
        holder.productListBinding.productQty.setText(String.valueOf(salesLine.getQty()));
        holder.productListBinding.fullfillmentId.setText(salesLine.getFullfillmentId());

        if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("PARTIAL")) {
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
//            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
//            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else if (salesLine.getStatus() != null && salesLine.getStatus().equalsIgnoreCase("FULL")) {
            holder.productListBinding.productStatus.setRotation(0);
            holder.productListBinding.productStatus.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.productListBinding.start.setVisibility(View.GONE);
            holder.productListBinding.productStatus.setVisibility(View.VISIBLE);
//            holder.productListBinding.itemLayout.setBackgroundColor(Color.parseColor("#33FFFF00"));
        } else {
            holder.productListBinding.itemLayout.setBackgroundColor(Color.TRANSPARENT);
        }
        if (salesLine.getGetBatchInfoRes() != null) {
            SelectedBatchListAdapter selectedBatchListAdapter = new SelectedBatchListAdapter(context, salesLine.getGetBatchInfoRes().getBatchList(), salesLine);
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.productListBinding.selectedbatchesRecycler.setLayoutManager(new LinearLayoutManager(context));
            holder.productListBinding.selectedbatchesRecycler.setAdapter(selectedBatchListAdapter);
            holder.productListBinding.headings.setVisibility(View.VISIBLE);
            holder.productListBinding.selectedbatchesRecycler.setVisibility(View.VISIBLE);
        }

        if (salesLine.isOrderItemNoSelected()) {
            holder.productListBinding.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.lite_green));
        }else {
            holder.productListBinding.itemLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        holder.productListBinding.start.setOnClickListener(view -> {
            if (!salesLine.isOnHold()) {
                if (mvpView != null) {
                    mvpView.onClickRackItemStart(salesLine);
                }
            } else {
                Toast.makeText(context, "The product is on hold.", Toast.LENGTH_SHORT).show();
            }
        });
        holder.productListBinding.productStatus.setOnClickListener(view -> {
            if (!salesLine.isOnHold()) {
                if (mvpView != null) {
                    mvpView.onClickRackItemStart(salesLine);
                }
            } else {
                Toast.makeText(context, "The product is on hold.", Toast.LENGTH_SHORT).show();
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
