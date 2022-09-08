package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterShippingLabelBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.ShippingLabelMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;

import java.util.ArrayList;
import java.util.List;

public class ShippingLabelAdapter extends RecyclerView.Adapter<ShippingLabelAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList;
    private ShippingLabelMvpView mvpView;

    //for search filter
    private List<GetJounalOnlineOrderTransactionsResponse> filteredGetJounalOnlineOrderTransactionsResponseList = new ArrayList<>();
    private List<GetJounalOnlineOrderTransactionsResponse> tempGetJounalOnlineOrderTransactionsResponseList = new ArrayList<>();

    public ShippingLabelAdapter(Context mContext, List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList, ShippingLabelMvpView mvpView) {
        this.mContext = mContext;
        this.getJounalOnlineOrderTransactionsResponseList = getJounalOnlineOrderTransactionsResponseList;
        this.tempGetJounalOnlineOrderTransactionsResponseList = getJounalOnlineOrderTransactionsResponseList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterShippingLabelBinding shippingLabelBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_shipping_label, parent, false);
        return new ShippingLabelAdapter.ViewHolder(shippingLabelBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse = getJounalOnlineOrderTransactionsResponseList.get(position);
        holder.shippingLabelBinding.setModel(getJounalOnlineOrderTransactionsResponse);

        holder.shippingLabelBinding.printLabel.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.onClickPrintLabel(getJounalOnlineOrderTransactionsResponse);
            }
        });
    }

    @Override
    public int getItemCount() {
        return getJounalOnlineOrderTransactionsResponseList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    getJounalOnlineOrderTransactionsResponseList = tempGetJounalOnlineOrderTransactionsResponseList;
                } else {
                    filteredGetJounalOnlineOrderTransactionsResponseList.clear();
                    for (GetJounalOnlineOrderTransactionsResponse row : tempGetJounalOnlineOrderTransactionsResponseList) {
                        if (!filteredGetJounalOnlineOrderTransactionsResponseList.contains(row) && (row.getRefno().contains(charString) || row.getReciptId().contains(charSequence))) {
                            filteredGetJounalOnlineOrderTransactionsResponseList.add(row);
                        }

                    }
                    getJounalOnlineOrderTransactionsResponseList = filteredGetJounalOnlineOrderTransactionsResponseList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = getJounalOnlineOrderTransactionsResponseList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (getJounalOnlineOrderTransactionsResponseList != null && !getJounalOnlineOrderTransactionsResponseList.isEmpty()) {
                    getJounalOnlineOrderTransactionsResponseList = (List<GetJounalOnlineOrderTransactionsResponse>) filterResults.values;
                    try {
                        mvpView.noShippinfLabelFound(getJounalOnlineOrderTransactionsResponseList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("ShippingLabelAdapter", e.getMessage());
                    }
                } else {
                    mvpView.noShippinfLabelFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterShippingLabelBinding shippingLabelBinding;

        public ViewHolder(@NonNull AdapterShippingLabelBinding shippingLabelBinding) {
            super(shippingLabelBinding.getRoot());
            this.shippingLabelBinding = shippingLabelBinding;
        }
    }
}
