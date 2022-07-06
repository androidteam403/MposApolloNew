package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickedUpordersPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import java.util.ArrayList;
import java.util.List;

public class PickedUpOrdersAdapter extends RecyclerView.Adapter<PickedUpOrdersAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<TransactionHeaderResponse.OMSHeader> fullfillmentList = new ArrayList<>();
    private PickedUpOrdersMvpView pickupProcessMvpView;
    private List<TransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();


    public PickedUpOrdersAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> fullfillmentList, PickedUpOrdersMvpView pickupProcessMvpView) {
        this.context = context;
        this.fullfillmentList = fullfillmentList;
        this.omsHeaderList = fullfillmentList;
        this.pickupProcessMvpView = pickupProcessMvpView;
    }

    @NonNull
    @Override
    public PickedUpOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPickedUpordersPBinding orderBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_picked_uporders_p, parent, false);
        return new PickedUpOrdersAdapter.ViewHolder(orderBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull PickedUpOrdersAdapter.ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = fullfillmentList.get(position);
        holder.orderBinding.fullfillmentID.setText(omsHeader.getRefno());
        holder.orderBinding.totalItems.setText(String.valueOf(omsHeader.getNumberofItemLines()));

        if (omsHeader.getOverallOrderStatus() != null && omsHeader.getOverallOrderStatus().length() > 2) {
            String boxId = omsHeader.getOverallOrderStatus().substring(2);
            holder.orderBinding.boxId.setText(boxId.substring(boxId.length() - 5));
        } else {
            holder.orderBinding.boxId.setText("-");
        }
        holder.itemView.setOnClickListener(v -> {
            pickupProcessMvpView.onItmClick(position, omsHeader);
        });
        if (omsHeader.getOverallOrderStatus().startsWith("1")) {
            holder.orderBinding.orderOverallStatus.setText("FULL");
        } else if (omsHeader.getOverallOrderStatus().startsWith("2")) {
            holder.orderBinding.orderOverallStatus.setText("PARTIAL");
        } else if (omsHeader.getOverallOrderStatus().startsWith("3")) {
            holder.orderBinding.orderOverallStatus.setText("NOT AVAILABLE");
        }
        if (omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
            holder.orderBinding.status.setText("Partial");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);
        } else if (omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.orderBinding.status.setText("Not Available");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);
        } else if (omsHeader.getStockStatus() != null && omsHeader.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
            holder.orderBinding.statusIcon.setRotation(0);
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.orderBinding.status.setText("Full");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (fullfillmentList != null && fullfillmentList.size() > 0) {
            count = fullfillmentList.size();
        }
        return count;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    fullfillmentList = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (TransactionHeaderResponse.OMSHeader row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getRefno().contains(charString))) {
                            filteredList.add(row);
                        }

                    }
                    fullfillmentList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = fullfillmentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (fullfillmentList != null && !fullfillmentList.isEmpty()) {
                    fullfillmentList = (ArrayList<TransactionHeaderResponse.OMSHeader>) filterResults.values;
                    try {
                        pickupProcessMvpView.noOrderFound(fullfillmentList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    pickupProcessMvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPickedUpordersPBinding orderBinding;

        public ViewHolder(@NonNull AdapterPickedUpordersPBinding orderBinding) {
            super(orderBinding.getRoot());
            this.orderBinding = orderBinding;
        }
    }

}