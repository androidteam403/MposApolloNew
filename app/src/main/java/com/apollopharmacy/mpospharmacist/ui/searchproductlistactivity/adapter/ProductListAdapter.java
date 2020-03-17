package com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ProductListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.ProductListMvpView;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.ProductList;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Filterable {
    private ProductListActivity activity;
    private ArrayList<GetItemDetailsRes.Items> productListArrayList;
    private ArrayList<GetItemDetailsRes.Items> productListFiltered;
    private ProductListMvpView productListMvpView;

    public ProductListAdapter(ProductListActivity activity, ArrayList<GetItemDetailsRes.Items> productListArrayList) {
        this.activity = activity;
        this.productListArrayList = productListArrayList;
        this.productListFiltered = productListArrayList;
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
        GetItemDetailsRes.Items item = productListFiltered.get(position);
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
        return productListFiltered.size();
    }

    public void setClickListiner(ProductListMvpView clickListiner){
        this.productListMvpView = clickListiner;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = productListArrayList;
                } else {
                    ArrayList<GetItemDetailsRes.Items> filteredList = new ArrayList<>();
                    for (GetItemDetailsRes.Items row : productListArrayList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getArtCode().startsWith(charString.toUpperCase()) || row.getDescription().startsWith(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }

                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<GetItemDetailsRes.Items>) filterResults.values;
                notifyDataSetChanged();
                activity.updateProductsCount(productListFiltered.size());
            }
        };
    }
}
