package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders.adapter;

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
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOrdersBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders.OrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> implements Filterable {
    private Context mContext;
    private List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList;
    private OrdersMvpView mvpView;

    //for search filter
    private List<GetJounalOnlineOrderTransactionsResponse> filteredGetJounalOnlineOrderTransactionsResponseList = new ArrayList<>();
    private List<GetJounalOnlineOrderTransactionsResponse> tempGetJounalOnlineOrderTransactionsResponseList = new ArrayList<>();

    public OrdersAdapter(Context mContext, List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList, OrdersMvpView mvpView) {
        this.mContext = mContext;
        this.getJounalOnlineOrderTransactionsResponseList = getJounalOnlineOrderTransactionsResponseList;
        this.tempGetJounalOnlineOrderTransactionsResponseList = getJounalOnlineOrderTransactionsResponseList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOrdersBinding ordersBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_orders, parent, false);
        return new OrdersAdapter.ViewHolder(ordersBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse = getJounalOnlineOrderTransactionsResponseList.get(position);
        holder.adapterOrdersBinding.setModel(getJounalOnlineOrderTransactionsResponse);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mvpView != null) {
                    mvpView.onClickItem(getJounalOnlineOrderTransactionsResponse);
                }
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
                        if (!filteredGetJounalOnlineOrderTransactionsResponseList.contains(row) && (row.getTransactionId().toLowerCase().contains(charString.toLowerCase()) || row.getRefno().toLowerCase().contains(charString.toLowerCase()) || row.getBoxId().toLowerCase().contains(charString.toLowerCase()))) {
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
                        mvpView.noOrderFound(getJounalOnlineOrderTransactionsResponseList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("ShippingLabelAdapter", e.getMessage());
                    }
                } else {
                    mvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdapterOrdersBinding adapterOrdersBinding;

        public ViewHolder(@NonNull AdapterOrdersBinding adapterOrdersBinding) {
            super(adapterOrdersBinding.getRoot());
            this.adapterOrdersBinding = adapterOrdersBinding;
        }
    }
}
