package com.apollopharmacy.mpospharmacist.ui.additem.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.CategoryDisplayItemBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class CategoryDiscAdapter extends RecyclerView.Adapter<CategoryDiscAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList;
    private CategoryDisplayItemBinding displayItemBinding;

    public CategoryDiscAdapter(Context activity, ArrayList<ManualDiscCheckRes.DisplayList> arrPayAdapterModel) {
        this.context = activity;
        this.displayListArrayList = arrPayAdapterModel;
    }

    @NonNull
    @Override
    public CategoryDiscAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        displayItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.category_display_item, parent, false);
        return new CategoryDiscAdapter.ViewHolder(displayItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDiscAdapter.ViewHolder holder, int position) {
        ManualDiscCheckRes.DisplayList item = displayListArrayList.get(position);
        holder.displayItemBinding.setDisplayItem(item);
        holder.displayItemBinding.cashPaymentAmountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0)
                displayListArrayList.get(position).setDiscountValue(Integer.valueOf(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                     editable.delete(0,1);
                }else if(editable.toString().length() == 0){
                    editable.append("0");
                }
                holder.displayItemBinding.cashPaymentAmountEdit.setSelection(editable.length());
            }
        });
    }

    @Override
    public int getItemCount() {
        return displayListArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CategoryDisplayItemBinding displayItemBinding;

        public ViewHolder(@NonNull CategoryDisplayItemBinding displayItemBinding){
            super(displayItemBinding.getRoot());
            this.displayItemBinding = displayItemBinding;
        }
    }
}