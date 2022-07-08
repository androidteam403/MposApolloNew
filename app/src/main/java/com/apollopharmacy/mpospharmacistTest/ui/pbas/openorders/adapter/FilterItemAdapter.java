package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFilterItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.FilterModel;

import java.util.List;

public class FilterItemAdapter extends RecyclerView.Adapter<FilterItemAdapter.ViewHolder> {
    private Context mContext;
    private List<FilterModel> filterModelList;

    public FilterItemAdapter(Context mContext, List<FilterModel> filterModelList) {
        this.mContext = mContext;
        this.filterModelList = filterModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFilterItemBinding filterItemBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_filter_item, parent, false);
        return new FilterItemAdapter.ViewHolder(filterItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilterModel filterModel = filterModelList.get(position);
        holder.filterItemBinding.filterName.setText(filterModel.getName());
        if (filterModel.isSelected())
            holder.filterItemBinding.parent.setBackground(mContext.getResources().getDrawable(R.drawable.backgroundforfilter));
        else
            holder.filterItemBinding.parent.setBackground(mContext.getResources().getDrawable(R.drawable.square_stroke_bg));
        holder.itemView.setOnClickListener(view -> {
            if (filterModelList.get(position).isSelected())
                filterModelList.get(position).setSelected(false);
            else
                filterModelList.get(position).setSelected(true);
            notifyItemChanged(position);
        });

    }


    @Override
    public int getItemCount() {
        return filterModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFilterItemBinding filterItemBinding;

        public ViewHolder(@NonNull AdapterFilterItemBinding filterItemBinding) {
            super(filterItemBinding.getRoot());
            this.filterItemBinding = filterItemBinding;
        }
    }
}
