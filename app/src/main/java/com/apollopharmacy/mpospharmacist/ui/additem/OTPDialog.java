package com.apollopharmacy.mpospharmacist.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.DialogOtpViewBinding;

public class OTPDialog {

    private Dialog dialog;
    private DialogOtpViewBinding dialogOtpViewBinding;

    private boolean negativeExist = false;
    private String otp;

    public OTPDialog(Context context) {
        dialog = new Dialog(context);
        dialogOtpViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_otp_view, null, false);
        dialog.setContentView(dialogOtpViewBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    public boolean validateOTP() {
        if (!TextUtils.isEmpty(dialogOtpViewBinding.otpEditText.getText().toString())) {
            if (!otp.equalsIgnoreCase(dialogOtpViewBinding.otpEditText.getText().toString())) {
                dialogOtpViewBinding.otpEditText.setError("Please Enter Valid OTP");
                return false;
            } else {
                return true;
            }
        } else {
            dialogOtpViewBinding.otpEditText.setError("Please Enter OTP");
            return false;
        }
    }

    public void setPositiveListener(View.OnClickListener okListener) {
        dialogOtpViewBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        dialogOtpViewBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            dialogOtpViewBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            dialogOtpViewBinding.separator.setVisibility(View.VISIBLE);
        } else {
            dialogOtpViewBinding.dialogButtonNO.setVisibility(View.GONE);
            dialogOtpViewBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        dialogOtpViewBinding.title.setText(title);
    }

    public void setPositiveLabel(String positive) {
        dialogOtpViewBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        dialogOtpViewBinding.dialogButtonNO.setText(negative);
    }

    public void setOTP(String otp) {
        this.otp = otp;
    }

}
