package com.apollopharmacy.mpospharmacist.ui.batchonfo.adapter;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacist.ui.batchonfo.model.GetBatchInfoRes;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BatchInfoAdapter extends RecyclerView.Adapter<BatchInfoAdapter.ViewHolder> {

    private ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList;

    private BatchInfoMvpView batchInfoMvpView;

    public BatchInfoAdapter(ArrayList<GetBatchInfoRes.BatchListObj> arrBatchList, BatchInfoMvpView batchInfoMvpView) {
        this.arrBatchList = arrBatchList;
        this.batchInfoMvpView = batchInfoMvpView;
    }

    @NonNull
    @Override
    public BatchInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BatchInfoListAdapterBinding batchInfoListAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.batch_info_list_adapter, parent, false);
        return new BatchInfoAdapter.ViewHolder(batchInfoListAdapterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BatchInfoAdapter.ViewHolder holder, int position) {
        GetBatchInfoRes.BatchListObj item = arrBatchList.get(position);
        holder.batchInfoListAdapterBinding.setBatchInfo(item);

        holder.itemView.setOnClickListener(v -> {
            if (batchInfoMvpView != null) {
                if(!item.getNearByExpiry()) {
                    batchInfoMvpView.onItemClick(position, item.getEnterReqQuantity());
                    holder.batchInfoListAdapterBinding.batchWiseQtyEdit.requestFocus();
                }
            }
        });
        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                    editable.delete(0,1);
                } else if(TextUtils.isEmpty(editable)){
                    editable.append("0");
                } else {
                    if (batchInfoMvpView != null) {
                        item.setREQQTY(Integer.parseInt(editable.toString()));
//                        if (!item.getNearByExpiry() && !TextUtils.isEmpty(editable) && !editable.toString().equalsIgnoreCase("0"))
                        //    batchInfoMvpView.onBatchQTYChange(position, Integer.parseInt(editable.toString()));
                    }
                }
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(editable.length());
            }
        });

        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(batchInfoMvpView != null){
                        batchInfoMvpView.onNavigateNextActivity();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private BatchInfoListAdapterBinding batchInfoListAdapterBinding;

        public ViewHolder(@NonNull BatchInfoListAdapterBinding batchInfoListAdapterBinding) {
            super(batchInfoListAdapterBinding.getRoot());
            this.batchInfoListAdapterBinding = batchInfoListAdapterBinding;
        }
    }

    @Override
    public int getItemCount() {
        return arrBatchList.size();
    }
}
