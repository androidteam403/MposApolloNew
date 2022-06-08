package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterRackRowPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RackWiseSortedData;

import java.util.List;

public class RackRowAdapter extends RecyclerView.Adapter<RackRowAdapter.ViewHolder> {
    private Context context;
    private List<RackWiseSortedData.BoxIdModel> boxIdModelList;

    public RackRowAdapter(Context context, List<RackWiseSortedData.BoxIdModel> boxIdModelList) {
        this.context = context;
        this.boxIdModelList = boxIdModelList;
    }

    @NonNull
    @Override
    public RackRowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterRackRowPBinding rackRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_rack_row_p, parent, false);
        return new RackRowAdapter.ViewHolder(rackRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull RackRowAdapter.ViewHolder holder, int position) {
        RackWiseSortedData.BoxIdModel boxIdModel = boxIdModelList.get(position);
        holder.rackRowBinding.orderItemNo.setText(boxIdModel.getOrderItemNo());
        if (boxIdModel.getBoxId() != null)
            if (boxIdModel.getBoxId().length() > 5)
                holder.rackRowBinding.rackBoxId.setText(boxIdModel.getBoxId().substring(boxIdModel.getBoxId().length() - 5));
            else
                holder.rackRowBinding.rackBoxId.setText(boxIdModel.getBoxId());
    }

    @Override
    public int getItemCount() {
        return boxIdModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterRackRowPBinding rackRowBinding;

        public ViewHolder(@NonNull AdapterRackRowPBinding rackRowBinding) {
            super(rackRowBinding.getRoot());
            this.rackRowBinding = rackRowBinding;
        }
    }
}
