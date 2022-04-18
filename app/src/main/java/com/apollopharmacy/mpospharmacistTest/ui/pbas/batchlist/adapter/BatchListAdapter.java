package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.AdapterBatchlistPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.BatchListMvpView;
import com.bumptech.glide.Glide;

import java.util.List;

public class BatchListAdapter extends RecyclerView.Adapter<BatchListAdapter.ViewHolder> {
    private Context mContext;
    private  List<GetBatchInfoRes.BatchListObj> batchListModelListl;
    private BatchListMvpView batchListMvpView;
    boolean batchSelected=false;

    public BatchListAdapter(Context mContext, List<GetBatchInfoRes.BatchListObj> batchListModelList1, BatchListMvpView mvpView) {
        this.mContext = mContext;
        this.batchListModelListl = batchListModelList1;
        this.batchListMvpView = mvpView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterBatchlistPBinding adapterBatchlistBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.adapter_batchlist_p, parent, false);
        return new BatchListAdapter.ViewHolder(adapterBatchlistBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj batchListModel = batchListModelListl.get(position);
        holder.adapterBatchlistBinding.batchNo.setText(batchListModel.getBatchNo());
        holder.adapterBatchlistBinding.qohCoount.setText(batchListModel.getQ_O_H());
      if (batchListModel.getREQQTY() == (double) batchListModel.getREQQTY()) {
                double d = batchListModel.getREQQTY();
                int value = Integer.valueOf((int) batchListModel.getREQQTY());
                holder.adapterBatchlistBinding.requiredQuantity.setText(String.valueOf(value));
            } else {
                holder.adapterBatchlistBinding.requiredQuantity.setText(String.valueOf(batchListModel.getREQQTY()));
            }

        holder.adapterBatchlistBinding.batchPickupStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.GONE);
                holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.VISIBLE);
                holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.Light_green);
                batchSelected=true;
                batchListMvpView.onCheckBoxClick(position, batchSelected, batchListModel.getREQQTY(), batchListModel.getBatchNo(), batchListModel.getItemID());

            }
        });

        holder.adapterBatchlistBinding.iconPointsAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.adapterBatchlistBinding.iconPointsAllow.setVisibility(View.GONE);
                holder.adapterBatchlistBinding.batchPickupStatus.setVisibility(View.VISIBLE);
                holder.adapterBatchlistBinding.batchidbackground.setBackgroundResource(R.color.white);


            }
        });


//        if (batchListModel.isBatchidSelect())
//            Glide.with(mContext).load(R.drawable.ic_circle_tick).into(holder.adapterBatchlistBinding.batchIdSelectIcon);
//        else
//            Glide.with(mContext).load(R.drawable.ic_circle_stroke).into(holder.adapterBatchlistBinding.batchIdSelectIcon);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mvpView != null)
//                    mvpView.onClickItem(position);
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return batchListModelListl.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterBatchlistPBinding adapterBatchlistBinding;

        public ViewHolder(@NonNull AdapterBatchlistPBinding adapterBatchlistBinding) {
            super(adapterBatchlistBinding.getRoot());
            this.adapterBatchlistBinding = adapterBatchlistBinding;
        }
    }
}
