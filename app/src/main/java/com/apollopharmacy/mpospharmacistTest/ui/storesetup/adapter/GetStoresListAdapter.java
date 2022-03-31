package com.apollopharmacy.mpospharmacistTest.ui.storesetup.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewDoctorSearchItemBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewStoreSearchItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;

import java.util.ArrayList;

public class GetStoresListAdapter extends RecyclerView.Adapter<GetStoresListAdapter.ViewHolder> implements Filterable {
    private Activity activity;
    private ArrayList<StoreListResponseModel.StoreListObj> storeArrayList;
    private ArrayList<StoreListResponseModel.StoreListObj> storeFilteredArrayList;
    private GetStoresDialogMvpView storesDialogMvpView;

    public GetStoresListAdapter(Activity activity, ArrayList<StoreListResponseModel.StoreListObj> storeArrayList) {
        this.activity = activity;
        this.storeArrayList = storeArrayList;
        this.storeFilteredArrayList = storeArrayList;
    }

    @NonNull
    @Override
    public GetStoresListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewStoreSearchItemBinding doctorSearchItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_store_search_item, parent, false);
        return new GetStoresListAdapter.ViewHolder(doctorSearchItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull GetStoresListAdapter.ViewHolder holder, int position) {
        StoreListResponseModel.StoreListObj item = storeFilteredArrayList.get(position);
        holder.viewStoreSearchItemBinding.setModel(item);
        holder.itemView.setOnClickListener(v -> {
            if (storesDialogMvpView != null) {
                storesDialogMvpView.onClickListener(item);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewStoreSearchItemBinding viewStoreSearchItemBinding;

        public ViewHolder(@NonNull ViewStoreSearchItemBinding viewStoreSearchItemBinding) {
            super(viewStoreSearchItemBinding.getRoot());
            this.viewStoreSearchItemBinding = viewStoreSearchItemBinding;
        }
    }

    @Override
    public int getItemCount() {
        return storeFilteredArrayList.size();
    }

    public void onClickListener(GetStoresDialogMvpView mvpView) {
        this.storesDialogMvpView = mvpView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    storeFilteredArrayList = storeArrayList;
                } else {
                    ArrayList<StoreListResponseModel.StoreListObj> filteredList = new ArrayList<>();
                    for (StoreListResponseModel.StoreListObj row : storeArrayList) {
                        if (row.getStoreId().contains(charString.toUpperCase()) || row.getStoreName().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }
                    storeFilteredArrayList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = storeFilteredArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                storeFilteredArrayList = (ArrayList<StoreListResponseModel.StoreListObj>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
