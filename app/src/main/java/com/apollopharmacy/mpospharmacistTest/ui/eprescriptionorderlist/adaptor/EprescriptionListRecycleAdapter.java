package com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewEprescriptionorderlistItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.EprescriptionOrderListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptionorderlist.model.OMSTransactionHeaderResModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EprescriptionListRecycleAdapter extends RecyclerView.Adapter<EprescriptionListRecycleAdapter.ItemBaseViewHolder> implements Filterable
{
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> mOrderArrList;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> mFilteredOrderArrList;
    private EprescriptionOrderListMvpView ePrescriptionListMvpView;
    private Context mContext;

    public EprescriptionListRecycleAdapter(Context context, ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> orderInfoArrList, EprescriptionOrderListMvpView ePrescriptionListMvpView) {
        this.mOrderArrList = orderInfoArrList;
        this.mContext = context;
        this.ePrescriptionListMvpView = ePrescriptionListMvpView;
        this.mFilteredOrderArrList = orderInfoArrList;
    }

    @NotNull
    @Override
    public EprescriptionListRecycleAdapter.ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewEprescriptionorderlistItemBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_eprescriptionorderlist_item, parent, false);
        return new ItemBaseViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final EprescriptionListRecycleAdapter.ItemBaseViewHolder holder, int position) {
        OMSTransactionHeaderResModel.OMSHeaderObj item = mFilteredOrderArrList.get(position);
        holder.listItemMainBinding.setModel(item);

        holder.itemView.setOnClickListener(v -> {
            if (ePrescriptionListMvpView != null) {
                ePrescriptionListMvpView.onOrderItemClick(position, item);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mFilteredOrderArrList.size();
    }

    static class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        public ViewEprescriptionorderlistItemBinding listItemMainBinding;

        public ItemBaseViewHolder(ViewEprescriptionorderlistItemBinding item) {
            super(item.getRoot());
            this.listItemMainBinding = item;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredOrderArrList = mOrderArrList;
                } else {
                    ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> filteredList = new ArrayList<>();
                    for (OMSTransactionHeaderResModel.OMSHeaderObj row : mOrderArrList) {
                        if (!filteredList.contains(row) && (row.getREFNO().contains(charString) || row.getVendorId().contains(charString) || row.getReciptId().contains(charString))) {
                            filteredList.add(row);
                        }
                    }
                    mFilteredOrderArrList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredOrderArrList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredOrderArrList = (ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
