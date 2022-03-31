package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSmspayPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.utils.DecimalDigitsInputFilter;

public class SmsPaymentDialog
{
    private Dialog dialog;
    private Context context;
    private DialogSmspayPaymentBinding smspayPaymentBinding;

    public SmsPaymentDialog(Context context) {

        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        smspayPaymentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_smspay_payment, null, false);
        dialog.setContentView(smspayPaymentBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
      //  setDialogDismiss();

    }

    private void setUp() {
        smspayPaymentBinding.walletAmountEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 2)});
        smspayPaymentBinding.walletAmountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                String text = arg0.toString();
                if (arg0.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        smspayPaymentBinding.walletAmountEdit.setText(smspayPaymentBinding.walletAmountEdit.getText().toString().replace(".", ""));
                        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
                    }
                } else {
                    if (text.contains(".") &&
                            text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        smspayPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        smspayPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable str) {
            }
        });
    }

    public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        smspayPaymentBinding.setOrder(posTransaction);
        smspayPaymentBinding.walletMobileNumber.setSelection(smspayPaymentBinding.walletMobileNumber.getText().length());
        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
    }


    public String getWalletMobileNumber() {
        return smspayPaymentBinding.walletMobileNumber.getText().toString();
    }

    public String getWalletAmount() {
        return String.valueOf(Double.parseDouble(smspayPaymentBinding.walletAmountEdit.getText().toString()));
    }

    public String getTransactionId() {
        return smspayPaymentBinding.transactionid.getText().toString();
    }

    public void setPaymentMethod(PaymentMethodModel paymentMethod) {
        smspayPaymentBinding.setPayment(paymentMethod);
    }

    public void setCloseListener(View.OnClickListener okListener) {
        smspayPaymentBinding.dialogCloseBtn.setOnClickListener(okListener);
    }
    public void setGenerateLinkListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogGenerateBtn.setOnClickListener(otpListener);
    }
    public void setValidateLinkListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogValidateBtn.setOnClickListener(otpListener);
    }

    public void setCanceltransactionListener(View.OnClickListener otpListener) {
        smspayPaymentBinding.dialogCancelBtn.setOnClickListener(otpListener);
    }


    public boolean isValidateAmount() {
        if (TextUtils.isEmpty(smspayPaymentBinding.walletMobileNumber.getText().toString())) {
            smspayPaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (smspayPaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            smspayPaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(smspayPaymentBinding.walletAmountEdit.getText().toString())) {
            smspayPaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        }

        return true;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }
}
