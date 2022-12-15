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
    private String userId;
//    public List<GetOMSTransactionResponse> getOMSTransactionResponseList;

    public FullfilmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> omsHeaderList, OpenOrdersMvpView mvpView, List<GetOMSTransactionResponse> getOMSTransactionResponseList, String userId) {
        this.context = context;
        this.omsHeaderList = omsHeaderList;
        this.filteredOmsHeaderList = omsHeaderList;
        this.mvpView = mvpView;
        this.userId = userId;
//        this.getOMSTransactionResponseList = getOMSTransactionResponseList;
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

        String[] messageList = omsHeaderList.get(position).getOverallOrderStatus().split(",");
        if (messageList.length > 1) {
            omsHeaderList.get(position).setScannedBarcode(messageList[1]);
            omsHeaderList.get(position).setTagBox(true);
        }

        holder.fullfilmentBinding.fullfilmentId.setText(" " + omsHeader.getRefno());
        holder.fullfilmentBinding.items.setText(String.valueOf(omsHeader.getNumberofItemLines()));
        holder.fullfilmentBinding.pickupStatus.setText(String.valueOf(omsHeader.getStockStatus()));
        holder.fullfilmentBinding.orderSourceHeader.setText(omsHeader.getOrderSource());
        if (omsHeader.getReVerification() == 1) {
            holder.fullfilmentBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_reverification_bg));
        } else {
            holder.fullfilmentBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
        }
        holder.fullfilmentBinding.fullfilmentId.setText(" " + omsHeader.getRefno());
        holder.fullfilmentBinding.items.setText(String.valueOf(omsHeader.getNumberofItemLines()));
        holder.fullfilmentBinding.pickupStatus.setText(String.valueOf(omsHeader.getStockStatus()));
        if (omsHeader.getPickPackStatus().equalsIgnoreCase("1") && userId.equalsIgnoreCase(omsHeader.getPickPackUser())) {
            holder.fullfilmentBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.middle_drop_orders_bg));
        }

        if (omsHeader.getGetOMSTransactionResponse() != null) {
            FulfilmentDetailsAdapter productListAdapter = new FulfilmentDetailsAdapter(context, null, mvpView, position, omsHeader.getGetOMSTransactionResponse().getSalesLine());
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
                if (omsHeader.getGetOMSTransactionResponse() != null) {
                    holder.fullfilmentBinding.rightArrow.setRotation(90);
                    holder.fullfilmentBinding.customerType.setText(omsHeader.getGetOMSTransactionResponse().getCustomerType());
                    holder.fullfilmentBinding.ordersource.setText(omsHeader.getGetOMSTransactionResponse().getOrderSource());
                    holder.fullfilmentBinding.orderDate.setText(omsHeader.getGetOMSTransactionResponse().getCreatedDateTime());
                    holder.fullfilmentBinding.deliveryDate.setText(omsHeader.getGetOMSTransactionResponse().getDeliveryDate());
                    holder.fullfilmentBinding.shippingMethodType.setText(omsHeader.getGetOMSTransactionResponse().getShippingMethod());
                    holder.fullfilmentBinding.stockStatus.setText(omsHeader.getGetOMSTransactionResponse().getStockStatus());
                    holder.fullfilmentBinding.paymentSource.setText(omsHeader.getGetOMSTransactionResponse().getPaymentSource());
                    holder.fullfilmentBinding.orderType.setText(omsHeader.getGetOMSTransactionResponse().getOrderType());
                    holder.fullfilmentBinding.customerName.setText(omsHeader.getGetOMSTransactionResponse().getCustomerName());
                    holder.fullfilmentBinding.vendorId.setText(omsHeader.getGetOMSTransactionResponse().getVendorId());
                    holder.fullfilmentBinding.mobileNumber.setText(omsHeader.getGetOMSTransactionResponse().getMobileNO());
                    holder.fullfilmentBinding.orderbillvalue.setText(String.valueOf(omsHeader.getGetOMSTransactionResponse().getNetAmount()));
                    holder.fullfilmentBinding.doctorName.setText(omsHeader.getGetOMSTransactionResponse().getDoctorName());
                    holder.fullfilmentBinding.statecode.setText(omsHeader.getGetOMSTransactionResponse().getCustomerState());
                    holder.fullfilmentBinding.city.setText(omsHeader.getGetOMSTransactionResponse().getBillingCity());
                    holder.fullfilmentBinding.address.setText(omsHeader.getGetOMSTransactionResponse().getCustAddress());
                    holder.fullfilmentBinding.pincode.setText(omsHeader.getGetOMSTransactionResponse().getPincode());
                    holder.fullfilmentBinding.comments.setText(omsHeader.getGetOMSTransactionResponse().getComment());
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
                if ((omsHeader.getPickPackStatus().equalsIgnoreCase("1") && !userId.equalsIgnoreCase(omsHeader.getPickPackUser()))) {
                    Toast.makeText(context, "Order is in progress", Toast.LENGTH_SHORT).show();
                } else {
                    if (mvpView != null)
                        for (int i = 0; i < omsHeaderList.size(); i++) {
                            if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                                mvpView.onFullfillmentItemClick(i, position, omsHeader);
                                break;
                            }
                        }

                }
            } else {
                Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fullfilmentBinding.selectbutton.setOnClickListener(v -> {
            if (!omsHeader.getStockStatus().equals("NOT AVAILABLE")) {
                if ((omsHeader.getPickPackStatus().equalsIgnoreCase("1") && !userId.equalsIgnoreCase(omsHeader.getPickPackUser()))) {
                    Toast.makeText(context, "Order is in progress", Toast.LENGTH_SHORT).show();

                } else {
                    for (int i = 0; i < omsHeaderList.size(); i++) {
                        if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                            mvpView.onFullfillmentItemClick(i, position, omsHeader);
                            break;
                        }
                    }

                }
            } else {
                Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
            }
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
                        if (!filteredList.contains(row) && (row.getRefno().toLowerCase().contains(charString.toLowerCase()) || row.getOverallOrderStatus().toLowerCase().contains(charString))) {
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
