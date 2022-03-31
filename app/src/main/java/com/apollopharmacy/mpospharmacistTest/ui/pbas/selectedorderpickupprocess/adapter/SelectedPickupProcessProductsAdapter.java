package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterSelectedPickupProcessProductsPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.selectedorderpickupprocess.SelectedOrderPickupProcessMvpView;

import java.util.List;

public class SelectedPickupProcessProductsAdapter extends RecyclerView.Adapter<SelectedPickupProcessProductsAdapter.ViewHolder> {
    private Context mContext;
    private List<RacksDataResponse.FullfillmentDetail.Product> productList;
    private SelectedOrderPickupProcessMvpView mvpView;

    public SelectedPickupProcessProductsAdapter(Context mContext, List<RacksDataResponse.FullfillmentDetail.Product> productList, SelectedOrderPickupProcessMvpView mvpView) {
        this.mContext = mContext;
        this.productList = productList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterSelectedPickupProcessProductsPBinding selectedPickupProcessProductsBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_selected_pickup_process_products_p, parent, false);
        return new SelectedPickupProcessProductsAdapter.ViewHolder(selectedPickupProcessProductsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RacksDataResponse.FullfillmentDetail.Product product = productList.get(position);
        holder.selectedPickupProcessProductsBinding.statusUpdateIcon.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.onClickStausIcon();
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterSelectedPickupProcessProductsPBinding selectedPickupProcessProductsBinding;

        public ViewHolder(@NonNull AdapterSelectedPickupProcessProductsPBinding selectedPickupProcessProductsBinding) {
            super(selectedPickupProcessProductsBinding.getRoot());
            this.selectedPickupProcessProductsBinding = selectedPickupProcessProductsBinding;
        }
    }
}
