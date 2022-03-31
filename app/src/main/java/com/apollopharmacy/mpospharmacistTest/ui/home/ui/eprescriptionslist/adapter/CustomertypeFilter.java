package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewCustomertypeFilterBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewEprescOrderItemBinding;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.CustomerTypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialogMvpView;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomertypeFilter extends RecyclerView.Adapter<CustomertypeFilter.ItemBaseViewHolder> implements Filterable
{

    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<CustomerTypeModel> mOrderArrList;
    private List<CustomerTypeModel> mFilteredOrderArrList;
    private EprescriptionsListMvpView ePrescriptionListMvpView;
    private EprescriptionsListMvpPresenter<EprescriptionsListMvpView> mPresenter;
    private Context mContext;

    public CustomertypeFilter(Context context, ArrayList<CustomerTypeModel> orderInfoArrList, EprescriptionsListMvpPresenter<EprescriptionsListMvpView> presenter) {
        this.mOrderArrList = orderInfoArrList;
        this.mContext = context;
        mFilteredOrderArrList = orderInfoArrList;
        //  this.ePrescriptionListMvpView = ePrescriptionListMvpView;
        this.mPresenter = presenter;
    }

    @NotNull
    @Override
    public CustomertypeFilter.ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewCustomertypeFilterBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_customertype_filter, parent, false);

        return new CustomertypeFilter.ItemBaseViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final CustomertypeFilter.ItemBaseViewHolder holder, int position) {
       // OMSTransactionHeaderResModel.OMSHeaderObj item = mFilteredOrderArrList.get(position);
        CustomerTypeModel Customertype= mFilteredOrderArrList.get(position);

        holder.listItemMainBinding.customertype.setText(Customertype.getCustomertype());
        holder.listItemMainBinding.statusview.setTag(position);

        Integer pos = (Integer) holder.listItemMainBinding.statusview.getTag();
        if(Constant.getInstance().Customertypearraylist.contains(Customertype.getCustomertype()))
        {
            holder.listItemMainBinding.statusview.setButtonDrawable(R.drawable.icon_points_allow);
            mFilteredOrderArrList.get(pos).setCheckstatus(true);
        }
        else
        {
            holder.listItemMainBinding.statusview.setButtonDrawable(R.drawable.icon_unchecked_checkbox);
            mFilteredOrderArrList.get(pos).setCheckstatus(false);
        }


        holder.listItemMainBinding.statusview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.listItemMainBinding.statusview.getTag();
                //Toast.makeText(ctx, imageModelArrayList.get(pos).getAnimal() + " clicked!", Toast.LENGTH_SHORT).show();

                if (mFilteredOrderArrList.get(pos).getCheckstatus()) {
                    mFilteredOrderArrList.get(pos).setCheckstatus(false);
                    holder.listItemMainBinding.statusview.setButtonDrawable(R.drawable.icon_unchecked_checkbox);

                    if(Constant.getInstance().Customertypearraylist.contains(mFilteredOrderArrList.get(pos).getCustomertype()))
                    {
                        Constant.getInstance().Customertypearraylist.remove(mFilteredOrderArrList.get(pos).getCustomertype());
                        notifyDataSetChanged();
                    }
                    else
                    {
                        //Constant.getInstance().Customertypearraylist.add(mFilteredOrderArrList.get(pos).getCustomertype())
                    }
                } else {
                    mFilteredOrderArrList.get(pos).setCheckstatus(true);
                    holder.listItemMainBinding.statusview.setButtonDrawable(R.drawable.icon_points_allow);
                    if(Constant.getInstance().Customertypearraylist.contains(mFilteredOrderArrList.get(pos).getCustomertype()))
                    {

                    }
                    else
                    {
                        Constant.getInstance().Customertypearraylist.add(mFilteredOrderArrList.get(pos).getCustomertype());
                        notifyDataSetChanged();
                    }

                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_ACTION_WIDTH;
    }

    @Override
    public int getItemCount() {
        return mFilteredOrderArrList.size();

    }

    public void onClickListener(EprescriptionsListMvpView mvpView) {
        this.ePrescriptionListMvpView = mvpView;
    }

    static class ItemBaseViewHolder extends RecyclerView.ViewHolder {
        public ViewCustomertypeFilterBinding listItemMainBinding;

        public ItemBaseViewHolder(ViewCustomertypeFilterBinding item) {
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
                    ArrayList<CustomerTypeModel> filteredList = new ArrayList<>();
                    for (CustomerTypeModel row : mOrderArrList) {

                        /*if (!filteredList.contains(row) && (row.getREFNO().contains(charString)||row.getReciptId().contains(charString) || row.getVendorId().contains(charString) || row.getStockStatus().contains(charString)|| row.getCustomerType().contains(charString)||row.getOrderType().contains(charString)|| row.getShippingMethod().contains(charString)||row.getPaymentSource().contains(charString))) {
                            if(Constant.getInstance().filtersModel.getStockstatus())
                            {
                                if(row.getStockStatus().contains("STOCK AVAILABLE"))
                                {

                                }
                            }
                            filteredList.add(row);
                        }*/
                        filteredList.add(row);
                    }
                    mFilteredOrderArrList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredOrderArrList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredOrderArrList = (ArrayList<CustomerTypeModel>) filterResults.values;
                notifyDataSetChanged();
                mPresenter.noOrderfound(mFilteredOrderArrList.size());


            }
        };
    }
}
