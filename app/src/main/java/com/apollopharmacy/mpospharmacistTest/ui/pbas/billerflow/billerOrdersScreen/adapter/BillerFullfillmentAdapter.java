package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBillerOrdersScreenPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen.BillerOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.io.Serializable;
import java.util.List;

public class BillerFullfillmentAdapter extends RecyclerView.Adapter<BillerFullfillmentAdapter.ViewHolder> {

    private Context context;
    private List<TransactionHeaderResponse.OMSHeader> fullfilmentModelList;
    private BillerOrdersMvpView mvpView;

    public BillerFullfillmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> fullfilmentModelList, BillerOrdersMvpView mvpView) {
        this.context = context;
        this.fullfilmentModelList = fullfilmentModelList;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_biller_orders_screen_p, parent, false);
        return new ViewHolder(adapterBillerOrdersScreenBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader fullfilmentModel = fullfilmentModelList.get(position);
        holder.adapterBillerOrdersScreenBinding.fullfillmentID.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getRefno());
        holder.adapterBillerOrdersScreenBinding.totalItems.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getNumberofItemLines());
        holder.adapterBillerOrdersScreenBinding.status.setText("Partial");





        holder.adapterBillerOrdersScreenBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mvpView != null)
                    mvpView.onRightArrowClickedContinue(position);
            }
        });

    }


    @Override
    public int getItemCount() {

        return fullfilmentModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding;

        public ViewHolder(@NonNull AdapterBillerOrdersScreenPBinding adapterBillerOrdersScreenBinding) {
            super(adapterBillerOrdersScreenBinding.getRoot());
            this.adapterBillerOrdersScreenBinding = adapterBillerOrdersScreenBinding;
        }
    }

    public static class FullfilmentModel implements Serializable {
        private String fullfilmentId;
        private String totalItems;
        private boolean isSelected;

        public String getFullfilmentId() {
            return fullfilmentId;
        }

        public void setFullfilmentId(String fullfilmentId) {
            this.fullfilmentId = fullfilmentId;
        }

        public String getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(String totalItems) {
            this.totalItems = totalItems;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}

