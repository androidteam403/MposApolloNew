package com.apollo.pharmacy.ui.searchpharmacy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ViewPharmacySearchItemBinding;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpPresenter;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpView;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;

import java.util.ArrayList;

public class SearchPharmacyListAdapter extends RecyclerView.Adapter<SearchPharmacyListAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Pharmacy.StoreListObj> pharmacyList;
    private SearchPharmacyMvpPresenter<SearchPharmacyMvpView> mPresenter;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ViewPharmacySearchItemBinding pharmacySearchItemBinding;

        public MyViewHolder(ViewPharmacySearchItemBinding view) {
            super(view.getRoot());
            this.pharmacySearchItemBinding = view;
        }
    }

    public SearchPharmacyListAdapter(Context context, ArrayList<Pharmacy.StoreListObj> pharmacyList, SearchPharmacyMvpPresenter<SearchPharmacyMvpView> mPresenter) {
        this.context = context;
        this.pharmacyList = pharmacyList;
        this.mPresenter = mPresenter;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewPharmacySearchItemBinding itemView = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.view_pharmacy_search_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Pharmacy.StoreListObj item = pharmacyList.get(position);
        holder.pharmacySearchItemBinding.setViewModel(item);
        holder.pharmacySearchItemBinding.setCallback(mPresenter);
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }
}
