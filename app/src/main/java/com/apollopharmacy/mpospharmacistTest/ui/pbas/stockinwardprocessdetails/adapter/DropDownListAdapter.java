package com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterDropDownInwardBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.StockInwardProcessDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetUniversalDropDownBindResponse;

import java.util.List;

public class DropDownListAdapter extends RecyclerView.Adapter<DropDownListAdapter.ViewHolder>{
    List<GetUniversalDropDownBindResponse.DropDownLine> dropDownLineLists;
    StockInwardProcessDetailsMvpView stockInwardProcessDetailsMvpView;

    int positionItem;

    public DropDownListAdapter(List<GetUniversalDropDownBindResponse.DropDownLine> dropDownLineList, StockInwardProcessDetailsMvpView stockInwardProcessDetailsMvpView, int position) {
        this.dropDownLineLists=dropDownLineList;
        this.stockInwardProcessDetailsMvpView=stockInwardProcessDetailsMvpView;
        this.positionItem=position;

    }

    @NonNull
    @Override
    public DropDownListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterDropDownInwardBinding adapterDropDownInwardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.adapter_drop_down_inward, parent, false);
        return new ViewHolder(adapterDropDownInwardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DropDownListAdapter.ViewHolder holder, int position) {
        GetUniversalDropDownBindResponse.DropDownLine dropDownLineList = dropDownLineLists.get(position);
        holder.adapterDropDownInwardBindings.listItemInward.setText(dropDownLineList.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stockInwardProcessDetailsMvpView.onClickDropDownItem(dropDownLineList.getText(), positionItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dropDownLineLists.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        AdapterDropDownInwardBinding adapterDropDownInwardBindings;
        public ViewHolder(@NonNull AdapterDropDownInwardBinding adapterDropDownInwardBinding ) {
            super(adapterDropDownInwardBinding.getRoot());
            adapterDropDownInwardBindings=adapterDropDownInwardBinding;
        }
    }
}
