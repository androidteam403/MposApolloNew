package com.apollopharmacy.mpospharmacistTest.ui.pbas.mpospackerflow.pickupverificationprocess.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;


import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCustomUpdateStatusPBinding;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;

public class UpdateStatusDialog {

    private Dialog dialog;
    private DialogCustomUpdateStatusPBinding dialogUpdateStatusBinding;

    private boolean negativeExist = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public UpdateStatusDialog(Context context, RackAdapter.RackBoxModel.ProductData pickPackProductsData) {
        dialog = new Dialog(context);
        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogUpdateStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_custom_update_status_p, null, false);
        dialog.setContentView(dialogUpdateStatusBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        if (pickPackProductsData != null) {
            dialogUpdateStatusBinding.title.setText(pickPackProductsData.getProductName());
            dialogUpdateStatusBinding.qty.setText(pickPackProductsData.getCapturedQuantity());
        }
        dialogUpdateStatusBinding.fullStatusColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogUpdateStatusBinding.fullStatusColor.isChecked()) {
                    dialogUpdateStatusBinding.fullStatusColor.setChecked(true);
                    dialogUpdateStatusBinding.fullStatusColor.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.black)));
                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
                }
            }
        });
        dialogUpdateStatusBinding.partialStatusColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogUpdateStatusBinding.partialStatusColor.isChecked()) {
                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
                    dialogUpdateStatusBinding.partialStatusColor.setChecked(true);
                    dialogUpdateStatusBinding.partialStatusColor.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.black)));
                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(false);
                }
            }
        });
        dialogUpdateStatusBinding.notAvailableStatusColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogUpdateStatusBinding.notAvailableStatusColor.isChecked()) {
                    dialogUpdateStatusBinding.fullStatusColor.setChecked(false);
                    dialogUpdateStatusBinding.partialStatusColor.setChecked(false);
                    dialogUpdateStatusBinding.notAvailableStatusColor.setChecked(true);
                    dialogUpdateStatusBinding.notAvailableStatusColor.setButtonTintList(ColorStateList.valueOf(context.getColor(R.color.black)));
                }
            }
        });

    }


    public void setPositiveListener(View.OnClickListener okListener) {
        dialogUpdateStatusBinding.dialogButtonOK.setOnClickListener(okListener);
    }


    public void setNegativeListener(View.OnClickListener okListener) {
        dialogUpdateStatusBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        dialogUpdateStatusBinding.title.setText(title);
    }


//    public void setPositiveLabel(String positive) {
//        alertDialogBoxBinding.btnYes.setText(positive);
//    }
//
//    public void setNegativeLabel(String negative) {
//        negativeExist = true;
//        alertDialogBoxBinding.btnNo.setText(negative);
//    }


}
