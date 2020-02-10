package com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.BatchInfoAdapterPojo;

import java.util.ArrayList;

public class BatchInfoAdapter extends RecyclerView.Adapter<BatchInfoAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<BatchInfoAdapterPojo> arrBatchList;

    BatchInfoListAdapterBinding batchInfoListAdapterBinding;

    public BatchInfoAdapter(Activity activity, ArrayList<BatchInfoAdapterPojo> arrBatchList) {
        this.activity = activity;
        this.arrBatchList = arrBatchList;
    }

    @NonNull
    @Override
    public BatchInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        batchInfoListAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.batch_info_list_adapter, parent, false);
        return new BatchInfoAdapter.ViewHolder(batchInfoListAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchInfoAdapter.ViewHolder holder, int position) {
        BatchInfoAdapterPojo item = arrBatchList.get(position);
        holder.batchInfoListAdapterBinding.setBatchInfo(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public BatchInfoListAdapterBinding batchInfoListAdapterBinding;

        public ViewHolder(@NonNull BatchInfoListAdapterBinding batchInfoListAdapterBinding) {
            super(batchInfoListAdapterBinding.getRoot());
            this.batchInfoListAdapterBinding = batchInfoListAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return arrBatchList.size();
    }
}
