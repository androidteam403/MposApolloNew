package com.apollo.pharmacy.ui.searchuser.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.SearchCustomerAdapterBinding;
import com.apollo.pharmacy.ui.searchuser.SearchUserMvpPresenter;
import com.apollo.pharmacy.ui.searchuser.SearchUserMvpView;
import com.apollo.pharmacy.ui.searchuser.model.SearchCustomerAdapterModel;

import java.util.ArrayList;

public class SearchCustomerAdapter extends RecyclerView.Adapter<SearchCustomerAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<SearchCustomerAdapterModel> arrSearchCustomerAdapterModel = new ArrayList<>();
    private SearchUserMvpPresenter<SearchUserMvpView> mPresenter;

    public SearchCustomerAdapter(Activity activity,
                                 ArrayList<SearchCustomerAdapterModel> arrSearchCustomerAdapterModel,
                                 SearchUserMvpPresenter<SearchUserMvpView> mPresenter) {
        this.activity = activity;
        this.arrSearchCustomerAdapterModel = arrSearchCustomerAdapterModel;
        this.mPresenter = mPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchCustomerAdapterBinding searchCustomerAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.search_customer_adapter, parent, false);
        return new ViewHolder(searchCustomerAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SearchCustomerAdapterModel searchCustomerAdapterModel = arrSearchCustomerAdapterModel.get(position);
        holder.searchCustomerAdapterBinding.setModel(searchCustomerAdapterModel);
        holder.searchCustomerAdapterBinding.setCallback(mPresenter);
    }

    @Override
    public int getItemCount() {
        return arrSearchCustomerAdapterModel.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SearchCustomerAdapterBinding searchCustomerAdapterBinding;

        public ViewHolder(@NonNull SearchCustomerAdapterBinding searchCustomerAdapterBinding) {
            super(searchCustomerAdapterBinding.getRoot());
            this.searchCustomerAdapterBinding = searchCustomerAdapterBinding;
        }
    }
}
