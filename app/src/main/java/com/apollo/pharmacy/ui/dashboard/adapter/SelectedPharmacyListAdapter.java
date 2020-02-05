package com.apollo.pharmacy.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.PharmacyItemBinding;
import com.apollo.pharmacy.ui.dashboard.fragments.sales.SalesFragment;
import com.apollo.pharmacy.ui.dashboard.model.PharmaPojo;

import java.util.ArrayList;

public class SelectedPharmacyListAdapter extends RecyclerView.Adapter<SelectedPharmacyListAdapter.MyViewHolder> {
    private SalesFragment context;
    private ArrayList<PharmaPojo> pharmacyList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private PharmacyItemBinding PharmacyItemBinding;

        public MyViewHolder(PharmacyItemBinding view) {
            super(view.getRoot());
            this.PharmacyItemBinding = view;
        }
    }

    public SelectedPharmacyListAdapter(SalesFragment context, ArrayList<PharmaPojo> pharmacyList) {
        this.context = context;
        this.pharmacyList = pharmacyList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PharmacyItemBinding itemView = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.pharmacy_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final PharmaPojo item = pharmacyList.get(position);
        holder.PharmacyItemBinding.setPharmamodel(item);
    }

    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }
}
