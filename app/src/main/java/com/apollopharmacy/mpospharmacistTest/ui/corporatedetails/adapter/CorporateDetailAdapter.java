package com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewCorporateItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.CorporateDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;

import java.util.ArrayList;

public class CorporateDetailAdapter extends RecyclerView.Adapter<CorporateDetailAdapter.ViewHolder> implements Filterable {
    private Activity activity;
    private ArrayList<CorporateModel.DropdownValueBean> corporateArrayList;
    private ArrayList<CorporateModel.DropdownValueBean> filteredCorporateArrayList;
    private CorporateDetailsMvpView corporateMvpView;

    public CorporateDetailAdapter(Activity activity, ArrayList<CorporateModel.DropdownValueBean> corporateArrayList) {
        this.activity = activity;
        this.corporateArrayList = corporateArrayList;
        filteredCorporateArrayList = corporateArrayList;
    }

    @NonNull
    @Override
    public CorporateDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewCorporateItemBinding corporateItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_corporate_item, parent, false);
        return new CorporateDetailAdapter.ViewHolder(corporateItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CorporateDetailAdapter.ViewHolder holder, int position) {
        CorporateModel.DropdownValueBean item = filteredCorporateArrayList.get(position);
        holder.viewCorporateItemBinding.setModel(item);
        holder.itemView.setOnClickListener(v -> {
            if (corporateMvpView != null) {
                corporateMvpView.onClickCorporateItem(item);
            }
        });
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredCorporateArrayList = corporateArrayList;
                } else {
                    ArrayList<CorporateModel.DropdownValueBean> filteredList = new ArrayList<>();
                    for (CorporateModel.DropdownValueBean row : corporateArrayList) {
                        if (row.getDescription().contains(charString.toUpperCase()) || row.getCode().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }
                    filteredCorporateArrayList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCorporateArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCorporateArrayList = (ArrayList<CorporateModel.DropdownValueBean>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewCorporateItemBinding viewCorporateItemBinding;

        public ViewHolder(@NonNull ViewCorporateItemBinding viewCorporateItemBinding) {
            super(viewCorporateItemBinding.getRoot());
            this.viewCorporateItemBinding = viewCorporateItemBinding;
        }
    }

    @Override
    public int getItemCount() {
        return filteredCorporateArrayList.size();
    }

    public void setClickListener(CorporateDetailsMvpView listener) {
        this.corporateMvpView = listener;
    }
}
