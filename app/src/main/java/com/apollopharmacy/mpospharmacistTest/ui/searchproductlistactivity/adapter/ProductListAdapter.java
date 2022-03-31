package com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ProductListAdapterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.ProductListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;

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
        if (productListFiltered.size() > 0) {
            GetItemDetailsRes.Items item = productListFiltered.get(position);
            holder.productListAdapterBinding.setProductlist(item);
            if (item.getDPCO()) {
                holder.productListAdapterBinding.itemBackground.setBackgroundColor(ContextCompat.getColor(activity, R.color.cpb_yellow));
                holder.productListAdapterBinding.arrowBack.setBackgroundColor(ContextCompat.getColor(activity, R.color.cpb_yellow));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (productListMvpView != null) {
                        productListMvpView.onClickProductItem(item);
                    }
                }
            });
        }
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

    public void setClickListiner(ProductListMvpView clickListiner) {
        this.productListMvpView = clickListiner;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (!charString.isEmpty()) {
                    ArrayList<GetItemDetailsRes.Items> filteredList = new ArrayList<>();
                    for (GetItemDetailsRes.Items row : productListArrayList) {
                        if (charString.toUpperCase().contains("%")) {
                            String[] splitKey = charString.toUpperCase().split("%");
                            if (splitKey.length > 1) {
                                for (int i = 0; i < splitKey.length; i++) {
                                    if (i != 0) {
                                        if (!filteredList.contains(row) && (row.getArtCode().contains(splitKey[i]) || row.getDescription().contains(splitKey[i]))) {
                                            filteredList.add(row);
                                        }
                                    }
                                }
                            } else {
                                if (!filteredList.contains(row) && (row.getArtCode().contains(charString.toUpperCase().replace("%", "")) || row.getDescription().contains(charString.toUpperCase().replace("%", "")))) {
                                    filteredList.add(row);
                                }
                            }
                        } else {
                            if (!filteredList.contains(row) && (row.getArtCode().contains(charString.toUpperCase()) || row.getDescription().contains(charString.toUpperCase()))) {
                                filteredList.add(row);
                            }
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

    public void clearDate() {
        productListFiltered.clear();
        productListArrayList.clear();
        notifyDataSetChanged();
    }

    public void add(ArrayList<GetItemDetailsRes.Items> productListFiltered) {
        this.productListFiltered = productListFiltered;
        this.productListArrayList = productListFiltered;
    }
}
