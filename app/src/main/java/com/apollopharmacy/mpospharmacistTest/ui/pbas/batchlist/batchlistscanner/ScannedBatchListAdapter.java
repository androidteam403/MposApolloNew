package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.batchlistscanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBatchlistBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.ArrayList;
import java.util.List;

public class ScannedBatchListAdapter extends RecyclerView.Adapter<ScannedBatchListAdapter.ViewHolder> {
    private Context context;
    private List<GetBatchInfoRes.BatchListObj> batchList;

    public ScannedBatchListAdapter(Context context, List<GetBatchInfoRes.BatchListObj> batchList) {
        this.context = context;
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBatchlistBinding adapterBatchlistBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_batchlist, parent, false);
        return new ViewHolder(adapterBatchlistBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj item = batchList.get(position);
        holder.adapterBatchlistBinding.batchNo.setText(item.getBatchNo());
        holder.adapterBatchlistBinding.expiryDate.setText(item.getExpDate());
        holder.adapterBatchlistBinding.mrp.setText(String.valueOf(item.getMRP()));
        holder.adapterBatchlistBinding.qoh.setText(item.getQ_O_H());
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBatchlistBinding adapterBatchlistBinding;

        public ViewHolder(@NonNull AdapterBatchlistBinding adapterBatchlistBinding) {
            super(adapterBatchlistBinding.getRoot());
            this.adapterBatchlistBinding = adapterBatchlistBinding;
        }
    }

    public void filter(ArrayList<GetBatchInfoRes.BatchListObj> batchList) {
        this.batchList = batchList;
        notifyDataSetChanged();
    }
}
