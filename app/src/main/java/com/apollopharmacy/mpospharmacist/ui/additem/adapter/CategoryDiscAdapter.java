package com.apollopharmacy.mpospharmacist.ui.additem.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.CategoryDisplayItemBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.utils.DecimalDigitsInputFilter;

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
        holder.displayItemBinding.cashPaymentAmountEdit.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(100,2)});
        holder.displayItemBinding.cashPaymentAmountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString();
                if (charSequence.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        holder.displayItemBinding.cashPaymentAmountEdit.setText(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString().replace(".", ""));
                        holder.displayItemBinding.cashPaymentAmountEdit.setSelection(holder.displayItemBinding.cashPaymentAmountEdit.getText().length());
                    }
//                    else if (text.indexOf("0") == 0) {
//                        digitEditText.setText(digitEditText.getText().toString().replace("0", ""));
//                    }
                } else {
                    if (text.contains(".") &&
                            text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        holder.displayItemBinding.cashPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        holder.displayItemBinding.cashPaymentAmountEdit.setSelection(holder.displayItemBinding.cashPaymentAmountEdit.getText().length());
                        displayListArrayList.get(position).setDiscountValue(Double.parseDouble(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString()));
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        holder.displayItemBinding.cashPaymentAmountEdit.setText(text.substring(0, text.length() - 1));
                        holder.displayItemBinding.cashPaymentAmountEdit.setSelection(holder.displayItemBinding.cashPaymentAmountEdit.getText().length());
                        displayListArrayList.get(position).setDiscountValue(Double.parseDouble(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString()));
                    }
                }
//                if(charSequence.length()>0 && !charSequence.toString().equalsIgnoreCase("."))
//                displayListArrayList.get(position).setDiscountValue(Double.parseDouble(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 1 && editable.toString().startsWith("0") && editable.toString().startsWith(".")) {
                     editable.delete(0,1);
                }
//                else if(editable.toString().length() == 0){
//                    editable.append("0");
//                }
//                holder.displayItemBinding.cashPaymentAmountEdit.setSelection(editable.length());
            }
        });

        holder.displayItemBinding.cashPaymentAmountEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(holder.displayItemBinding.cashPaymentAmountEdit.hasFocus()){
                    if(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString().equalsIgnoreCase("0") || holder.displayItemBinding.cashPaymentAmountEdit.getText().toString().equalsIgnoreCase(".0")){
                        holder.displayItemBinding.cashPaymentAmountEdit.setText("");
                        holder.displayItemBinding.cashPaymentAmountEdit.setCursorVisible(true);
                    }
                    InputMethodManager imm = (InputMethodManager)holder.displayItemBinding.cashPaymentAmountEdit.getContext(). getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(holder.displayItemBinding.cashPaymentAmountEdit, InputMethodManager.SHOW_IMPLICIT);
                    }
                }else{
                    if(holder.displayItemBinding.cashPaymentAmountEdit.getText().toString().isEmpty()){
                        holder.displayItemBinding.cashPaymentAmountEdit.setText("0");
                    }
                }
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