package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterPickedUpordersPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.model.OMSOrderUpdateResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.PickedUpOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickeduporders.model.OMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.ArrayList;
import java.util.List;

public class PickedUpOrdersAdapter extends RecyclerView.Adapter<PickedUpOrdersAdapter.ViewHolder>  implements Filterable {
    private Context context;
    private List<TransactionHeaderResponse.OMSHeader> fullfillmentList = new ArrayList<>();
    private PickedUpOrdersMvpView pickupProcessMvpView;
    public List<OMSOrderUpdateResponse> salesLineList;
    private List<TransactionHeaderResponse.OMSHeader> filteredList = new ArrayList<>();
    private List<TransactionHeaderResponse.OMSHeader> omsHeaderList = new ArrayList<>();
    List<List<RackAdapter.RackBoxModel.ProductData>> listOfList;
    private boolean firstAccessCheck;


    public PickedUpOrdersAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> fullfillmentList, PickedUpOrdersMvpView pickupProcessMvpView) {
        this.context = context;
        this.fullfillmentList = fullfillmentList;

        this.pickupProcessMvpView = pickupProcessMvpView;
//        this.listOfList = fullfillmentListOfListFiltered;
//        this.firstAccessCheck = acessCheck;
    }

    @NonNull
    @Override
    public PickedUpOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterPickedUpordersPBinding orderBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_picked_uporders_p, parent, false);
        return new PickedUpOrdersAdapter.ViewHolder(orderBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull PickedUpOrdersAdapter.ViewHolder holder, int position) {
        TransactionHeaderResponse.OMSHeader fullFillModel = fullfillmentList.get(position);
        holder.orderBinding.fullfillmentID.setText(fullFillModel.getRefno());
        holder.orderBinding.totalItems.setText(String.valueOf(fullFillModel.getNumberofItemLines()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pickupProcessMvpView.onItmClick(position,holder.orderBinding.status.getText().toString(),fullfillmentList,fullFillModel);

                pickupProcessMvpView.onItmClick(position, fullfillmentList);
            }
        });

        if (fullFillModel.getStockStatus() != null && fullFillModel.getStockStatus().equalsIgnoreCase("PARTIAL AVAILABLE")) {

            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
            holder.orderBinding.status.setText("Partial");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);

        } else if (fullFillModel.getStockStatus() != null && fullFillModel.getStockStatus().equalsIgnoreCase("NOT AVAILABLE")) {
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
            holder.orderBinding.status.setText("Not Available");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);

        } else if (fullFillModel.getStockStatus() != null && fullFillModel.getStockStatus().equalsIgnoreCase("STOCK AVAILABLE")) {
            holder.orderBinding.statusIcon.setRotation(0);
            holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
            holder.orderBinding.status.setText("Full");
            holder.orderBinding.statusIcon.setVisibility(View.VISIBLE);
        }

//        switch (Integer.valueOf(fullFillModel.getStockStatus())) {
//            case 0:
//                holder.orderBinding.orderChildLayout.setBackgroundColor(context.getResources().getColor(R.color.lite_grey));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.statusLayout.setVisibility(View.GONE);
//                break;
//            case 1:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("In progress");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.in_progress));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 2:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("In progress");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.in_progress));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 3:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Partial");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//
//            case 4:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Partial");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 5:
//                holder.orderBinding.orderChildLayout.setBackgroundColor(context.getResources().getColor(R.color.trans_red));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Not Available");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 6:
//                holder.orderBinding.orderChildLayout.setBackgroundColor(context.getResources().getColor(R.color.trans_red));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Not Available");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 7:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Full");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 8:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Full");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            case 9:
//                holder.orderBinding.orderChildLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_green));
//                holder.orderBinding.start.setVisibility(View.GONE);
//                holder.orderBinding.status.setText("Full");
//                holder.orderBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                break;
//            default:
//        }

//        List<RackAdapter.RackBoxModel.ProductData> productDataList = new ArrayList<>();
//        for (int k = 0; k < fullFillModel.getProducts().size(); k++) {
//            RackAdapter.RackBoxModel.ProductData productData = new RackAdapter.RackBoxModel.ProductData();
//            productData.setProductId(fullFillModel.getProducts().get(k).getProductId());
//            productData.setProductName(fullFillModel.getProducts().get(k).getProductName());
//            productData.setRackId(fullFillModel.getProducts().get(k).getRackId());
//            productData.setAvailableQuantity(fullFillModel.getProducts().get(k).getAvailableQuantity());
//            productData.setRequiredQuantity(fullFillModel.getProducts().get(k).getRequiredQuantity());
//            productData.setBatchId(fullFillModel.getProducts().get(k).getBatchId());
//            productData.setPackerStatus(fullFillModel.getProducts().get(k).getPackerStatus());
//            if (listOfList != null && listOfList.size() > 0) {
//                for (int i = 0; i < listOfList.size(); i++) {
//                    for (int l = 0; l < listOfList.get(i).size(); l++) {
//                        if (listOfList.get(i).get(l).getProductId().equalsIgnoreCase(fullFillModel.getProducts().get(k).getProductId())) {
//                            productData.setCapturedQuantity(listOfList.get(i).get(l).getCapturedQuantity());
//                            productData.setStatus(listOfList.get(i).get(l).getStatus());
//                            productData.setProductStatusFillingUpdate(listOfList.get(i).get(l).isProductStatusFillingUpdate());
//                        }
//                    }
//                }
//            } else {
//                productData.setCapturedQuantity(fullFillModel.getProducts().get(k).getCapturedQuantity());
//                productData.setStatus(fullFillModel.getProducts().get(k).getStatus());
//                productData.setProductStatusFillingUpdate(fullFillModel.getProducts().get(k).isProductStatusFillingUpdate());
//            }
//            productDataList.add(productData);
//        }
//
//        if (!firstAccessCheck)
//            firstTimeMultipleStatusCheck(productDataList, position, holder.orderBinding);
//        holder.orderBinding.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (pickupProcessMvpView != null) {
//                    if (!holder.orderBinding.status.getText().toString().equalsIgnoreCase("Not Available"))
//                        pickupProcessMvpView.onItemClick(position, holder.orderBinding.status.getText().toString(), productDataList, fullfillmentList, fullFillModel);
//                }
//            }
//        });

    }


    private void firstTimeMultipleStatusCheck(List<RackAdapter.RackBoxModel.ProductData> productDataList, int position, AdapterPickedUpordersPBinding rackBinding) {
        boolean full = false;
        boolean partial = false;
        boolean notAvailable = false;
        int notFlag = 0;
        int fullFalg = 0;

        if (listOfList != null && listOfList.size() > 0) {
            for (int i = 0; i < listOfList.size(); i++) {
                for (int k = 0; k < listOfList.get(i).size(); k++) {
                    for (int j = 0; j < productDataList.size(); j++) {
                        if (listOfList.get(i).get(k).getProductId().equalsIgnoreCase(productDataList.get(j).getProductId())) {
                            if (productDataList.get(j).getStatus() != null) {
                                if (productDataList.get(j).getStatus().equalsIgnoreCase(listOfList.get(i).get(k).getStatus()))
                                    productDataList.get(j).setStatus(listOfList.get(i).get(k).getStatus());
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productDataList.size(); i++) {
            if (productDataList.get(i).getStatus() != null) {
                if (productDataList.get(i).getStatus().equalsIgnoreCase("Partial")) {
                    partial = true;
                    notAvailable = false;
                    full = false;
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("NotAvailable")) {
                    partial = true;
                    notFlag = notFlag + 1;
                    if (notFlag == productDataList.size()) {
                        notAvailable = true;
                        partial = false;
                        full = false;
                    }
                } else if (productDataList.get(i).getStatus().equalsIgnoreCase("Full")) {
                    partial = true;
                    fullFalg = fullFalg + 1;
                    if (fullFalg == productDataList.size()) {
                        full = true;
                        partial = false;
                        notAvailable = false;
                    }
                }
            }
        }

//        if (partial) {
//            fullfillmentList.get(position).setExpandStatus(3);
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Partial");
//            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partial));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//        } else if (notAvailable) {
//            fullfillmentList.get(position).setExpandStatus(5);
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Not Available");
//            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_not_available));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//        } else if (full) {
//            fullfillmentList.get(position).setExpandStatus(7);
//            rackBinding.start.setVisibility(View.GONE);
//            rackBinding.status.setText("Full");
//            rackBinding.statusIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//            rackBinding.statusLayout.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (fullfillmentList != null && fullfillmentList.size() > 0) {
            count = fullfillmentList.size();
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
                    fullfillmentList = omsHeaderList;
                } else {
                    filteredList.clear();
                    for (TransactionHeaderResponse.OMSHeader row : omsHeaderList) {
                        if (!filteredList.contains(row) && (row.getRefno().contains(charString))) {
                            filteredList.add(row);
                        }

                    }
                    fullfillmentList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = fullfillmentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (fullfillmentList != null && !fullfillmentList.isEmpty()) {
                    fullfillmentList = (ArrayList<TransactionHeaderResponse.OMSHeader>) filterResults.values;
                    try {
                        pickupProcessMvpView.noOrderFound(fullfillmentList.size());
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("FullfilmentAdapter", e.getMessage());
                    }
                } else {
                    pickupProcessMvpView.noOrderFound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPickedUpordersPBinding orderBinding;

        public ViewHolder(@NonNull AdapterPickedUpordersPBinding orderBinding) {
            super(orderBinding.getRoot());
            this.orderBinding = orderBinding;
        }
    }

}