package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogFeedbackformBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSmspayPaymentBinding;

public class FeedBackDialog
{
    private Dialog dialog;
    private Context context;
    private DialogFeedbackformBinding feedbackformBinding;
    public FeedBackDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        feedbackformBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_feedbackform, null, false);
        dialog.setContentView(feedbackformBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
    }

    public  void setUp()
    {

    }
    public  void setorderid(String orderid)
    {
        feedbackformBinding.receiptidText.setText(orderid);
    }
    public  void setmobilenumber(String mobilenumber)
    {
        feedbackformBinding.mobilenumber.setText(mobilenumber);
    }

    public void setCloseListener(View.OnClickListener okListener) {
        feedbackformBinding.dialogCloseBtn.setOnClickListener(okListener);
    }

    public void setSubmitbutton(View.OnClickListener okListener) {
        feedbackformBinding.submitButton.setOnClickListener(okListener);
    }

    public void setexcellentlayoutLostener(View.OnClickListener okListener) {
        feedbackformBinding.excellentLayout.setOnClickListener(okListener);
    }
    public void setverygoodlayoutLostener(View.OnClickListener okListener) {
        feedbackformBinding.verygoodLayout.setOnClickListener(okListener);
    }

    public void setgoodlayoutLostener(View.OnClickListener okListener) {
        feedbackformBinding.goodLayout.setOnClickListener(okListener);
    }
    public void setsatisfactionlayoutLostener(View.OnClickListener okListener) {
        feedbackformBinding.satisfactionLayout.setOnClickListener(okListener);
    }
    public void setpoorlayoutLostener(View.OnClickListener okListener) {
        feedbackformBinding.poorLayout.setOnClickListener(okListener);
    }
    public void dismiss() {
        dialog.dismiss();
    }

    public String getexcelentviewrating()
    {
        String rating="5";
        feedbackformBinding.excellentLayout.setAlpha(1.0f);
        feedbackformBinding.verygoodLayout.setAlpha(0.3f);
        feedbackformBinding.goodLayout.setAlpha(0.3f);
        feedbackformBinding.satisfactionLayout.setAlpha(0.3f);
        feedbackformBinding.poorLayout.setAlpha(0.3f);
        return  rating;
    }
    public  String getverygoodviewrating()
    {
        String rating="4";

        feedbackformBinding.excellentLayout.setAlpha(0.3f);
        feedbackformBinding.verygoodLayout.setAlpha(1.0f);
        feedbackformBinding.goodLayout.setAlpha(0.3f);
        feedbackformBinding.satisfactionLayout.setAlpha(0.3f);
        feedbackformBinding.poorLayout.setAlpha(0.3f);
        return  rating;
    }
    public  String getgoodviewrating()
    {
        String rating="3";
        feedbackformBinding.excellentLayout.setAlpha(0.3f);
        feedbackformBinding.verygoodLayout.setAlpha(0.3f);
        feedbackformBinding.goodLayout.setAlpha(1.0f);
        feedbackformBinding.satisfactionLayout.setAlpha(0.3f);
        feedbackformBinding.poorLayout.setAlpha(0.3f);
        return  rating;
    }
    public  String getsatisfactionviewrating()
    {
        String rating="2";
        feedbackformBinding.excellentLayout.setAlpha(0.3f);
        feedbackformBinding.verygoodLayout.setAlpha(0.3f);
        feedbackformBinding.goodLayout.setAlpha(0.3f);
        feedbackformBinding.satisfactionLayout.setAlpha(1.0f);
        feedbackformBinding.poorLayout.setAlpha(0.3f);
        return  rating;
    }
    public  String getpoorviewrating()
    {
        String rating="1";
        feedbackformBinding.excellentLayout.setAlpha(0.3f);
        feedbackformBinding.verygoodLayout.setAlpha(0.3f);
        feedbackformBinding.goodLayout.setAlpha(0.3f);
        feedbackformBinding.satisfactionLayout.setAlpha(0.3f);
        feedbackformBinding.poorLayout.setAlpha(1.0f);
        return  rating;
    }
    public  String getcommenttext()
    {
        return feedbackformBinding.commentEditText.getText().toString() ;
    }
    public  String getorderid()
    {
        return feedbackformBinding.receiptidText.getText().toString();
    }

    public void show() {
        dialog.show();
    }

}
