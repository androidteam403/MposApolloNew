package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogCreditPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;

public class CreditPaymentDialog {

    private Dialog dialog;
    private DialogCreditPaymentBinding dialogCreditPaymentBinding;

    private boolean negativeExist = false;
    private SalesLineEntity editItem;

    public CreditPaymentDialog(Context context) {
        dialog = new Dialog(context);
        dialogCreditPaymentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_credit_payment, null, false);
        dialog.setContentView(dialogCreditPaymentBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


    }

    public void setPaymentAmount(double creditPaymentAmount) {
        dialogCreditPaymentBinding.editQuantityEditText.setText(String.valueOf(creditPaymentAmount));
        dialogCreditPaymentBinding.editQuantityEditText.setEnabled(false);
    }

    public String getEnteredEditText() {
        return dialogCreditPaymentBinding.editQuantityEditText.getText().toString();
    }

    public boolean validateQuantity() {
        if (!TextUtils.isEmpty(dialogCreditPaymentBinding.editQuantityEditText.getText().toString())) {
            return true;
        } else {
            dialogCreditPaymentBinding.editQuantityEditText.setError("Please Enter Quantity");
            return false;
        }
    }

    public void setPositiveListener(View.OnClickListener okListener) {
        dialogCreditPaymentBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        dialogCreditPaymentBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            dialogCreditPaymentBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            dialogCreditPaymentBinding.separator.setVisibility(View.VISIBLE);
        } else {
            dialogCreditPaymentBinding.dialogButtonNO.setVisibility(View.GONE);
            dialogCreditPaymentBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        dialogCreditPaymentBinding.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        dialogCreditPaymentBinding.subtitle.setText(subtitle);
    }

    public void setBalanceAmount(String amount) {
        dialogCreditPaymentBinding.balanceAmount.setText(amount);
    }

    public void setPositiveLabel(String positive) {
        dialogCreditPaymentBinding.dialogButtonOK.setText(positive);
    }

    public void hidePositiveBtn() {
        dialogCreditPaymentBinding.dialogButtonOK.setVisibility(View.GONE);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        dialogCreditPaymentBinding.dialogButtonNO.setText(negative);
    }

    public void setInputType() {
        dialogCreditPaymentBinding.editQuantityEditText.setInputType(InputType.TYPE_CLASS_TEXT);
    }
}
