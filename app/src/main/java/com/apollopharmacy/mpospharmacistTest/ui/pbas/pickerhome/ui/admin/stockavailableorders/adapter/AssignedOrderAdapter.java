package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.LayoutOrderBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.model.GetOMSTransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.admin.stockavailableorders.StockAvailableOrdersMvpView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignedOrderAdapter extends RecyclerView.Adapter<AssignedOrderAdapter.ViewHolder> {
    private final Context context;
    private List<GetOMSTransactionHeaderResponse.OMSHeader> assignedOrders;
    private StockAvailableOrdersMvpView mvpView;

    public AssignedOrderAdapter(Context context, List<GetOMSTransactionHeaderResponse.OMSHeader> assignedOrders, StockAvailableOrdersMvpView mvpView) {
        this.context = context;
        this.assignedOrders = assignedOrders;
        this.mvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOrderBinding layoutOrderBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_order, parent, false);
        return new ViewHolder(layoutOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetOMSTransactionHeaderResponse.OMSHeader omsHeader = assignedOrders.get(position);
        holder.layoutOrderBinding.fulfilmentId.setText(omsHeader.getRefno());
        holder.layoutOrderBinding.sourceId.setText(omsHeader.getOrderSource());
        holder.layoutOrderBinding.unAssignBtn.setVisibility(View.VISIBLE);
        holder.layoutOrderBinding.assignBtn.setVisibility(View.GONE);
        holder.layoutOrderBinding.assignedToLayout.setVisibility(View.VISIBLE);
        holder.layoutOrderBinding.items.setText(String.valueOf(omsHeader.getNumberofItemLines()));
        holder.layoutOrderBinding.assignedTo.setText(omsHeader.getPickPackUser());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss", Locale.ENGLISH);
        Date inputDate = null;
        try {
            inputDate = inputFormat.parse(omsHeader.getDeliveryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = outputFormat.format(inputDate);
        holder.layoutOrderBinding.deliveryDate.setText(outputDate);
        holder.layoutOrderBinding.unAssignBtn.setOnClickListener(view -> mvpView.onClickUnAssign(omsHeader, omsHeader.getPickPackUser()));
    }

    @Override
    public int getItemCount() {
        return assignedOrders.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LayoutOrderBinding layoutOrderBinding;

        public ViewHolder(@NonNull LayoutOrderBinding layoutOrderBinding) {
            super(layoutOrderBinding.getRoot());
            this.layoutOrderBinding = layoutOrderBinding;
        }
    }

    public void setFilteredList(List<GetOMSTransactionHeaderResponse.OMSHeader> filteredList) {
        assignedOrders = filteredList;
        notifyDataSetChanged();
    }
}
