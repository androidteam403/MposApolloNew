package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFilterTypeBinding;

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
        AdapterFilterTypeBinding filterTypeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_filter_type, parent, false);
        return new ViewHolder(filterTypeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.filterTypeBinding.name.setText(filterTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return filterTypes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFilterTypeBinding filterTypeBinding;

        public ViewHolder(@NonNull AdapterFilterTypeBinding filterTypeBinding) {
            super(filterTypeBinding.getRoot());
            this.filterTypeBinding = filterTypeBinding;
        }
    }
}
