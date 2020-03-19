package com.apollopharmacy.mpospharmacist.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.EditQuantityDialogBinding;
import com.apollopharmacy.mpospharmacist.databinding.ExitInfoDialogBinding;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import androidx.databinding.DataBindingUtil;

public class EditQuantityDialog {

    private Dialog dialog;
    private EditQuantityDialogBinding editQuantityDialogBinding;

    private boolean negativeExist = false;
    private GetItemDetailsRes.Items editItem;

    public EditQuantityDialog(Context context) {
        dialog = new Dialog(context);
        editQuantityDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.edit_quantity_dialog, null, false);
        dialog.setContentView(editQuantityDialogBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setItemData(GetItemDetailsRes.Items item){
        editItem = item;
    }

    public int getEnteredQuantity(){
        return Integer.parseInt(editQuantityDialogBinding.editQuantityEditText.getText().toString());
    }

    public boolean validateQuantity(){
        if(!TextUtils.isEmpty(editQuantityDialogBinding.editQuantityEditText.getText().toString())){
            if(editItem.getBatchListObj().getEnterReqQuantity() < getEnteredQuantity()){
                editQuantityDialogBinding.editQuantityEditText.setError("Please Enter Valid Quantity");
                return false;
            }else {
                editItem.getBatchListObj().setEnterReqQuantity(getEnteredQuantity());
                return true;
            }
        }else{
            editQuantityDialogBinding.editQuantityEditText.setError("Please Enter Quantity");
            return false;
        }
    }
    public void setPositiveListener(View.OnClickListener okListener) {
        editQuantityDialogBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        editQuantityDialogBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            editQuantityDialogBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            editQuantityDialogBinding.separator.setVisibility(View.VISIBLE);
        } else {
            editQuantityDialogBinding.dialogButtonNO.setVisibility(View.GONE);
            editQuantityDialogBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        editQuantityDialogBinding.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        editQuantityDialogBinding.subtitle.setText(subtitle);
    }

    public void setPositiveLabel(String positive) {
        editQuantityDialogBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        editQuantityDialogBinding.dialogButtonNO.setText(negative);
    }

}
