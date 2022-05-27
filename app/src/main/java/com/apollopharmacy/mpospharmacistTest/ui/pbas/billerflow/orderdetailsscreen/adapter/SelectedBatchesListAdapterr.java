package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedBatchesBillerrBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public class SelectedBatchesListAdapterr extends RecyclerView.Adapter<SelectedBatchesListAdapterr.ViewHolder> {
    Context context;
    List<GetBatchInfoRes.BatchListObj> selectedBatchList;
    List<SalesLineEntity> products;

    public SelectedBatchesListAdapterr(Context context, List<GetBatchInfoRes.BatchListObj> selectedBatchList, List<SalesLineEntity> products) {
        this.context = context;
        this.selectedBatchList = selectedBatchList;
        this.products = products;
    }

    @NonNull
    @Override
    public SelectedBatchesListAdapterr.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedBatchesBillerrBinding adapterSelectedBatchesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_batches_billerr, parent, false);
        return new SelectedBatchesListAdapterr.ViewHolder(adapterSelectedBatchesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedBatchesListAdapterr.ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListObj = selectedBatchList.get(position);

        holder.adapterSelectedBatchesBinding.batchno.setText(batchListObj.getBatchNo());
        holder.adapterSelectedBatchesBinding.expDate.setText(batchListObj.getExpDate());
        holder.adapterSelectedBatchesBinding.mrp.setText(String.valueOf(batchListObj.getMRP()));
        holder.adapterSelectedBatchesBinding.requiredQuantity.setText(String.valueOf(Math.round(batchListObj.getREQQTY())));


    }

    @Override
    public int getItemCount() {
        return selectedBatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterSelectedBatchesBillerrBinding adapterSelectedBatchesBinding;

        public ViewHolder(@NonNull AdapterSelectedBatchesBillerrBinding adapterSelectedBatchesBinding) {
            super(adapterSelectedBatchesBinding.getRoot());
            this.adapterSelectedBatchesBinding = adapterSelectedBatchesBinding;
        }
    }
}
