package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedBatchesBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;


public class SelectedBatchListAdapter extends RecyclerView.Adapter<SelectedBatchListAdapter.ViewHolder> {

    Context context;
    List<GetBatchInfoRes.BatchListObj> batchList;

    public SelectedBatchListAdapter(Context context, List<GetBatchInfoRes.BatchListObj> batchList) {
        this.context = context;
        this.batchList = batchList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedBatchesBinding adapterSelectedBatchesBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_selected_batches, parent, false);
        return new SelectedBatchListAdapter.ViewHolder(adapterSelectedBatchesBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListObj = batchList.get(position);
        holder.adapterSelectedBatchesBinding.batchno.setText(batchListObj.getBatchNo());
        holder.adapterSelectedBatchesBinding.expDate.setText(batchListObj.getExpDate());
        holder.adapterSelectedBatchesBinding.mrp.setText(String.valueOf(batchListObj.getMRP()));
        int qty= (int) batchListObj.getREQQTY();
        holder.adapterSelectedBatchesBinding.requiredQuantity.setText(String.valueOf(qty));
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterSelectedBatchesBinding adapterSelectedBatchesBinding;

        public ViewHolder(@NonNull AdapterSelectedBatchesBinding adapterSelectedBatchesBinding) {
            super(adapterSelectedBatchesBinding.getRoot());
            this.adapterSelectedBatchesBinding = adapterSelectedBatchesBinding;
        }
    }
}

