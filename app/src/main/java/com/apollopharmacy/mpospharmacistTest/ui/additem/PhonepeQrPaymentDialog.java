package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogPhonepeqrcodePaymentBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.WalletServiceReq;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.utils.DecimalDigitsInputFilter;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp.getContext;

public class PhonepeQrPaymentDialog extends BaseActivity {


    private Dialog dialog;
    private Context context;
    private DialogPhonepeqrcodePaymentBinding dialogPhonepeqrcodePaymentBinding;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    String providerReferenceId = null;

    public PhonepeQrPaymentDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dialogPhonepeqrcodePaymentBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_phonepeqrcode_payment, null, false);
        dialog.setContentView(dialogPhonepeqrcodePaymentBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
        setDialogDismiss();
    }

    public void setUp() {
        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(100, 2)});
        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                String text = arg0.toString();
                if (arg0.length() <= 1) {
                    if (text.contains(".") && text.indexOf(".") == 0) {
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setText(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().toString().replace(".", ""));
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setSelection(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().length());
                    }
                } else {
                    if (text.contains(".") &&
                            text.indexOf(".") != text.length() - 1 &&
                            String.valueOf(text.charAt(text.length() - 1)).equals(".")) {
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setSelection(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().length());
                    }
                    if (text.contains(".") && text.substring(text.indexOf(".") + 1).length() > 2) {
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setText(text.substring(0, text.length() - 1));
                        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setSelection(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable str) {
            }
        });
    }

    public void setGenerateQrCodeDisable() {
        dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setEnabled(false);
        dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setAlpha(0.5f);

    }

    public void setGenerateQrCodeEnable() {
        dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setEnabled(true);
        dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setAlpha(1);
    }

    public void setValidatePaymentDisable() {
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setEnabled(false);
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setAlpha(0.5f);
    }

    public void setValidatePaymentEnable() {
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setEnabled(true);
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setAlpha(1);
    }

    public void setCancelPaymentDisable() {
        dialogPhonepeqrcodePaymentBinding.dialogCancelQrcodeBtn.setEnabled(false);
        dialogPhonepeqrcodePaymentBinding.dialogCancelQrcodeBtn.setAlpha(0.5f);
    }

    public void setCancelPaymentEnable() {
        dialogPhonepeqrcodePaymentBinding.dialogCancelQrcodeBtn.setEnabled(true);
        dialogPhonepeqrcodePaymentBinding.dialogCancelQrcodeBtn.setAlpha(1);
    }

    public void setCloseBtnDisable() {
        dialogPhonepeqrcodePaymentBinding.dialogCloseBtn.setEnabled(false);
        dialogPhonepeqrcodePaymentBinding.dialogCloseBtn.setAlpha(0.5f);
    }

    public void setCloseBtnEnable() {
        dialogPhonepeqrcodePaymentBinding.dialogCloseBtn.setEnabled(true);
        dialogPhonepeqrcodePaymentBinding.dialogCloseBtn.setAlpha(1);
    }

    public boolean isValidateAmount(WalletServiceReq walletServiceReq) {
        if (TextUtils.isEmpty(dialogPhonepeqrcodePaymentBinding.walletMobileNumber.getText().toString())) {
            dialogPhonepeqrcodePaymentBinding.walletMobileNumber.setError("Enter Mobile Number");
            return false;
        } else if (dialogPhonepeqrcodePaymentBinding.walletMobileNumber.getText().toString().length() < 10) {
            dialogPhonepeqrcodePaymentBinding.walletMobileNumber.setError("Enter valid Mobile Number");
            return false;
        }
        if (TextUtils.isEmpty(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().toString())) {
            dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setError("Enter Amount");
            return false;
        }

        return true;
    }


    public String getWalletMobileNumber() {
        return dialogPhonepeqrcodePaymentBinding.walletMobileNumber.getText().toString();
    }

    public double getWalletAmount() {
        return Double.parseDouble(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().toString());
    }

    public void ValidatePaymentStatussetenabled() {
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setEnabled(true);
    }

    public void ValidatePaymentStatussetdisabled() {
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setEnabled(false);
    }

    public void setproviderReferenceId(String providerReferenceId1) {
        providerReferenceId = providerReferenceId1;
    }

    public String getProviderReferenceId() {
        return providerReferenceId;
    }

    public void setCloseListener(View.OnClickListener okListener) {
        dialogPhonepeqrcodePaymentBinding.dialogCloseBtn.setOnClickListener(okListener);
    }

    public void setCancelListener(View.OnClickListener okListener) {
        dialogPhonepeqrcodePaymentBinding.dialogCancelQrcodeBtn.setOnClickListener(okListener);
    }

    public void setGenerateQrcodeListner(View.OnClickListener otpListener) {
        dialogPhonepeqrcodePaymentBinding.dialogGenerateQrcodeBtn.setOnClickListener(otpListener);
    }

    public void setValidatePaymentStatusListner(View.OnClickListener otpListener) {
        dialogPhonepeqrcodePaymentBinding.dialogButtonValidatePaymentstatus.setOnClickListener(otpListener);
    }

    public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        dialogPhonepeqrcodePaymentBinding.setOrder(posTransaction);
        dialogPhonepeqrcodePaymentBinding.walletMobileNumber.setSelection(dialogPhonepeqrcodePaymentBinding.walletMobileNumber.getText().length());
        dialogPhonepeqrcodePaymentBinding.walletAmountEdit.setSelection(dialogPhonepeqrcodePaymentBinding.walletAmountEdit.getText().length());
    }

    public void setPaymentMethod(PaymentMethodModel paymentMethod) {
        dialogPhonepeqrcodePaymentBinding.setPayment(paymentMethod);
    }

    public void setQrcodeimage(String Qrcodestring) {
        if (Qrcodestring.equalsIgnoreCase("REMOVE_QR_CODE")) {
            dialogPhonepeqrcodePaymentBinding.QrcodeID.setImageDrawable(context.getResources().getDrawable(R.drawable.bg_white));
        } else {
            Log.d("phonepe dialog-->", Qrcodestring);
            // below line is for getting
            DisplayMetrics metrics = new DisplayMetrics();
            ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getMetrics(metrics);
            float widthDpi = metrics.xdpi;
            float heightDpi = metrics.ydpi;

            int widthPixels = metrics.widthPixels;
            int heightPixels = metrics.heightPixels;

            // generating dimension from width and height.
            int dimen = widthPixels < heightPixels ? widthPixels : heightPixels;
            dimen = dimen * 3 / 4;

            // setting this dimensions inside our qr code
            // encoder to generate our qr code.
            qrgEncoder = new QRGEncoder(Qrcodestring, null, QRGContents.Type.TEXT, dimen);
            try {
                // getting our qrcode in the form of bitmap.
                bitmap = qrgEncoder.encodeAsBitmap();
                // the bitmap is set inside our image
                // view using .setimagebitmap method.
                dialogPhonepeqrcodePaymentBinding.QrcodeID.setImageBitmap(bitmap);
            } catch (WriterException e) {
                // this method is called for
                // exception handling.
                Log.e("Tag", e.toString());
            }
        }
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }


    public void setDialogDismiss() {
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
    }
}


