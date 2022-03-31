package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogEprescriptionCancelBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogStocknotavailableBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.EditphysicalbatchdialogBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.StocknotAvailableBinding;
import com.apollopharmacy.mpospharmacistTest.ui.addcustomer.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo.EPrescriptionInfoMvpView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

public class StockNotVailableDialog
{
    private Dialog dialog;
    private StocknotAvailableBinding editphysicalbatchdialogBinding;

    private boolean negativeExist = false;
    private SalesLineEntity editItem;

    public StockNotVailableDialog(Context context) {
        dialog = new Dialog(context);
        editphysicalbatchdialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.stocknot_available, null, false);
        dialog.setContentView(editphysicalbatchdialogBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       /* editphysicalbatchdialogBinding.editQuantityEditText.addTextChangedListener(new TextWatcher() {
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



    public void setPositiveListener(View.OnClickListener okListener) {
        editphysicalbatchdialogBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        editphysicalbatchdialogBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
            editphysicalbatchdialogBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            editphysicalbatchdialogBinding.separator.setVisibility(View.VISIBLE);

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        editphysicalbatchdialogBinding.title.setText(title);
    }



    public void setPositiveLabel(String positive) {
        editphysicalbatchdialogBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
       // negativeExist = true;
        editphysicalbatchdialogBinding.dialogButtonNO.setText(negative);
    }

}
