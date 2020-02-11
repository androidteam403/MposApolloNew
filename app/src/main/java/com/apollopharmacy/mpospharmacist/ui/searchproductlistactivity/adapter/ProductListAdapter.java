package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<GetItemDetailsRes.Items> productListArrayList;
    private ProductListMvpView productListMvpView;

    public ProductListAdapter(Activity activity, ArrayList<GetItemDetailsRes.Items> productListArrayList) {
        this.activity = activity;
        this.productListArrayList = productListArrayList;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductListAdapterBinding productListAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.product_list_adapter, parent, false);
        return new ProductListAdapter.ViewHolder(productListAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ViewHolder holder, int position) {
        GetItemDetailsRes.Items item = productListArrayList.get(position);
        holder.productListAdapterBinding.setProductlist(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productListMvpView != null){
                    productListMvpView.onClickProductItem(item);
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ProductListAdapterBinding productListAdapterBinding;

        public ViewHolder(@NonNull ProductListAdapterBinding productListAdapterBinding) {
            super(productListAdapterBinding.getRoot());
            this.productListAdapterBinding = productListAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return productListArrayList.size();
    }

    public void setClickListiner(ProductListMvpView clickListiner){
        this.productListMvpView = clickListiner;
    }
}
