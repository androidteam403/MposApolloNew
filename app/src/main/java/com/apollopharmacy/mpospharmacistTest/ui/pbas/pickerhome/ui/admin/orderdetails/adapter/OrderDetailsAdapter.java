package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.orderdetails.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrderDetailsLayoutBinding;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private Context context;
    private String status;

    public OrderDetailsAdapter(Context context, String status) {
        this.context = context;
        this.status = status;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrderDetailsLayoutBinding orderDetailsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_order_details_layout, parent, false);
        return new ViewHolder(orderDetailsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 0) {
            holder.orderDetailsLayoutBinding.topStrip.setVisibility(View.GONE);
            holder.orderDetailsLayoutBinding.statusPending.setVisibility(View.VISIBLE);
            holder.orderDetailsLayoutBinding.statusDone.setVisibility(View.GONE);
        } else if (position == 1) {
            holder.orderDetailsLayoutBinding.statusDone.setVisibility(View.VISIBLE);
            holder.orderDetailsLayoutBinding.statusPending.setVisibility(View.GONE);
        }

        if (status.equalsIgnoreCase("inprogress")) {
            holder.orderDetailsLayoutBinding.requestOrderCountLayout.setVisibility(View.GONE);
            holder.orderDetailsLayoutBinding.inprogressOrderCountLayout.setVisibility(View.VISIBLE);
            holder.orderDetailsLayoutBinding.reasonLayout.setVisibility(View.GONE);
            if (position == 0) {
                holder.orderDetailsLayoutBinding.inprogressCount.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
                holder.orderDetailsLayoutBinding.inprogressSlash.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
                holder.orderDetailsLayoutBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.partialcirculargreeenorange));
            }else if (position == 1) {
                holder.orderDetailsLayoutBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_background_green));
                holder.orderDetailsLayoutBinding.inprogressCount.setText("5");
                holder.orderDetailsLayoutBinding.inprogressCount.setTextColor(Color.parseColor("#30ab72"));
                holder.orderDetailsLayoutBinding.inprogressSlash.setTextColor(Color.parseColor("#30ab72"));
            }
        } else if (status.equalsIgnoreCase("request")) {
            holder.orderDetailsLayoutBinding.requestOrderCountLayout.setVisibility(View.VISIBLE);
            holder.orderDetailsLayoutBinding.inprogressOrderCountLayout.setVisibility(View.GONE);
            holder.orderDetailsLayoutBinding.reasonLayout.setVisibility(View.VISIBLE);
            if (position == 0) {
                holder.orderDetailsLayoutBinding.statusDone.setVisibility(View.VISIBLE);
                holder.orderDetailsLayoutBinding.statusPending.setVisibility(View.GONE);
                holder.orderDetailsLayoutBinding.reasonLayout.setVisibility(View.GONE);
                holder.orderDetailsLayoutBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rounded_background_green));
            } else {
                holder.orderDetailsLayoutBinding.statusDone.setVisibility(View.GONE);
                holder.orderDetailsLayoutBinding.statusPending.setVisibility(View.VISIBLE);
                holder.orderDetailsLayoutBinding.reasonLayout.setVisibility(View.VISIBLE);
                holder.orderDetailsLayoutBinding.statusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.partialcirculargreeenorange));
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOrderDetailsLayoutBinding orderDetailsLayoutBinding;

        public ViewHolder(@NonNull AdapterOrderDetailsLayoutBinding orderDetailsLayoutBinding) {
            super(orderDetailsLayoutBinding.getRoot());
            this.orderDetailsLayoutBinding = orderDetailsLayoutBinding;
        }
    }
}
