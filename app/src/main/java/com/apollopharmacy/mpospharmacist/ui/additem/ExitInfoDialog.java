package com.apollopharmacy.mpospharmacist.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ExitInfoDialogBinding;

import androidx.databinding.DataBindingUtil;

public class ExitInfoDialog {

    private Dialog dialog;
    private ExitInfoDialogBinding exitInfoDialogBinding;

    private boolean negativeExist = false;

    public ExitInfoDialog(Context context) {
        dialog = new Dialog(context);
        exitInfoDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.exit_info_dialog, null, false);
        dialog.setContentView(exitInfoDialogBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }


    public void setPositiveListener(View.OnClickListener okListener) {
        exitInfoDialogBinding.dialogButtonOK.setOnClickListener(okListener);
    }

    public void setNegativeListener(View.OnClickListener okListener) {
        exitInfoDialogBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void show() {
        if (negativeExist) {
            exitInfoDialogBinding.dialogButtonNO.setVisibility(View.VISIBLE);
            exitInfoDialogBinding.separator.setVisibility(View.VISIBLE);
        } else {
            exitInfoDialogBinding.dialogButtonNO.setVisibility(View.GONE);
            exitInfoDialogBinding.separator.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setTitle(String title) {
        exitInfoDialogBinding.title.setText(title);
    }

    public void setSubtitle(String subtitle) {
        exitInfoDialogBinding.subtitle.setText(subtitle);
    }

    public void setPositiveLabel(String positive) {
        exitInfoDialogBinding.dialogButtonOK.setText(positive);
    }

    public void setNegativeLabel(String negative) {
        negativeExist = true;
        exitInfoDialogBinding.dialogButtonNO.setText(negative);
    }

}
