package com.apollopharmacy.mpospharmacistTest.ui.pbas.forwardtopacker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterItemLayoutBinding;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context context;

    public ItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterItemLayoutBinding adapterItemLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_item_layout, parent, false);
        return new ViewHolder(adapterItemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterItemLayoutBinding adapterItemLayoutBinding;

        public ViewHolder(@NonNull AdapterItemLayoutBinding adapterItemLayoutBinding) {
            super(adapterItemLayoutBinding.getRoot());
            this.adapterItemLayoutBinding = adapterItemLayoutBinding;
        }
    }
}
