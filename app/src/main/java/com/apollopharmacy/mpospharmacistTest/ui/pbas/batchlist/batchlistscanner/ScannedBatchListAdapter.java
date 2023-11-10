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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScannedBatchListAdapter extends RecyclerView.Adapter<ScannedBatchListAdapter.ViewHolder> {
    private Context context;
    private List<GetBatchInfoRes.BatchListObj> batchList;
    private BatchlistScannerMvpView mvpView;

    public ScannedBatchListAdapter(Context context, List<GetBatchInfoRes.BatchListObj> batchList, BatchlistScannerMvpView mvpView) {
        this.context = context;
        this.batchList = batchList;
        this.mvpView = mvpView;
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
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy");
        Date inputDate = null;
        try {
            inputDate = inputFormat.parse(item.getExpDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.adapterBatchlistBinding.expiryDate.setText(outputFormat.format(inputDate));
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
