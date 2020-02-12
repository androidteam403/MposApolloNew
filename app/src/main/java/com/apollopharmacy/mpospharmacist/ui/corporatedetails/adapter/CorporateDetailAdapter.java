package com.apollopharmacy.mpospharmacist.ui.corporatedetails.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ViewCorporateItemBinding;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.CorporateDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;

import java.util.ArrayList;

public class CorporateDetailAdapter extends RecyclerView.Adapter<CorporateDetailAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<CorporateModel.DropdownValueBean> corporateModelArrayList;
    private CorporateDetailsMvpView corporateMvpView;

    public CorporateDetailAdapter(Activity activity, ArrayList<CorporateModel.DropdownValueBean> corporateModelArrayList) {
        this.activity = activity;
        this.corporateModelArrayList = corporateModelArrayList;
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
        CorporateModel.DropdownValueBean item = corporateModelArrayList.get(position);
        holder.viewCorporateItemBinding.setModel(item);
        holder.itemView.setOnClickListener(v -> {
            if(corporateMvpView != null){
                corporateMvpView.onClickCorporateItem(item);
            }
        });
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
        return corporateModelArrayList.size();
    }

    public void setClickListener(CorporateDetailsMvpView listener){
        this.corporateMvpView = listener;
    }
}
