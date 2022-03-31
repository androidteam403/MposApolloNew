package com.apollopharmacy.mpospharmacistTest.ui.additem;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.MaunalDiscDialogBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.adapter.CategoryDiscAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.ManualDiscCheckRes;

import java.util.ArrayList;
import java.util.List;

public class ManualDiscDialog {

    private Dialog dialog;
    private Context context;
    private MaunalDiscDialogBinding maunalDiscDialogBinding;

    private boolean negativeExist = false;
    private CategoryDiscAdapter categoryDiscAdapter;
    private ArrayList<ManualDiscCheckRes.DisplayList> displayListArrayList = new ArrayList<>();

    public ManualDiscDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        maunalDiscDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.maunal_disc_dialog, null, false);
        dialog.setContentView(maunalDiscDialogBinding.getRoot());
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        setUp();
    }

    private void setUp() {
        categoryDiscAdapter = new CategoryDiscAdapter(context, displayListArrayList);
        RecyclerView.LayoutManager mLayoutManagerOne = new LinearLayoutManager(context);
        maunalDiscDialogBinding.categoryRecyclerView.setLayoutManager(mLayoutManagerOne);
        maunalDiscDialogBinding.categoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        maunalDiscDialogBinding.categoryRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        maunalDiscDialogBinding.categoryRecyclerView.setAdapter(categoryDiscAdapter);
    }

    public void setCategoryDisplayList(List<ManualDiscCheckRes.DisplayList> displayList) {
        displayListArrayList.addAll(displayList);
        categoryDiscAdapter.notifyDataSetChanged();
    }

    public void setAvailDiscountList(List<ManualDiscCheckRes.AvailableDiscList> availDiscountList) {
        ArrayList<String> offersList = new ArrayList<>();
        for (int i = 0; i < availDiscountList.size(); i++) {
            if (!offersList.contains(availDiscountList.get(i).getOfferId())) {
                offersList.add(availDiscountList.get(i).getOfferId());
                RadioButton button = new RadioButton(context);
                button.setId(i);
                button.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/roboto_regular.ttf"));
                button.setTag(availDiscountList.get(i));
                button.setText(availDiscountList.get(i).getDiscountName());
                if (i == 0) {
                    button.setChecked(true);
                    checkIsOtpRequired(availDiscountList.get(i));
                } else {
                    button.setChecked(false);
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((RadioGroup) view.getParent()).check(view.getId());
                        if (view.getTag() != null) {
                            ManualDiscCheckRes.AvailableDiscList discList = (ManualDiscCheckRes.AvailableDiscList) view.getTag();
                            checkIsOtpRequired(discList);
                        }
                    }
                });
                maunalDiscDialogBinding.hourRadioGroup.addView(button);
            }
        }
    }

    private String fixedDiscountCode;

    private void checkIsOtpRequired(ManualDiscCheckRes.AvailableDiscList availableDiscList) {
        fixedDiscountCode = availableDiscList.getOfferId();
        if (availableDiscList.getIsOTPRequired() == 1) {
            maunalDiscDialogBinding.dialogGenerateBtn.setVisibility(View.VISIBLE);
            maunalDiscDialogBinding.dialogApplyDisc.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogValidateBtn.setVisibility(View.GONE);
        } else {
            maunalDiscDialogBinding.dialogApplyDisc.setVisibility(View.VISIBLE);
            maunalDiscDialogBinding.dialogValidateBtn.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogGenerateBtn.setVisibility(View.GONE);
        }
    }

    private String OTP;

    public void visibleValidateOtp(String otp) {
        if (!TextUtils.isEmpty(otp)) {
            OTP = otp;
            maunalDiscDialogBinding.additionalDiscOtpEditText.setVisibility(View.VISIBLE);
            maunalDiscDialogBinding.dialogGenerateBtn.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogApplyDisc.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogValidateBtn.setVisibility(View.VISIBLE);
        } else {
            maunalDiscDialogBinding.additionalDiscOtpEditText.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogApplyDisc.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogValidateBtn.setVisibility(View.GONE);
            maunalDiscDialogBinding.dialogGenerateBtn.setVisibility(View.VISIBLE);
        }
    }

    public boolean isValidateOTP() {
        if (OTP.equalsIgnoreCase(getOTPFieldData())) {
            return true;
        } else {
            maunalDiscDialogBinding.additionalDiscOtpEditText.setError("Enter Valid OTP");
            return false;
        }
    }

    private String getOTPFieldData() {
        return maunalDiscDialogBinding.additionalDiscOtpEditText.getText().toString();
    }

    public void setApplyDiscListener(View.OnClickListener okListener) {
        maunalDiscDialogBinding.dialogApplyDisc.setOnClickListener(okListener);
    }

    public void setCancelListener(View.OnClickListener okListener) {
        maunalDiscDialogBinding.dialogButtonNO.setOnClickListener(okListener);
    }

    public void setGenerateOTPListener(View.OnClickListener otpListener) {
        maunalDiscDialogBinding.dialogGenerateBtn.setOnClickListener(otpListener);
    }

    public void setValidateOTPListener(View.OnClickListener otpListener) {
        maunalDiscDialogBinding.dialogValidateBtn.setOnClickListener(otpListener);
    }

    public ArrayList<ManualDiscCheckRes.DisplayList> getDisplayListArrayList() {
        return displayListArrayList;
    }

    public String getFixedDiscountCode() {
        return fixedDiscountCode;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }


}
