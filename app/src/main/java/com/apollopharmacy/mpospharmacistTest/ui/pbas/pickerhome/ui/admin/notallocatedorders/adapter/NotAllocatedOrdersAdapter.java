package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.notallocatedorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterNotAllocatedOrdersBinding;

public class NotAllocatedOrdersAdapter extends RecyclerView.Adapter<NotAllocatedOrdersAdapter.ViewHolder> {
    private Context context;

    public NotAllocatedOrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterNotAllocatedOrdersBinding notAllocatedOrdersBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_not_allocated_orders, parent, false);
        return new ViewHolder(notAllocatedOrdersBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 3) {
            holder.notAllocatedOrdersBinding.bottomStripLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterNotAllocatedOrdersBinding notAllocatedOrdersBinding;

        public ViewHolder(@NonNull AdapterNotAllocatedOrdersBinding notAllocatedOrdersBinding) {
            super(notAllocatedOrdersBinding.getRoot());
            this.notAllocatedOrdersBinding = notAllocatedOrdersBinding;
        }
    }
}
