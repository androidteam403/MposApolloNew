package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPackerBatchDetailsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.List;

public class PackerBatchDetailsAdapter extends RecyclerView.Adapter<PackerBatchDetailsAdapter.ViewHolder> {
    private Context mContext;
    private List<GetBatchInfoRes.BatchListObj> selectedBatchList;

    public PackerBatchDetailsAdapter(Context mContext, List<GetBatchInfoRes.BatchListObj> selectedBatchList) {
        this.mContext = mContext;
        this.selectedBatchList = selectedBatchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPackerBatchDetailsBinding packerBatchDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_packer_batch_details, parent, false);
        return new PackerBatchDetailsAdapter.ViewHolder(packerBatchDetailsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.packerBatchDetailsBinding.setModel(selectedBatchList.get(position));
    }

    @Override
    public int getItemCount() {
        return selectedBatchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPackerBatchDetailsBinding packerBatchDetailsBinding;

        public ViewHolder(@NonNull AdapterPackerBatchDetailsBinding packerBatchDetailsBinding) {
            super(packerBatchDetailsBinding.getRoot());
            this.packerBatchDetailsBinding = packerBatchDetailsBinding;
        }
    }
}
