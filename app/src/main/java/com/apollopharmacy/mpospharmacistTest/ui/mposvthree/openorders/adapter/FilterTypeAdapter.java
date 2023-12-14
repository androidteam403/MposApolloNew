package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFilterTypeV3Binding;

import java.util.ArrayList;

public class FilterTypeAdapter extends RecyclerView.Adapter<FilterTypeAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> filterTypes;

    public FilterTypeAdapter(Context context, ArrayList<String> filterTypes) {
        this.context = context;
        this.filterTypes = filterTypes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFilterTypeV3Binding filterTypeV3Binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.adapter_filter_type_v3,
                parent,
                false
        );
        return new ViewHolder(filterTypeV3Binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.filterTypeV3Binding.name.setText(filterTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return filterTypes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFilterTypeV3Binding filterTypeV3Binding;

        public ViewHolder(@NonNull AdapterFilterTypeV3Binding filterTypeV3Binding) {
            super(filterTypeV3Binding.getRoot());
            this.filterTypeV3Binding = filterTypeV3Binding;
        }
    }
}
