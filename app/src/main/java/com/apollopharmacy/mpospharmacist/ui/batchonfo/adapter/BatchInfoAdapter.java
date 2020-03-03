package com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BatchInfoAdapter extends RecyclerView.Adapter<BatchInfoAdapter.ViewHolder> {

    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList;

    private BatchInfoMvpView batchInfoMvpView;

    public BatchInfoAdapter(ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList, BatchInfoMvpView batchInfoMvpView) {
        this.arrBatchList = arrBatchList;
        this.batchInfoMvpView = batchInfoMvpView;
    }

    @NonNull
    @Override
    public BatchInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BatchInfoListAdapterBinding batchInfoListAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.batch_info_list_adapter, parent, false);
        return new BatchInfoAdapter.ViewHolder(batchInfoListAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchInfoAdapter.ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj item = arrBatchList.get(position);
        holder.batchInfoListAdapterBinding.setBatchInfo(item);

        holder.itemView.setOnClickListener(v -> {
            if (batchInfoMvpView != null) {
                if(!item.getNearByExpiry())
                batchInfoMvpView.onItemClick(position);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private BatchInfoListAdapterBinding batchInfoListAdapterBinding;

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
