package com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogSmspayPaymentBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DilogCustomertypeFilterBinding;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.adapter.CustomertypeFilter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.CustomerTypeModel;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.adapter.GetStoresListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;
import com.apollopharmacy.mpospharmacistTest.utils.Constant;
import com.apollopharmacy.mpospharmacistTest.utils.DecimalDigitsInputFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CustomertypeFilterDialog {

    private Dialog dialog;
    private Context context;
    private DilogCustomertypeFilterBinding dilogCustomertypeFilterBinding;
    EprescriptionsListMvpPresenter<EprescriptionsListMvpView> mpresenter;
    EprescriptionsListMvpView eprescriptionsListMvpView;
    CustomertypeFilter customertypeFilter;

    private ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> customertypeArrList = new ArrayList<>();


    public CustomertypeFilterDialog(Context context) {

        this.context = context;
        dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        dilogCustomertypeFilterBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dilog_customertype_filter, null, false);
        dialog.setContentView(dilogCustomertypeFilterBinding.getRoot());
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setUp();
        //  setDialogDismiss();

    }

    public void setcustomertypeListArray(ArrayList<OMSTransactionHeaderResModel.OMSHeaderObj> customertypeArrList) {
        //this.customertypeArrList = customertypeArrList;
        this.customertypeArrList.clear();
       // Constant.getInstance().Customertypearraylist.clear();
        ArrayList<CustomerTypeModel> customertypearray=new ArrayList<>();

        ArrayList<String> customertype=new ArrayList<>();

        for(OMSTransactionHeaderResModel.OMSHeaderObj item :customertypeArrList)
        {
            if(item.getCustomerType().length() > 0)
            {
                customertype.add(item.getCustomerType());
            }

        }
        ArrayList<String> newcustomertypeList = new ArrayList<String>(new HashSet<String>(customertype));
        if(newcustomertypeList.size() > 0)
        {
            CustomerTypeModel customerTypeModel1=new CustomerTypeModel();
            customerTypeModel1.setCustomertype("All");
            customerTypeModel1.setCheckstatus(false);
            customertypearray.add(customerTypeModel1);
            for(String customertype1:newcustomertypeList)
            {
                CustomerTypeModel customerTypeModel=new CustomerTypeModel();
                customerTypeModel.setCustomertype(customertype1);
                customerTypeModel.setCheckstatus(false);
                customertypearray.add(customerTypeModel);
            }
        }


        customertypeFilter = new CustomertypeFilter(context,  customertypearray,mpresenter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        dilogCustomertypeFilterBinding.customertypeFiltersview.setLayoutManager(mLayoutManager);
        customertypeFilter.onClickListener(eprescriptionsListMvpView);
        dilogCustomertypeFilterBinding.customertypeFiltersview.setAdapter(customertypeFilter);
      //  customertypeFilter.notifyDataSetChanged();


    }

    private void setUp() {



    }

    public void setCloseListener(View.OnClickListener okListener) {
        dilogCustomertypeFilterBinding.dialogButtonCancel.setOnClickListener(okListener);
       // dilogCustomertypeFilterBinding.dialogfiltersapplyBtn.setOnClickListener(okListener);
    }


    public void setApplyListener(View.OnClickListener otpListener) {
        dilogCustomertypeFilterBinding.dialogfiltersapplyBtn.setOnClickListener(otpListener);
    }
    /*public void setCalculatedPosTransaction(CalculatePosTransactionRes posTransaction) {
        smspayPaymentBinding.setOrder(posTransaction);
        smspayPaymentBinding.walletMobileNumber.setSelection(smspayPaymentBinding.walletMobileNumber.getText().length());
        smspayPaymentBinding.walletAmountEdit.setSelection(smspayPaymentBinding.walletAmountEdit.getText().length());
    }*/


   /* public String getWalletMobileNumber() {
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
*/
    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }
}
