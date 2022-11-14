package com.apollopharmacy.mpospharmacistTest.ui.additem.dialog;

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
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogHdfcpayPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.utils.DecimalDigitsInputFilter;

public class HdfcPaymentDialog {
    private Dialog dialog;
    private Context context;
    private DialogHdfcpayPaymentBinding hdfcpayPaymentBinding;
    private CalculatePosTransactionRes posTransaction;
    private PaymentMethodModel paymentMethod;

    public HdfcPaymentDialog(Context context) {

        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        hdfcpayPaymentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_hdfcpay_payment, null, false);
        dialog.setContentView(hdfcpayPaymentBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
        //  setDialogDismiss();

    }

    private void setUp() {
        hdfcpayPaymentBinding.walletAmountEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 2)});
        hdfcpayPaymentBinding.walletAmountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                String text = arg0.toString();
                if (arg0.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        hdfcpayPaymentBinding.walletAmountEdit.setText(hdfcpayPaymentBinding.walletAmountEdit.getText().toString().replace(".", ""));
                        hdfcpayPaymentBinding.walletAmountEdit.setSelection(hdfcpayPaymentBinding.walletAmountEdit.getText().length());
                    }
                } else {
                    if (text.contains(".") &&
                            text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        hdfcpayPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        hdfcpayPaymentBinding.walletAmountEdit.setSelection(hdfcpayPaymentBinding.walletAmountEdit.getText().length());
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        hdfcpayPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        hdfcpayPaymentBinding.walletAmountEdit.setSelection(hdfcpayPaymentBinding.walletAmountEdit.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable str) {
            }
        });
    }

    public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        hdfcpayPaymentBinding.setOrder(posTransaction);
        this.posTransaction = posTransaction;
        hdfcpayPaymentBinding.walletMobileNumber.setSelection(hdfcpayPaymentBinding.walletMobileNumber.getText().length());
        hdfcpayPaymentBinding.walletAmountEdit.setSelection(hdfcpayPaymentBinding.walletAmountEdit.getText().length());
    }


    public String getWalletMobileNumber() {
        return hdfcpayPaymentBinding.walletMobileNumber.getText().toString();
    }

    public String getWalletAmount() {
        return String.valueOf(Double.parseDouble(hdfcpayPaymentBinding.walletAmountEdit.getText().toString()));
    }

    public String getTransactionId() {
        return hdfcpayPaymentBinding.transactionid.getText().toString();
    }

    public void setPaymentMethod(PaymentMethodModel paymentMethod) {
        this.paymentMethod = paymentMethod;
        hdfcpayPaymentBinding.setPayment(paymentMethod);
    }

    public void setCloseListener(View.OnClickListener okListener) {
        hdfcpayPaymentBinding.dialogCloseBtn.setOnClickListener(okListener);
    }

    public void setGenerateLinkListener(View.OnClickListener otpListener) {
        hdfcpayPaymentBinding.dialogGenerateBtn.setOnClickListener(otpListener);
    }

    public void setValidateLinkListener(View.OnClickListener otpListener) {
        hdfcpayPaymentBinding.dialogValidateBtn.setOnClickListener(otpListener);
    }

    public void setCanceltransactionListener(View.OnClickListener otpListener) {
        hdfcpayPaymentBinding.dialogCancelBtn.setOnClickListener(otpListener);
    }

    //dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setAlpha(0.5f);
    public void setDialogGenerateLinkBtnEnable() {
        hdfcpayPaymentBinding.dialogGenerateBtn.setEnabled(true);
        hdfcpayPaymentBinding.dialogGenerateBtn.setAlpha(1);
        hdfcpayPaymentBinding.walletMobileNumber.setEnabled(true);
    }

    public void setDialogGenerateLinkBtnDisable() {
        hdfcpayPaymentBinding.dialogGenerateBtn.setEnabled(false);
        hdfcpayPaymentBinding.dialogGenerateBtn.setAlpha(0.5f);
        hdfcpayPaymentBinding.walletMobileNumber.setEnabled(false);
    }

    public void setWalletAmountEnable() {
        hdfcpayPaymentBinding.walletAmountEdit.setEnabled(true);

    }

    public void setWalletAmountdisable() {
        hdfcpayPaymentBinding.walletAmountEdit.setEnabled(false);
    }

    public void setDialogCloseEnable() {
        hdfcpayPaymentBinding.dialogCloseBtn.setEnabled(true);
        hdfcpayPaymentBinding.dialogCloseBtn.setAlpha(1);
    }

    public void setDialogClosedisable() {
        hdfcpayPaymentBinding.dialogCloseBtn.setEnabled(false);
        hdfcpayPaymentBinding.dialogCloseBtn.setAlpha(0.5f);
    }

    public void setMobileNumberEditEnable(boolean isEnable) {
        hdfcpayPaymentBinding.walletMobileNumber.setEnabled(isEnable);
    }

    public boolean isValidateAmount() {
        if (TextUtils.isEmpty(hdfcpayPaymentBinding.walletMobileNumber.getText().toString())) {
            hdfcpayPaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (hdfcpayPaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            hdfcpayPaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(hdfcpayPaymentBinding.walletAmountEdit.getText().toString())) {
            hdfcpayPaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        } else if (Double.parseDouble(hdfcpayPaymentBinding.walletAmountEdit.getText().toString()) > this.paymentMethod.getBalanceAmount()) {
            Toast.makeText(context, "Amount should not be greater than " + this.paymentMethod.getBalanceAmount(), Toast.LENGTH_SHORT).show();
//            hdfcpayPaymentBinding.walletAmountEdit.setError("Amount should not be greater than "+ this.paymentMethod.getBalanceAmount());
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
