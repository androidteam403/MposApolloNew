package com.apollo.pharmacy.ui.searchproduct.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ViewSearchProductBatchInfoBinding;
import com.apollo.pharmacy.databinding.ViewSearchProductItemInfoBinding;
import com.apollo.pharmacy.ui.searchproduct.listener.SearchProductListener;
import com.apollo.pharmacy.ui.searchproduct.model.ProductInfoPojo;

import java.util.ArrayList;

public class ProductInfoAdapter extends RecyclerView.Adapter<ProductInfoAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<ProductInfoPojo> productInfoArrayList;
    private SearchProductListener searchProductListener;

    public ProductInfoAdapter(Activity activity, ArrayList<ProductInfoPojo> productInfoArrayList,
                              SearchProductListener searchProductListener) {
        this.activity = activity;
        this.productInfoArrayList = productInfoArrayList;
        this.searchProductListener=searchProductListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewSearchProductItemInfoBinding viewSearchProductItemInfoBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.view_search_product_item_info, parent, false);
        return new ViewHolder(viewSearchProductItemInfoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductInfoPojo item = productInfoArrayList.get(position);
        holder.viewSearchProductItemInfoBinding.setProductInfo(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchProductListener!=null){
                    searchProductListener.onItemClick(item);
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewSearchProductItemInfoBinding viewSearchProductItemInfoBinding;
        public ViewSearchProductBatchInfoBinding viewSearchProductBatchInfoBinding;
        public ViewHolder(@NonNull ViewSearchProductItemInfoBinding viewSearchProductItemInfoBinding) {
            super(viewSearchProductItemInfoBinding.getRoot());
            this.viewSearchProductItemInfoBinding = viewSearchProductItemInfoBinding;
        }
    }

    @Override
    public int getItemCount() {
        return productInfoArrayList.size();
    }
}