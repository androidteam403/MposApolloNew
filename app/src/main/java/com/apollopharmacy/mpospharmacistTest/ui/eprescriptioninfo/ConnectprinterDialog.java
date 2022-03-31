package com.apollopharmacy.mpospharmacistTest.ui.eprescriptioninfo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.CheckreservedqtydialogBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.PrinterconnetlayoutBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;

public class ConnectprinterDialog
{
    private Dialog dialog;
  //  private CheckreservedqtydialogBinding editphysicalbatchdialogBinding;
    private PrinterconnetlayoutBinding printerconnetlayoutBinding;

    private boolean negativeExist = false;
    private SalesLineEntity editItem;

    public ConnectprinterDialog(Context context) {
        dialog = new Dialog(context);
        printerconnetlayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.printerconnetlayout, null, false);
        dialog.setContentView(printerconnetlayoutBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



    public void setPositiveListener(View.OnClickListener okListener) {
        printerconnetlayoutBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        printerconnetlayoutBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            printerconnetlayoutBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            printerconnetlayoutBinding.separator.setVisibility(View.VISIBLE);
        } else {
            printerconnetlayoutBinding.dialogButtonNO.setVisibility(View.GONE);
            printerconnetlayoutBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        printerconnetlayoutBinding.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        // editphysicalbatchdialogBinding.subtitle.setText(subtitle);
    }

    public void setPositiveLabel(String positive) {
        printerconnetlayoutBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        printerconnetlayoutBinding.dialogButtonNO.setText(negative);
    }
}
