package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter;

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
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBillerOrdersScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersMvpView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BillerFullfillmentAdapter extends RecyclerView.Adapter<BillerFullfillmentAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> fullfillmentList;
    private BillerOrdersMvpView mvpView;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> filteredList = new ArrayList<>();
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> omsHeaderList = new ArrayList<>();

    public BillerFullfillmentAdapter(Context context, List<OMSTransactionHeaderResModel.OMSHeaderObj> fullfillmentList, BillerOrdersMvpView mvpView) {
        this.context = context;
        this.omsHeaderList = fullfillmentList;
        this.fullfillmentList = fullfillmentList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_biller_orders_screen_p, parent, false);
        return new ViewHolder(adapterBillerOrdersScreenBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OMSTransactionHeaderResModel.OMSHeaderObj fullfilmentModel = fullfillmentList.get(position);
        holder.adapterBillerOrdersScreenBinding.fullfillmentID.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getREFNO());
        holder.adapterBillerOrdersScreenBinding.totalItems.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getNumberofItemLines());
        if (fullfilmentModel.getOverallOrderStatus() != null && fullfilmentModel.getOverallOrderStatus().length() > 2) {
            String boxId = fullfilmentModel.getOverallOrderStatus().substring(2);
            holder.adapterBillerOrdersScreenBinding.boxId.setText(boxId.substring(boxId.length() - 5));
        } else {
            holder.adapterBillerOrdersScreenBinding.boxId.setText("-");
        }
        if (fullfilmentModel.getOverallOrderStatus().startsWith("1")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("FULL");
        } else if (fullfilmentModel.getOverallOrderStatus().startsWith("2")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("PARTIAL");
        } else if (fullfilmentModel.getOverallOrderStatus().startsWith("3")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("NOT AVAILABLE");
        }
        if (fullfilmentModel.getOrderPickup()) {
            holder.adapterBillerOrdersScreenBinding.pickuporderstatus.setText("Completed");
        } else {
            holder.adapterBillerOrdersScreenBinding.pickuporderstatus.setText("Pending");
        }

        if (fullfilmentModel.getOrderPickup()) {
            holder.adapterBillerOrdersScreenBinding.packerorderstatus.setText("Completed");
        } else {
            holder.adapterBillerOrdersScreenBinding.packerorderstatus.setText("Pending");
        }

        if (fullfilmentModel.getOverallOrderStatus().substring(0, 1).equals("0")) {
            holder.adapterBillerOrdersScreenBinding.status.setVisibility(View.GONE);
            holder.adapterBillerOrdersScreenBinding.statusText.setVisibility(View.GONE);
            holder.adapterBillerOrdersScreenBinding.statusIcon.setVisibility(View.GONE);
        } else if (fullfilmentModel.getOverallOrderStatus().substring(0, 1).equals("1")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("Full");
            holder.adapterBillerOrdersScreenBinding.statusIcon.setRotation(0);
            holder.adapterBillerOrdersScreenBinding.statusText.setText("Full");
            holder.adapterBillerOrdersScreenBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
        } else if (fullfilmentModel.getOverallOrderStatus().substring(0, 1).equals("2")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("Partial");
            holder.adapterBillerOrdersScreenBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.partialcirculargreeenorange));
            holder.adapterBillerOrdersScreenBinding.statusText.setText("Partial");
        } else if (fullfilmentModel.getOverallOrderStatus().substring(0, 1).equals("3")) {
            holder.adapterBillerOrdersScreenBinding.status.setText("Not Available");
            holder.adapterBillerOrdersScreenBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.adapterBillerOrdersScreenBinding.statusText.setText("Not Available");
        }
        holder.itemView.setOnClickListener(view -> {
            if (mvpView != null)
                mvpView.onRightArrowClickedContinue(fullfilmentModel.getREFNO());
        });
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
        {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        fullfillmentList = omsHeaderList;
                    } else {
                        filteredList.clear();
                        for (OMSTransactionHeaderResModel.OMSHeaderObj row : omsHeaderList) {
                            if (!filteredList.contains(row) && (row.getREFNO().contains(charString))) {
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
                        fullfillmentList = (ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj>) filterResults.values;
                        try {
                            mvpView.noOrderFound(fullfillmentList.size());
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("FullfilmentAdapter", e.getMessage());
                        }
                    } else {
                        mvpView.noOrderFound(0);
                        notifyDataSetChanged();
                    }
                }
            };
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding;

        public ViewHolder(@NonNull AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding) {
            super(adapterBillerOrdersScreenBinding.getRoot());
            this.adapterBillerOrdersScreenBinding = adapterBillerOrdersScreenBinding;
        }
    }

    public static class FullfilmentModel implements Serializable {
        private String fullfilmentId;
        private String totalItems;
        private boolean isSelected;

        public String getFullfilmentId() {
            return fullfilmentId;
        }

        public void setFullfilmentId(String fullfilmentId) {
            this.fullfilmentId = fullfilmentId;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}

