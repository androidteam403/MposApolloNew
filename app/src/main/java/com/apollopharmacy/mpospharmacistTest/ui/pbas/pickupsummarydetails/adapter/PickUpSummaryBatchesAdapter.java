package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupsummarydetails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedBatchesBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedBatchesDetailsActivityBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.SelectedBatchListAdapter;

import java.util.List;

public class PickUpSummaryBatchesAdapter extends RecyclerView.Adapter<PickUpSummaryBatchesAdapter.ViewHolder> {
    Context context;
    List<GetBatchInfoRes.BatchListObj> batchList;

    public PickUpSummaryBatchesAdapter(Context context, List<GetBatchInfoRes.BatchListObj> batchList) {
        this.context=context;
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public PickUpSummaryBatchesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedBatchesDetailsActivityBinding adapterSelectedBatchesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_batches_details_activity, parent, false);
        return new PickUpSummaryBatchesAdapter.ViewHolder(adapterSelectedBatchesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PickUpSummaryBatchesAdapter.ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListObj = batchList.get(position);

        holder.adapterSelectedBatchesBinding.batchno.setText(batchListObj.getBatchNo());
        holder.adapterSelectedBatchesBinding.expDate.setText(batchListObj.getExpDate());
        holder.adapterSelectedBatchesBinding.mrp.setText(String.valueOf(batchListObj.getMRP()));
        holder.adapterSelectedBatchesBinding.qohCoount.setText(batchListObj.getQ_O_H());
        holder.adapterSelectedBatchesBinding.requiredQuantity.setText(String.valueOf(batchListObj.getREQQTY()));
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        public  AdapterSelectedBatchesDetailsActivityBinding adapterSelectedBatchesBinding;

        public ViewHolder(@NonNull AdapterSelectedBatchesDetailsActivityBinding adapterSelectedBatchesBinding) {
            super(adapterSelectedBatchesBinding.getRoot());
            this.adapterSelectedBatchesBinding = adapterSelectedBatchesBinding;
        }
    }
}
