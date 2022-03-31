package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ViewCategorytypeFilterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.EprescriptionsListMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.CategorytypeModel;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategorytypeFilter extends RecyclerView.Adapter<CategorytypeFilter.ItemBaseViewHolder> implements Filterable {
    public static final int ITEM_TYPE_ACTION_WIDTH = 1001;
    private List<CategorytypeModel> mOrderArrList;
    private List<CategorytypeModel> mFilteredOrderArrList;
    private EprescriptionsListMvpView ePrescriptionListMvpView;
    private EprescriptionsListMvpPresenter<EprescriptionsListMvpView> mPresenter;
    private Context mContext;

    public CategorytypeFilter(Context context, ArrayList<CategorytypeModel> orderInfoArrList, EprescriptionsListMvpPresenter<EprescriptionsListMvpView> presenter) {
        this.mOrderArrList = orderInfoArrList;
        this.mContext = context;
        mFilteredOrderArrList = orderInfoArrList;
        //  this.ePrescriptionListMvpView = ePrescriptionListMvpView;
        this.mPresenter = presenter;
    }

    @NotNull
    @Override
    public CategorytypeFilter.ItemBaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewCategorytypeFilterBinding itemMainBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_categorytype_filter, parent, false);

        return new CategorytypeFilter.ItemBaseViewHolder(itemMainBinding);
    }

    @Override
    public void onBindViewHolder(@NotNull final CategorytypeFilter.ItemBaseViewHolder holder, int position) {
        // OMSTransactionHeaderResModel.OMSHeaderObj item = mFilteredOrderArrList.get(position);
        CategorytypeModel categorytypeModel= mFilteredOrderArrList.get(position);
        holder.listItemMainBinding.customertype.setText(categorytypeModel.getCategorytype());
        holder.listItemMainBinding.statusview.setTag(position);
        Integer pos = (Integer) holder.listItemMainBinding.statusview.getTag();
        if(Constant.getInstance().Categorytypearraylist.contains(categorytypeModel.getCategorytype()))
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

                    if(Constant.getInstance().Categorytypearraylist.contains(mFilteredOrderArrList.get(pos).getCategorytype()))
                    {
                        Constant.getInstance().Categorytypearraylist.remove(mFilteredOrderArrList.get(pos).getCategorytype());
                        notifyDataSetChanged();
                    }
                    else
                    {
                        //Constant.getInstance().Customertypearraylist.add(mFilteredOrderArrList.get(pos).getCustomertype())
                    }
                } else {
                    mFilteredOrderArrList.get(pos).setCheckstatus(true);
                    holder.listItemMainBinding.statusview.setButtonDrawable(R.drawable.icon_points_allow);
                    if(Constant.getInstance().Categorytypearraylist.contains(mFilteredOrderArrList.get(pos).getCategorytype()))
                    {

                    }
                    else
                    {
                        Constant.getInstance().Categorytypearraylist.add(mFilteredOrderArrList.get(pos).getCategorytype());
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
        public ViewCategorytypeFilterBinding listItemMainBinding;

        public ItemBaseViewHolder(ViewCategorytypeFilterBinding item) {
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
                    ArrayList<CategorytypeModel> filteredList = new ArrayList<>();
                    for (CategorytypeModel row : mOrderArrList) {

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
                mFilteredOrderArrList = (ArrayList<CategorytypeModel>) filterResults.values;
                notifyDataSetChanged();
                mPresenter.noOrderfound(mFilteredOrderArrList.size());

            }
        };
    }
}
