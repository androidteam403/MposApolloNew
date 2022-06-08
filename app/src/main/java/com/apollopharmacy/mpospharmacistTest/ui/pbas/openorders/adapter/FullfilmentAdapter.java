package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFullfilmentPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.util.ArrayList;
import java.util.List;

public class FullfilmentAdapter extends RecyclerView.Adapter<FullfilmentAdapter.ViewHolder> implements Filterable {
    private final Context context;
    private List<TransactionHeaderResponse.OMSHeader> filteredOmsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();
    private final OpenOrdersMvpView mvpView;
    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;

    public FullfilmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> omsHeaderList, OpenOrdersMvpView mvpView, List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
        this.context = context;
        this.omsHeaderList = omsHeaderList;
        this.filteredOmsHeaderList = omsHeaderList;
        this.mvpView = mvpView;
        this.getOMSTransactionResponseList = getOMSTransactionResponseList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFullfilmentPBinding fullfilmentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_fullfilment_p, parent, false);
        return new ViewHolder(fullfilmentBinding);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TransactionHeaderResponse.OMSHeader omsHeader = filteredOmsHeaderList.get(position);
        holder.fullfilmentBinding.fullfilmentId.setText(context.getResources().getString(R.string.label_space) + omsHeader.getRefno());
        holder.fullfilmentBinding.items.setText(String.valueOf(omsHeader.getNumberofItemLines()));
        holder.fullfilmentBinding.pickupStatus.setText(String.valueOf(omsHeader.getStockStatus()));

        if (getOMSTransactionResponseList != null && getOMSTransactionResponseList.size() > 0) {
            FulfilmentDetailsAdapter productListAdapter = new FulfilmentDetailsAdapter(context, null, mvpView, position, getOMSTransactionResponseList.get(0).getSalesLine());
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.fullfilmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.fullfilmentBinding.recyclerView.setAdapter(productListAdapter);
        }


//        if (omsHeader.getOrderPickup()) {
//            holder.fullfilmentBinding.pickupStatus.setText("Completed");
//
//        } else {
//            holder.fullfilmentBinding.pickupStatus.setText("Pending");
//        }
        switch (filteredOmsHeaderList.get(position).getExpandStatus()) {
            case 0:
                holder.fullfilmentBinding.rightArrow.setRotation(0);
                filteredOmsHeaderList.get(position).setExpandStatus(0);
                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
                break;
            case 1:
                if (getOMSTransactionResponseList != null && getOMSTransactionResponseList.size() > 0) {
                    holder.fullfilmentBinding.rightArrow.setRotation(90);
                    holder.fullfilmentBinding.customerType.setText(getOMSTransactionResponseList.get(0).getCustomerType());
                    holder.fullfilmentBinding.ordersource.setText(getOMSTransactionResponseList.get(0).getOrderSource());
                    holder.fullfilmentBinding.orderDate.setText(getOMSTransactionResponseList.get(0).getCreatedDateTime());
                    holder.fullfilmentBinding.deliveryDate.setText(getOMSTransactionResponseList.get(0).getDeliveryDate());
                    holder.fullfilmentBinding.shippingMethodType.setText(getOMSTransactionResponseList.get(0).getShippingMethod());
                    holder.fullfilmentBinding.stockStatus.setText(getOMSTransactionResponseList.get(0).getStockStatus());
                    holder.fullfilmentBinding.paymentSource.setText(getOMSTransactionResponseList.get(0).getPaymentSource());
                    holder.fullfilmentBinding.orderType.setText(getOMSTransactionResponseList.get(0).getOrderType());
                    holder.fullfilmentBinding.customerName.setText(getOMSTransactionResponseList.get(0).getCustomerName());
                    holder.fullfilmentBinding.vendorId.setText(getOMSTransactionResponseList.get(0).getVendorId());
                    holder.fullfilmentBinding.mobileNumber.setText(getOMSTransactionResponseList.get(0).getMobileNO());
                    holder.fullfilmentBinding.orderbillvalue.setText(String.valueOf(getOMSTransactionResponseList.get(0).getNetAmount()));
                    holder.fullfilmentBinding.doctorName.setText(getOMSTransactionResponseList.get(0).getDoctorName());
                    holder.fullfilmentBinding.statecode.setText(getOMSTransactionResponseList.get(0).getCustomerState());
                    holder.fullfilmentBinding.city.setText(getOMSTransactionResponseList.get(0).getBillingCity());
                    holder.fullfilmentBinding.address.setText(getOMSTransactionResponseList.get(0).getCustAddress());
                    holder.fullfilmentBinding.pincode.setText(getOMSTransactionResponseList.get(0).getPincode());
                    holder.fullfilmentBinding.comments.setText(getOMSTransactionResponseList.get(0).getComment());
//                    if(getOMSTransactionResponseList.get(position).getStockStatus().equalsIgnoreCase("NOT AVAILABLE")){
//                        holder.fullfilmentBinding.selectbutton.setVisibility(View.GONE);
//                        holder.fullfilmentBinding.notifytoadmin.setVisibility(View.VISIBLE);
//                    }

                }


                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.fullfilmentBinding.orderChildLayout.setVisibility(View.VISIBLE);
                holder.fullfilmentBinding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
                break;
            default:
        }

        if (filteredOmsHeaderList.get(position).isSelected()) {
            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
        } else {
            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_stroke));
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
        }
        holder.fullfilmentBinding.rightArrow.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.ondownArrowClicked(omsHeader.getRefno(), position);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (!omsHeader.getStockStatus().equals("NOT AVAILABLE")) {
                if (mvpView != null)
                    for (int i = 0; i < omsHeaderList.size(); i++) {
                        if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                            mvpView.onFullfillmentItemClick(i, position);
                            break;
                        }
                    }
            } else {
                Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fullfilmentBinding.selectbutton.setOnClickListener(v -> {
//            if (!omsHeader.getStockStatus().equals("NOT AVAILABLE")) {
                for (int i = 0; i < omsHeaderList.size(); i++) {
                    if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                        mvpView.onFullfillmentItemClick(i, position);
                        break;
                    }
                }
//            } else {
//                Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
//           }
        });

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (filteredOmsHeaderList != null && filteredOmsHeaderList.size() > 0) {
            count = filteredOmsHeaderList.size();
        }
        return count;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredOmsHeaderList = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (TransactionHeaderResponse.OMSHeader row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getRefno().contains(charString))) {
                            filteredList.add(row);
                        }

                    }
                    filteredOmsHeaderList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredOmsHeaderList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filteredOmsHeaderList != null && !filteredOmsHeaderList.isEmpty()) {
                    filteredOmsHeaderList = (ArrayList<TransactionHeaderResponse.OMSHeader>) filterResults.values;
                    try {
                        mvpView.noOrderFound(filteredOmsHeaderList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    mvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFullfilmentPBinding fullfilmentBinding;

        public ViewHolder(@NonNull AdapterFullfilmentPBinding fullfilmentBinding) {
            super(fullfilmentBinding.getRoot());
            this.fullfilmentBinding = fullfilmentBinding;
        }
    }
}
