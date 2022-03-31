package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.CheckreservedqtydialogBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.EditphysicalbatchdialogBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;

public class CheckReservedQtyDialog
{
    private Dialog dialog;
    private CheckreservedqtydialogBinding editphysicalbatchdialogBinding;

    private boolean negativeExist = false;
    private SalesLineEntity editItem;

    public CheckReservedQtyDialog(Context context) {
        dialog = new Dialog(context);
        editphysicalbatchdialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.checkreservedqtydialog, null, false);
        dialog.setContentView(editphysicalbatchdialogBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*editphysicalbatchdialogBinding.editQuantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               *//* if (editable.toString().length() > 1 && editable.toString().startsWith("0")) {
                    editable.delete(0, 1);
                } else if (editable.toString().startsWith("0")) {
                    //editable.append("1");
                }*//*
                editphysicalbatchdialogBinding.editQuantityEditText.setSelection(editable.length());
            }
        });*/
    }

   /* public void setItemData(SalesLineEntity item) {
        editItem = item;
       // editphysicalbatchdialogBinding.editQuantityEditText.setText(String.valueOf(editItem.getQty()));
    }

    public  String getEnterredbatch()
    {
      //  return editphysicalbatchdialogBinding.editQuantityEditText.getText().toString();
    }

    public int getEnteredQuantity() {
       // return Integer.parseInt(editphysicalbatchdialogBinding.editQuantityEditText.getText().toString());
    }
*/
   /* public boolean validateQuantity() {
      *//*  if (!TextUtils.isEmpty(editphysicalbatchdialogBinding.editQuantityEditText.getText().toString())) {
            if (editItem.getQty() < getEnteredQuantity()) {
                editphysicalbatchdialogBinding.editQuantityEditText.setError("Please Enter Valid Quantity");
                return false;
            } else {
                // editItem.setQty(getEnteredQuantity());
                return true;
            }
        } else {
            editphysicalbatchdialogBinding.editQuantityEditText.setError("Please Enter Quantity");
            return false;
        }*//*
    }*/

    public void setPositiveListener(View.OnClickListener okListener) {
        editphysicalbatchdialogBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        editphysicalbatchdialogBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            editphysicalbatchdialogBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            editphysicalbatchdialogBinding.separator.setVisibility(View.VISIBLE);
        } else {
            editphysicalbatchdialogBinding.dialogButtonNO.setVisibility(View.GONE);
            editphysicalbatchdialogBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        editphysicalbatchdialogBinding.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        // editphysicalbatchdialogBinding.subtitle.setText(subtitle);
    }

    public void setPositiveLabel(String positive) {
        editphysicalbatchdialogBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        editphysicalbatchdialogBinding.dialogButtonNO.setText(negative);
    }

}
