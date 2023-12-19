package com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFulfilmentV3Binding;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.HeaderItemDecoration;
import com.apollopharmacy.mpospharmacistTest.ui.mposvthree.openorders.OpenOrdersV3MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class FulfilmentAdapter extends RecyclerView.Adapter<FulfilmentAdapter.ViewHolder> implements Filterable, HeaderItemDecoration.StickyHeaderInterface {
    private final Context context;
    private List<TransactionHeaderResponse.OMSHeader> filteredOmsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();
    private final OpenOrdersV3MvpView mvpView;
    private String userId;
    private LayoutInflater inflater;

    private boolean isDispatchCutoffTime = false;
    private boolean isSelectIconChecked = false;
    private String shipmentTat = "";
    private List<TransactionHeaderResponse.OMSHeader> selectedList = new ArrayList<>();

    public FulfilmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> omsHeaderList, OpenOrdersV3MvpView mvpView, List<GetOMSTransactionResponse> getOMSTransactionResponseList, String userId) {
        this.context = context;
        this.omsHeaderList = omsHeaderList;
        this.filteredOmsHeaderList = omsHeaderList;
        this.mvpView = mvpView;
        this.userId = userId;
        this.inflater = LayoutInflater.from(context);
//        this.getOMSTransactionResponseList = getOMSTransactionResponseList;
    }

    public void setDispatchCutoffTime(boolean isDispatchCutoffTime) {
        this.isDispatchCutoffTime = isDispatchCutoffTime;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterFulfilmentV3Binding fulfilmentV3Binding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.adapter_fulfilment_v3,
                parent,
                false
        );
        return new ViewHolder(fulfilmentV3Binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader omsHeader = filteredOmsHeaderList.get(position);
        AtomicReference<String> selectedShipmentTat = new AtomicReference<>("");
        Map<String, Long> groupByShipmentTat = filteredOmsHeaderList.stream()
                .collect(Collectors.groupingBy(TransactionHeaderResponse.OMSHeader::getShipmentTat, Collectors.counting()));
        if (position == 0 || !filteredOmsHeaderList.get(position).getShipmentTat().equalsIgnoreCase(filteredOmsHeaderList.get(position - 1).getShipmentTat())) {
            holder.fulfilmentV3Binding.shipmentDateHeader.setVisibility(View.VISIBLE);
            if (filteredOmsHeaderList.get(position).isShipmentTatSelected()) {
                holder.fulfilmentV3Binding.selectIcon.setImageDrawable(context.getDrawable(R.drawable.ic_artboard_40));
            } else {
                holder.fulfilmentV3Binding.selectIcon.setImageDrawable(context.getDrawable(R.drawable.ic_artboard_39));
            }
            holder.fulfilmentV3Binding.selectIcon.setOnClickListener(view -> {
                for (Map.Entry<String, Long> entry : groupByShipmentTat.entrySet()) {
                    if (omsHeader.getShipmentTat().equalsIgnoreCase(entry.getKey())) {
                        selectedShipmentTat.set(entry.getKey());
                    }
                }
//                mvpView.onShipmentTatSelect(selectedShipmentTat.get(), isSelectIconChecked, position);
            });
            groupByShipmentTat.forEach((k, v) -> {
                if (omsHeader.getShipmentTat().equalsIgnoreCase(k)) {
                    holder.fulfilmentV3Binding.shipmentCount.setText("(" + v + ")");
                }
            });
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(omsHeader.getShipmentTat());
                String shipmentDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(date);
                holder.fulfilmentV3Binding.shipmentDate.setText(shipmentDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            holder.fulfilmentV3Binding.shipmentDateHeader.setVisibility(View.GONE);
        }

        if (!omsHeader.getPickPackUser().isEmpty() && !omsHeader.getPickPackUser().equalsIgnoreCase(userId)) {
            holder.fulfilmentV3Binding.rightArrow.setColorFilter(Color.parseColor("#3eadfe"));
        }
        if (omsHeader.getPickPackUser().isEmpty() || omsHeader.getPickPackUser() == null) {
            holder.fulfilmentV3Binding.rightArrow.setColorFilter(Color.parseColor("#039347"));
        }
        if (!omsHeader.getPickPackUser().isEmpty() && omsHeader.getPickPackUser().equalsIgnoreCase(userId)) {
            holder.fulfilmentV3Binding.rightArrow.setColorFilter(Color.parseColor("#f29227"));
        }

        String[] messageList = omsHeaderList.get(position).getOverallOrderStatus().split(",");
        if (messageList.length > 1) {
            omsHeaderList.get(position).setScannedBarcode(messageList[1]);
            omsHeaderList.get(position).setTagBox(true);
        }
        holder.fulfilmentV3Binding.fullfilmentId.setText(" " + omsHeader.getRefno());
        holder.fulfilmentV3Binding.items.setText(String.valueOf(omsHeader.getNumberofItemLines()));
        if (omsHeader.getStockStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.fulfilmentV3Binding.pickupStatus.setText("NO");
            holder.fulfilmentV3Binding.pickupStatus.setTextColor(Color.parseColor("#ff0202"));
        } else {
            holder.fulfilmentV3Binding.pickupStatus.setText("YES");
            holder.fulfilmentV3Binding.pickupStatus.setTextColor(Color.parseColor("#009245"));
        }
        holder.fulfilmentV3Binding.orderSourceHeader.setText(omsHeader.getOrderSource());
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm:ss", Locale.ENGLISH);
        Date inputDate = null;
        try {
            inputDate = inputFormat.parse(omsHeader.getDeliveryDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDate = outputFormat.format(inputDate);
        holder.fulfilmentV3Binding.deliveryDatePickpack.setText(outputDate);
        holder.fulfilmentV3Binding.deliveryDatePickpack.setText(outputDate);
        if (omsHeader.getReVerification() == 1) {
            holder.fulfilmentV3Binding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_reverification_bg));
        } else {
            holder.fulfilmentV3Binding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
        }
        holder.fulfilmentV3Binding.fullfilmentId.setText(" " + omsHeader.getRefno());
        if (omsHeader.getPickPackStatus().equalsIgnoreCase("1") && userId.equalsIgnoreCase(omsHeader.getPickPackUser())) {
            holder.fulfilmentV3Binding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.middle_drop_orders_bg));
        }
        if (omsHeader.getGetOMSTransactionResponse() != null) {
            FulfilmentDetailsAdapter productListAdapter = new FulfilmentDetailsAdapter(context, null, mvpView, position, omsHeader.getGetOMSTransactionResponse().getSalesLine());
            new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
            holder.fulfilmentV3Binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.fulfilmentV3Binding.recyclerView.setAdapter(productListAdapter);
        }
        switch (filteredOmsHeaderList.get(position).getExpandStatus()) {
            case 0:
                holder.fulfilmentV3Binding.rightArrow.setRotation(0);
                filteredOmsHeaderList.get(position).setExpandStatus(0);
                holder.fulfilmentV3Binding.rackChild2Layout.setVisibility(View.GONE);
                holder.fulfilmentV3Binding.rackChild2Layout.setBackground(null);
                break;
            case 1:
                if (omsHeader.getGetOMSTransactionResponse() != null) {
                    holder.fulfilmentV3Binding.rightArrow.setRotation(90);
                    holder.fulfilmentV3Binding.customerType.setText(omsHeader.getGetOMSTransactionResponse().getCustomerType());
                    holder.fulfilmentV3Binding.ordersource.setText(omsHeader.getGetOMSTransactionResponse().getVendorId());
                    holder.fulfilmentV3Binding.orderDate.setText(omsHeader.getGetOMSTransactionResponse().getCreatedDateTime());
                    holder.fulfilmentV3Binding.deliveryDate.setText(omsHeader.getGetOMSTransactionResponse().getDeliveryDate());
                    holder.fulfilmentV3Binding.shippingMethodType.setText(omsHeader.getGetOMSTransactionResponse().getShippingMethod());
                    holder.fulfilmentV3Binding.sourceOrderName.setText(omsHeader.getGetOMSTransactionResponse().getOrderSource());
                    holder.fulfilmentV3Binding.paymentSource.setText(omsHeader.getGetOMSTransactionResponse().getPaymentSource());
                    holder.fulfilmentV3Binding.orderType.setText(omsHeader.getGetOMSTransactionResponse().getOrderType());
                    holder.fulfilmentV3Binding.customerName.setText(omsHeader.getGetOMSTransactionResponse().getCustomerName());
                    holder.fulfilmentV3Binding.vendorId.setText(omsHeader.getGetOMSTransactionResponse().getVendorId());
                    holder.fulfilmentV3Binding.mobileNumber.setText(omsHeader.getGetOMSTransactionResponse().getMobileNO());
                    holder.fulfilmentV3Binding.orderbillvalue.setText(String.valueOf(omsHeader.getGetOMSTransactionResponse().getNetAmount()));
                    holder.fulfilmentV3Binding.doctorName.setText(omsHeader.getGetOMSTransactionResponse().getDoctorName());
                    holder.fulfilmentV3Binding.statecode.setText(omsHeader.getGetOMSTransactionResponse().getCustomerState());
                    holder.fulfilmentV3Binding.city.setText(omsHeader.getGetOMSTransactionResponse().getBillingCity());
                    holder.fulfilmentV3Binding.address.setText(omsHeader.getGetOMSTransactionResponse().getCustAddress());
                    holder.fulfilmentV3Binding.pincode.setText(omsHeader.getGetOMSTransactionResponse().getPincode());
                    holder.fulfilmentV3Binding.comments.setText(omsHeader.getGetOMSTransactionResponse().getComment());
                    if (omsHeader.getGetOMSTransactionResponse().getSalesLine() != null && omsHeader.getGetOMSTransactionResponse().getSalesLine().size() > 0) {
                        holder.fulfilmentV3Binding.fulfillmentType.setText(omsHeader.getGetOMSTransactionResponse().getSalesLine().get(0).getFullfillmentType());
                    }
                    holder.fulfilmentV3Binding.shipmentTat.setText(omsHeader.getShipmentTat());
                    holder.fulfilmentV3Binding.billdateTat.setText(omsHeader.getBillingTat());
                }
                holder.fulfilmentV3Binding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.fulfilmentV3Binding.orderChildLayout.setVisibility(View.VISIBLE);
                holder.fulfilmentV3Binding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
                break;
            default:
        }
        if (filteredOmsHeaderList.get(position).isSelected()) {
            holder.fulfilmentV3Binding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_artboard_40));
        } else {
            holder.fulfilmentV3Binding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_artboard_39));
        }
        holder.fulfilmentV3Binding.rightArrow.setOnClickListener(view -> {
            if (mvpView != null) {
                mvpView.ondownArrowClicked(omsHeader.getRefno(), position);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            if (!omsHeader.getStockStatus().equals("NOT AVAILABLE")) {
                if (mvpView != null)
                    for (int i = 0; i < omsHeaderList.size(); i++) {
                        if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                            mvpView.onFullfillmentItemClick(i, position, omsHeader);
                            break;
                        }
                    }
            } else {
                Toast.makeText(context, omsHeader.getStockStatus(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.fulfilmentV3Binding.selectbutton.setOnClickListener(v -> {
            for (int i = 0; i < omsHeaderList.size(); i++) {
                if (omsHeaderList.get(i).getRefno().equals(omsHeader.getRefno())) {
                    mvpView.onFullfillmentItemClick(i, position, omsHeader);
                    break;
                }
            }
        });
        if (omsHeader.getGetOMSTransactionResponse() != null
                && omsHeader.getGetOMSTransactionResponse().getOrderPrescriptionURL() != null
                && omsHeader.getGetOMSTransactionResponse().getOrderPrescriptionURL().size() > 0) {
            holder.fulfilmentV3Binding.prescriptionsLayout.setVisibility(View.VISIBLE);
            PrescriptionsAdapter prescriptionsAdapter = new PrescriptionsAdapter(context, mvpView, omsHeader.getGetOMSTransactionResponse().getOrderPrescriptionURL());
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.fulfilmentV3Binding.prescriptionsRecycler.setLayoutManager(mLayoutManager);
            holder.fulfilmentV3Binding.prescriptionsRecycler.setAdapter(prescriptionsAdapter);
        } else {
            holder.fulfilmentV3Binding.prescriptionsLayout.setVisibility(View.GONE);
        }
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

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        int headerPosition = 0;
        do {
            if (this.isHeader(itemPosition)) {
                headerPosition = itemPosition;
                break;
            }
            itemPosition -= 1;
        } while (itemPosition >= 0);
        return headerPosition;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.adapter_fulfilment_v3_header;
    }

    @Override
    public void bindHeaderData(View headerView, int headerPosition, int itemPosition) {
        if (filteredOmsHeaderList != null && filteredOmsHeaderList.size() > 0) {
            TransactionHeaderResponse.OMSHeader omsHeader = filteredOmsHeaderList.get(headerPosition);
            TextView shipmentCount = headerView.findViewById(R.id.shipment_count_n);
            TextView shipmentDateN = headerView.findViewById(R.id.shipment_date_n);
            Map<String, Long> groupByShipmentTat = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                groupByShipmentTat = filteredOmsHeaderList.stream()
                        .collect(Collectors.groupingBy(TransactionHeaderResponse.OMSHeader::getShipmentTat, Collectors.counting()));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                groupByShipmentTat.forEach((k, v) -> {
                    if (omsHeader.getShipmentTat().equalsIgnoreCase(k)) {
                        shipmentCount.setText("(" + v + ")");
                    }
                });
            }
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(omsHeader.getShipmentTat());
                String shipmentDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(date);
                shipmentDateN.setText(String.valueOf(shipmentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (itemPosition == 0) {
            return true;
        } else if (filteredOmsHeaderList != null && filteredOmsHeaderList.size() > 0) {
            if (filteredOmsHeaderList.get(itemPosition) != null && !filteredOmsHeaderList.get(itemPosition).getShipmentTat().equalsIgnoreCase(filteredOmsHeaderList.get(itemPosition - 1).getShipmentTat())) {
                return true;
            }
        }
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFulfilmentV3Binding fulfilmentV3Binding;

        public ViewHolder(@NonNull AdapterFulfilmentV3Binding fulfilmentV3Binding) {
            super(fulfilmentV3Binding.getRoot());
            this.fulfilmentV3Binding = fulfilmentV3Binding;
        }
    }
}
