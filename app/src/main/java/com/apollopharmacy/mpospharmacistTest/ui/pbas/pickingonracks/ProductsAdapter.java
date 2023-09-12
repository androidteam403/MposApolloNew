package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickingonracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterProductsLayoutBinding;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    Context context;

    public ProductsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterProductsLayoutBinding productsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_products_layout, parent, false);
        return new ViewHolder(productsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == 3) {
            holder.productsLayoutBinding.bottomStripLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AdapterProductsLayoutBinding productsLayoutBinding;

        public ViewHolder(@NonNull AdapterProductsLayoutBinding productsLayoutBinding) {
            super(productsLayoutBinding.getRoot());
            this.productsLayoutBinding = productsLayoutBinding;
        }
    }
}
