package com.apollopharmacy.mpospharmacist.ui.additem;

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

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.DialogWalletPaymentBinding;
import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.WalletServiceReq;
import com.apollopharmacy.mpospharmacist.utils.DecimalDigitsInputFilter;

public class WalletPaymentDialog {

    private Dialog dialog;
    private Context context;
    private DialogWalletPaymentBinding walletPaymentBinding;


    public WalletPaymentDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        walletPaymentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_wallet_payment, null, false);
        dialog.setContentView(walletPaymentBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
    }

    private void setUp() {
        walletPaymentBinding.walletAmountEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 2)});
        walletPaymentBinding.walletAmountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                String text = arg0.toString();
                if (arg0.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        walletPaymentBinding.walletAmountEdit.setText(walletPaymentBinding.walletAmountEdit.getText().toString().replace(".", ""));
                        walletPaymentBinding.walletAmountEdit.setSelection(walletPaymentBinding.walletAmountEdit.getText().length());
                    } else if (text.indexOf("0") == 0) {
                        walletPaymentBinding.walletAmountEdit.setText(walletPaymentBinding.walletAmountEdit.getText().toString().replace("0", ""));
                    }
                } else {
                    if (text.contains(".") && text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        walletPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        walletPaymentBinding.walletAmountEdit.setSelection(walletPaymentBinding.walletAmountEdit.getText().length());
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        walletPaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        walletPaymentBinding.walletAmountEdit.setSelection(walletPaymentBinding.walletAmountEdit.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable str) {
            }
        });
    }

    private String OTP;

    public void visibleValidateOtp(String otp) {
        if (!TextUtils.isEmpty(otp)) {
            OTP = otp;
            walletPaymentBinding.walletOtpEdit.setVisibility(View.VISIBLE);
            walletPaymentBinding.dialogGenerateBtn.setVisibility(View.GONE);
            //   walletPaymentBinding.dialogApplyDisc.setVisibility(View.GONE);
            walletPaymentBinding.dialogValidateBtn.setVisibility(View.VISIBLE);
        } else {
            walletPaymentBinding.walletOtpEdit.setVisibility(View.GONE);
            //   walletPaymentBinding.dialogApplyDisc.setVisibility(View.GONE);
            walletPaymentBinding.dialogValidateBtn.setVisibility(View.GONE);
            walletPaymentBinding.dialogGenerateBtn.setVisibility(View.VISIBLE);
        }
    }

    public boolean isValidateOTP() {
        if (OTP.equalsIgnoreCase(getOTPFieldData())) {
            return true;
        } else {
            walletPaymentBinding.walletOtpEdit.setError("Enter Valid OTP");
            return false;
        }
    }

    public boolean isValidateAmount(WalletServiceReq walletServiceReq) {
        if (TextUtils.isEmpty(walletPaymentBinding.walletMobileNumber.getText().toString())) {
            walletPaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (walletPaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            walletPaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(walletPaymentBinding.walletAmountEdit.getText().toString())) {
            walletPaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        }

        return true;
    }

    public boolean isValidateOTP(WalletServiceReq walletServiceReq) {
        if (TextUtils.isEmpty(walletPaymentBinding.walletMobileNumber.getText().toString())) {
            walletPaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (walletPaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            walletPaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(walletPaymentBinding.walletAmountEdit.getText().toString())) {
            walletPaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        }
        if (walletServiceReq.getWalletType() != 4) {
            if (TextUtils.isEmpty(walletPaymentBinding.walletOtpEdit.getText().toString())) {
                walletPaymentBinding.walletOtpEdit.setError("Enter OTP");
                return false;
            }
        }
        return true;
    }

    public String getOTPFieldData() {
        return walletPaymentBinding.walletOtpEdit.getText().toString();
    }

    public String getWalletMobileNumber() {
        return walletPaymentBinding.walletMobileNumber.getText().toString();
    }

    public double getWalletAmount() {
        return Double.parseDouble(walletPaymentBinding.walletAmountEdit.getText().toString());
    }

    public void setCancelListener(View.OnClickListener okListener) {
        walletPaymentBinding.dialogButtonCancel.setOnClickListener(okListener);
    }

    public void setCloseListener(View.OnClickListener okListener) {
        walletPaymentBinding.dialogCloseBtn.setOnClickListener(okListener);
    }

    public void setGenerateOTPListener(View.OnClickListener otpListener) {
        walletPaymentBinding.dialogGenerateBtn.setOnClickListener(otpListener);
    }

    public void setValidateOTPListener(View.OnClickListener otpListener) {
        walletPaymentBinding.dialogValidateBtn.setOnClickListener(otpListener);
    }


    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        walletPaymentBinding.title.setText(title);
    }

    public void setEnableGenerateOTP(boolean isEnableGenerate) {
        if (isEnableGenerate) {
            walletPaymentBinding.walletOtpLayout.setVisibility(View.GONE);
            walletPaymentBinding.dialogGenerateBtn.setVisibility(View.VISIBLE);
            walletPaymentBinding.dialogValidateBtn.setVisibility(View.GONE);
            walletPaymentBinding.dialogButtonCancel.setVisibility(View.GONE);
            walletPaymentBinding.dialogCloseBtn.setVisibility(View.VISIBLE);
        } else {
            walletPaymentBinding.walletOtpLayout.setVisibility(View.VISIBLE);
            walletPaymentBinding.dialogGenerateBtn.setVisibility(View.GONE);
            walletPaymentBinding.dialogValidateBtn.setVisibility(View.VISIBLE);
            walletPaymentBinding.dialogButtonCancel.setVisibility(View.GONE);
            walletPaymentBinding.dialogCloseBtn.setVisibility(View.VISIBLE);
        }
    }

    public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        walletPaymentBinding.setOrder(posTransaction);
        walletPaymentBinding.walletMobileNumber.setSelection(walletPaymentBinding.walletMobileNumber.getText().length());
        walletPaymentBinding.walletAmountEdit.setSelection(walletPaymentBinding.walletAmountEdit.getText().length());
    }

    public void setPaymentMethod(PaymentMethodModel paymentMethod) {
        walletPaymentBinding.setPayment(paymentMethod);
    }

    public void setGenerateOTPSuccess(int walletType) {
        walletPaymentBinding.dialogGenerateBtn.setVisibility(View.GONE);
        walletPaymentBinding.dialogValidateBtn.setVisibility(View.VISIBLE);
        walletPaymentBinding.dialogButtonCancel.setVisibility(View.VISIBLE);
        walletPaymentBinding.dialogCloseBtn.setVisibility(View.GONE);
        walletPaymentBinding.walletMobileNumber.setEnabled(false);
        walletPaymentBinding.walletAmountEdit.setEnabled(false);
        if (walletType != 4) {
            walletPaymentBinding.walletOtpLayout.setVisibility(View.VISIBLE);
        }
    }
}
