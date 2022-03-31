package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewEprescOrderItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.FiltersModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OrderListRecycleAdapter extends RecyclerView.Adapter<OrderListRecycleAdapter.ItemBaseViewHolder> implements Filterable {

    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> mOrderArrList=new ArrayList<>();
    private List<OMSTransactionHeaderResModel.OMSHeaderObj> mFilteredOrderArrList=new ArrayList<>();
    ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> filteredList = new ArrayList<>();
    private EprescriptionsListMvpView ePrescriptionListMvpView;
    private EprescriptionsListMvpPresenter<EprescriptionsListMvpView> mPresenter;
    private Context mContext;

    public OrderListRecycleAdapter(Context context, ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> orderInfoArrList, EprescriptionsListMvpPresenter<EprescriptionsListMvpView> presenter) {
        this.mOrderArrList = orderInfoArrList;
        this.mContext = context;
        this.mFilteredOrderArrList = orderInfoArrList;
        //  this.ePrescriptionListMvpView = ePrescriptionListMvpView;
        this.mPresenter = presenter;
    }

    @NotNull
    @Override
    public ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewEprescOrderItemBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_epresc_order_item, parent, false);

        return new ItemBaseViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final ItemBaseViewHolder holder, int position) {
        OMSTransactionHeaderResModel.OMSHeaderObj item = mFilteredOrderArrList.get(position);

       // Spannable wordtoSpan = new SpannableString(String.valueOf(mFilteredOrderArrList.get(position).getOrderPickup()) );
       // wordtoSpan.setSpan(new ForegroundColorSpan(Color.BLACK),  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

               if(mFilteredOrderArrList.get(position).getOrderPickup())
               {
                   holder.listItemMainBinding.orderpickupstatus.setText("Completed");

               }
               else
               {
                   holder.listItemMainBinding.orderpickupstatus.setText("Pending");
               }

               if(mFilteredOrderArrList.get(position).getOrderPacked())
               {
                   holder.listItemMainBinding.orderpackingstatus.setText("Completed");

               }
               else
               {
                   holder.listItemMainBinding.orderpackingstatus.setText("Pending");
               }


       // holder.listItemMainBinding.orderpickupstatus.setText(String.valueOf(mFilteredOrderArrList.get(position).getOrderPickup()));
       // holder.listItemMainBinding.orderpackingstatus.setText(String.valueOf(mFilteredOrderArrList.get(position).getOrderPacked()));

        holder.listItemMainBinding.setModel(item);
        holder.itemView.setOnClickListener(v -> {
            if (this.mPresenter != null) {
               //ePrescriptionListMvpView.onOrderItemClick(position, item);
                this.mPresenter.onOrderItemClick(position, item);
            }
        });

        mPresenter.noOrderfound(mFilteredOrderArrList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {

        int count=0;
        if(mFilteredOrderArrList != null && mFilteredOrderArrList.size() > 0)
        {
            count=mFilteredOrderArrList.size();
        }

        return count;

    }

    static class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        public ViewEprescOrderItemBinding listItemMainBinding;
      //  mFilteredOrderArrList= new ArrayList<>();

        public ItemBaseViewHolder(ViewEprescOrderItemBinding item) {
            super(item.getRoot());

            this.listItemMainBinding = item;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredOrderArrList = mOrderArrList;
                } else {
                    filteredList.clear();
                    for (OMSTransactionHeaderResModel.OMSHeaderObj row : mOrderArrList) {

                        if (!filteredList.contains(row) && (row.getREFNO().contains(charString)||row.getReciptId().contains(charString) || row.getVendorId().contains(charString) || row.getStockStatus().contains(charString)|| row.getCustomerType().contains(charString)||row.getOrderType().contains(charString)|| row.getShippingMethod().contains(charString)||row.getPaymentSource().contains(charString))) {
                            if(Constant.getInstance().filtersModel.getStockstatus())
                           {
                               if(row.getStockStatus().contains("STOCK AVAILABLE"))
                               {

                               }
                           }
                            filteredList.add(row);
                        }

                    }
                  //  if(!filteredList.isEmpty()) {
                        mFilteredOrderArrList = filteredList;
                   // }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredOrderArrList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(mFilteredOrderArrList != null && !mFilteredOrderArrList.isEmpty()) {
                    mFilteredOrderArrList = (ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj>) filterResults.values;
                   // mPresenter.noOrderfound(mFilteredOrderArrList.size());
                    try
                    {
                        //System.out.println("Inside try block");
                         mPresenter.noOrderfound(mFilteredOrderArrList.size());
                        notifyDataSetChanged();
                    }
                    catch(NullPointerException ex)
                    {
                        System.out.println("Exception caught in Catch block");
                    }
                    catch (ArrayIndexOutOfBoundsException ex)
                    {
                        System.out.println("Exception caught in Catch block");
                    }

                   // notifyDataSetChanged();
                }
                else
                {
                    mPresenter.noOrderfound(0);
                    notifyDataSetChanged();
                }
            }
        };
    }
}
