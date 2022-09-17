package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold.adapter;

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
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterOnHoldBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.onhold.OnHoldMvpView;

import java.util.ArrayList;
import java.util.List;

public class OnHoldAdapter extends RecyclerView.Adapter<OnHoldAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList;
    private OnHoldMvpView onHoldMvpView;

    private List<TransactionHeaderResponse.OMSHeader> filteredOmsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();

    public OnHoldAdapter(Context mContext, List<TransactionHeaderResponse.OMSHeader> omsHeaderList, OnHoldMvpView onHoldMvpView) {
        this.mContext = mContext;
        this.omsHeaderList = omsHeaderList;
        this.onHoldMvpView = onHoldMvpView;
        this.filteredOmsHeaderList = omsHeaderList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterOnHoldBinding onHoldBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_on_hold, parent, false);
        return new OnHoldAdapter.ViewHolder(onHoldBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = filteredOmsHeaderList.get(position);
        holder.onHoldBinding.setModel(omsHeader);
        holder.onHoldBinding.unHold.setOnClickListener(view -> {
            if (onHoldMvpView != null) {
                onHoldMvpView.onClickUnHold(omsHeader);
            }
        });
    }


    @Override
    public int getItemCount() {
        int count = 0;
        if (filteredOmsHeaderList != null && filteredOmsHeaderList.size() > 0) {
            count = filteredOmsHeaderList.size();
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
                    filteredOmsHeaderList = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (TransactionHeaderResponse.OMSHeader row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getRefno().toLowerCase().contains(charString.toLowerCase()) || row.getOverallOrderStatus().toLowerCase().contains(charString.toLowerCase()))) {
                            filteredList.add(row);
                        }

                    }
                    filteredOmsHeaderList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredOmsHeaderList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filteredOmsHeaderList != null && !filteredOmsHeaderList.isEmpty()) {
                    filteredOmsHeaderList = (ArrayList<TransactionHeaderResponse.OMSHeader>) filterResults.values;
                    try {
                        onHoldMvpView.noOrderFound(filteredOmsHeaderList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    onHoldMvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterOnHoldBinding onHoldBinding;

        public ViewHolder(@NonNull AdapterOnHoldBinding onHoldBinding) {
            super(onHoldBinding.getRoot());
            this.onHoldBinding = onHoldBinding;
        }
    }
}
