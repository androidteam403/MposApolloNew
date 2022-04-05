package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterFullfilmentPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.OpenOrdersMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;


import java.io.Serializable;
import java.util.List;

public class FullfilmentAdapter extends RecyclerView.Adapter<FullfilmentAdapter.ViewHolder> {
    private final Context context;
    private final List<TransactionHeaderResponse.OMSHeader> omsHeaderList;
    private final OpenOrdersMvpView mvpView;
//    List<RackAdapter.RackBoxModel.ProductData> listOfList;
//    private RacksDataResponse racksDataResponse;
   public List<GetOMSTransactionResponse> getOMSTransactionResponseList;
    private boolean firstAccessCheck;

    public FullfilmentAdapter(Context context, List<TransactionHeaderResponse.OMSHeader> omsHeaderList, OpenOrdersMvpView mvpView,  List<GetOMSTransactionResponse> getOMSTransactionResponseList) {
        this.context = context;
       this.omsHeaderList = omsHeaderList;
        this.mvpView = mvpView;
//        this.listOfList = listOfList;
//        this.racksDataResponse = racksDataResponse;
        this.getOMSTransactionResponseList=getOMSTransactionResponseList;
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

        TransactionHeaderResponse.OMSHeader omsHeader = omsHeaderList.get(position);
        holder.fullfilmentBinding.fullfilmentId.setText(context.getResources().getString(R.string.label_space) + omsHeader.getVendorId());
        holder.fullfilmentBinding.items.setText(String.valueOf(omsHeaderList.size()));
        holder.fullfilmentBinding.status.setText(omsHeader.getStockStatus());
//        switch (fullfilmentModel.getRequestStatus()) {

//            case 0:
////                holder.orderBinding.orderChildLayout.setBackgroundColor(context.getResources().getColor(R.color.lite_grey));
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 1:
////                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.orderChildLayout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
//                break;
//            case 3:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//
//            case 4:
//
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 5:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 6:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 7:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 8:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            case 9:
//                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
//                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
//                break;
//            default:
//        }


//        if (fullfilmentModel.isSelected) {
//            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
////            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
//        } else {
//            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_stroke));
////            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));
//
//        }
//        holder.fullfilmentBinding.fullfilmentId.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getFullfilmentId());
//        holder.fullfilmentBinding.items.setText(context.getResources().getString(R.string.label_space) + fullfilmentModel.getTotalItems());
//        holder.fullfilmentBinding.productItems.setText("" + fullfilmentModel.totalItems);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
         if (mvpView != null)
            mvpView.onFullfillmentItemClick(position);
             notifyDataSetChanged();
    }
        });

        switch (omsHeaderList.get(position).getExpandStatus()) {

            case 0:
                if(omsHeaderList.get(position).getExpandStatus()==1){
                    holder.fullfilmentBinding.rightArrow.setRotation(0);
                    omsHeaderList.get(position).setExpandStatus(0);
                }
//                fullfilmentModel.setExpandStatus(0);
//                holder.orderBinding.orderChildLayout.setBackgroundColor(context.getResources().getColor(R.color.lite_grey));
                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.GONE);
                holder.fullfilmentBinding.rackChild2Layout.setBackground(null);
                break;
            case 1:
                if(getOMSTransactionResponseList!=null && getOMSTransactionResponseList.size()>0){

                holder.fullfilmentBinding.rightArrow.setRotation(90);
                holder.fullfilmentBinding.customerType.setText(getOMSTransactionResponseList.get(0).getCustomerType());
                holder.fullfilmentBinding.ordersource.setText(getOMSTransactionResponseList.get(0).getOrderSource());
                holder.fullfilmentBinding.orderDate.setText(getOMSTransactionResponseList.get(0).getCreatedDateTime());
                holder.fullfilmentBinding.deliveryDate.setText(getOMSTransactionResponseList.get(0).getDeliveryDate());
                holder.fullfilmentBinding.shippingMethodType.setText(getOMSTransactionResponseList.get(0).getShippingMethod());
                holder.fullfilmentBinding.stockStatus.setText(getOMSTransactionResponseList.get(0).getStockStatus());
                holder.fullfilmentBinding.paymentSource.setText(getOMSTransactionResponseList.get(0).getPaymentSource());
                holder.fullfilmentBinding.orderType.setText(getOMSTransactionResponseList.get(0).getOrderType());
                }
                if(getOMSTransactionResponseList!=null && getOMSTransactionResponseList.size()>0) {


//        FulfilmentDetailsAdapter productListAdapter = new FulfilmentDetailsAdapter(context, racksDataResponse.getFullfillmentDetails().get(position).getProducts(), mvpView, position);
                    FulfilmentDetailsAdapter productListAdapter = new FulfilmentDetailsAdapter(context, null, mvpView, position, getOMSTransactionResponseList.get(0).getSalesLine());
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true);
                    holder.fullfilmentBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    holder.fullfilmentBinding.recyclerView.setAdapter(productListAdapter);
                    holder.itemView.setOnClickListener(v -> {



                    });

                }
//                holder.orderBinding.statusLayout.setVisibility(View.VISIBLE);
                holder.fullfilmentBinding.rackChild2Layout.setVisibility(View.VISIBLE);
                holder.fullfilmentBinding.orderChildLayout.setVisibility(View.VISIBLE);
                holder.fullfilmentBinding.rackChild2Layout.setBackground(context.getResources().getDrawable(R.drawable.yellow_stroke_bg));
                break;
            default:
        }


        if (omsHeaderList.get(position).isSelected()) {
            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_yellow_bg));
        } else {
            holder.fullfilmentBinding.fullfillmentSelectIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_stroke));
//            holder.fullfilmentBinding.fullfillmentParentLayout.setBackground(context.getResources().getDrawable(R.drawable.square_stroke_bg));

        }
            holder.fullfilmentBinding.selectbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mvpView != null)
                        mvpView.onFullfillmentItemClick(position);
                }
            });

        holder.fullfilmentBinding.fullfillmentSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mvpView != null)
                    mvpView.onRightArrowClickedContinue(position);
            }
        });


        holder.fullfilmentBinding.rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();

                if(mvpView!=null && omsHeaderList.get(position).getExpandStatus()==0){
                    mvpView.ondownArrowClicked(position);
                }

                for(TransactionHeaderResponse.OMSHeader fullfilmentModel : omsHeaderList){
                    if(fullfilmentModel.getExpandStatus()==1){
                        fullfilmentModel.setExpandStatus(0);
                        holder.fullfilmentBinding.rightArrow.setRotation(0);
                    }
                }




            }
        });
    }

    @Override
    public int getItemCount() {
        return omsHeaderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterFullfilmentPBinding fullfilmentBinding;

        public ViewHolder(@NonNull AdapterFullfilmentPBinding fullfilmentBinding) {
            super(fullfilmentBinding.getRoot());
            this.fullfilmentBinding = fullfilmentBinding;
        }
    }

//    public static class FullfilmentModel implements Serializable {
//        private String fullfilmentId;
//        private String totalItems;
//        private boolean isSelected;
//        private int expandStatus = 0;
//
//        public int getExpandStatus() {
//            return expandStatus;
//        }
//
//        public void setExpandStatus(int expandStatus) {
//            this.expandStatus = expandStatus;
//        }
//
////        public String getFullfilmentId() {
////            return fullfilmentId;
////        }
////
////        public void setFullfilmentId(String fullfilmentId) {
////            this.fullfilmentId = fullfilmentId;
////        }
////
////        public String getTotalItems() {
////            return totalItems;
////        }
////
////        public void setTotalItems(String totalItems) {
////            this.totalItems = totalItems;
////        }
//
//        public boolean isSelected() {
//            return isSelected;
//        }
//
//        public void setSelected(boolean selected) {
//            isSelected = selected;
//        }
//    }
}
