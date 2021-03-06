package com.apollopharmacy.mpospharmacistTest.ui.batchonfo.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.BatchInfoListAdapterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.BatchInfoMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.batchonfo.model.GetBatchInfoRes;

import java.util.ArrayList;

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
        if(item.getREQQTY()== (Double) item.getREQQTY())
        {
            double d=item.getREQQTY();
            int value = Integer.valueOf((int) item.getREQQTY());
            item.setREQQTY(value);
            holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText(String.valueOf(value));
        }
        else
        {
            item.setREQQTY(item.getREQQTY());
             holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText(String.valueOf(item.getREQQTY()));

        }




        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                batchInfoMvpView.onItemExpiryClick(position, item.getEnterReqQuantity());
                return false;
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (batchInfoMvpView != null) {
                if (!item.getNearByExpiry()) {
                    batchInfoMvpView.onItemClick(position, item.getEnterReqQuantity(), item);
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
               /* if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                    editable.delete(0, 1);
                }*/
                if (editable.toString().isEmpty()) {
                    item.setREQQTY(0);
                } else if (!TextUtils.isEmpty(editable)) {
                    if (batchInfoMvpView != null) {
                        item.setREQQTY(Double.parseDouble(editable.toString()));
                    }
                }
                holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setSelection(editable.length());
            }
        });

        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (batchInfoMvpView != null) {
                        batchInfoMvpView.onNavigateNextActivity();
                    }
                    return true;
                }
                return false;
            }
        });
        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.hasFocus()) {
                    if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().equalsIgnoreCase("0")) {
                        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("");
                    }
                    InputMethodManager imm = (InputMethodManager) holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(holder.batchInfoListAdapterBinding.batchWiseQtyEdit, InputMethodManager.SHOW_IMPLICIT);
                    }
                } else {
                    if (holder.batchInfoListAdapterBinding.batchWiseQtyEdit.getText().toString().isEmpty()) {
                        holder.batchInfoListAdapterBinding.batchWiseQtyEdit.setText("0");
                    }
                }
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private BatchInfoListAdapterBinding batchInfoListAdapterBinding;
        private EditText reqqtyedittext;

        public ViewHolder(@NonNull BatchInfoListAdapterBinding batchInfoListAdapterBinding) {
            super(batchInfoListAdapterBinding.getRoot());
            this.batchInfoListAdapterBinding = batchInfoListAdapterBinding;
            this.reqqtyedittext=batchInfoListAdapterBinding.batchWiseQtyEdit;
        }
    }

    @Override
    public int getItemCount() {
        return arrBatchList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
