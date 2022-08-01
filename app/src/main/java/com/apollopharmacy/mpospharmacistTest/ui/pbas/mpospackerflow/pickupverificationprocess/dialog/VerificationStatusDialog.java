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
import com.apollopharmacy.mpospharmacistTest.databinding.DialogVerificationStatusPBinding;


public class VerificationStatusDialog {

    private Dialog dialog;
    private DialogVerificationStatusPBinding verificationStatusBinding;

    private boolean negativeExist = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public VerificationStatusDialog(Context context, boolean reverification, String fullfillmentId) {
        dialog = new Dialog(context);
        verificationStatusBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_verification_status_p, null, false);
        dialog.setContentView(verificationStatusBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        if (reverification) {

            verificationStatusBinding.title.setText("Push to Re-verification");
            verificationStatusBinding.dialogMessage.setText("Packer not verified for\n Fulfilment ID :" + fullfillmentId + "\n Push to Picker");
            verificationStatusBinding.statusImage.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.red)));
            verificationStatusBinding.statusImage.setImageResource(R.drawable.delete_white_icon);
        } else {
            verificationStatusBinding.title.setText("Push to Billing");
            verificationStatusBinding.dialogMessage.setText("Packer verified for\n Fulfilment ID : " + fullfillmentId + "\n Push to billing");
        }
//        verificationStatusBinding.fullfilmentId.setText(fullfillmentId);
    }


    public void setPositiveListener(View.OnClickListener okListener) {
        verificationStatusBinding.dialogButtonOK.setOnClickListener(okListener);
    }


    public void setNegativeListener(View.OnClickListener okListener) {
        verificationStatusBinding.dialogButtonNO.setOnClickListener(okListener);
//        verificationStatusBinding.dialogButtonNot.setOnClickListener(okListener);
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void show() {

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        verificationStatusBinding.title.setText(title);
    }


}
