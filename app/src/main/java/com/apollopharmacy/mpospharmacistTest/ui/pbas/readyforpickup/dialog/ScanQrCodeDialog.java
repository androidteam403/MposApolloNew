package com.apollopharmacy.mpospharmacistTest.ui.pbas.readyforpickup.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogScanQrCodePBinding;

public class ScanQrCodeDialog {

    private Dialog dialog;
    private DialogScanQrCodePBinding dialogScanQrCodeBinding;

    private boolean negativeExist = false;

    public ScanQrCodeDialog(Context context, String id) {
        dialog = new Dialog(context);
        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialogScanQrCodeBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_scan_qr_code_p, null, false);
        dialog.setContentView(dialogScanQrCodeBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        if (id != null && !id.isEmpty()) {
            dialogScanQrCodeBinding.title.setText("Scan QR/barcode to tag box for\nFullfillment ID:" + id);
        } else {
            dialogScanQrCodeBinding.title.setText("Scan QR/barcode of the box");
        }
    }


    public void setPositiveListener(View.OnClickListener okListener) {
        dialogScanQrCodeBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setCameraClickListener(View.OnClickListener okListener) {
        dialogScanQrCodeBinding.cameraClick.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        dialogScanQrCodeBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {

        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        dialogScanQrCodeBinding.title.setText(title);
    }

    public void setCameraImage(Bitmap image) {
        dialogScanQrCodeBinding.capturesScanedImage.setImageBitmap(image);
    }

    public void visibilyHandlings() {
        dialogScanQrCodeBinding.cameraClick.setVisibility(View.GONE);
        dialogScanQrCodeBinding.capturesScanedImage.setVisibility(View.VISIBLE);
        dialogScanQrCodeBinding.dialogButtonOK.setVisibility(View.VISIBLE);
        dialogScanQrCodeBinding.text.setVisibility(View.GONE);
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
