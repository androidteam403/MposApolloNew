package com.apollopharmacy.mpospharmacist.ui.additem;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.pay.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.pay.model.SaveRetailsTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.model.TransactionIDResModel;
import com.apollopharmacy.mpospharmacist.ui.searchproductlistactivity.model.GetItemDetailsRes;

import java.util.ArrayList;

public interface AddItemMvpView extends MvpView {

    void onManualSearchClick();

    void onVoiceSearchClick();

    void onBarCodeSearchClick();

    void onClickActionBarBack();

    void onClearAll();

    void onPayButtonClick();

    Context getContext();

    String getCashPaymentAmount();

    String getCardPaymentAmount();

    GetCustomerResponse.CustomerEntity getCustomerModule();

    DoctorSearchResModel.DropdownValueBean getDoctorModule();

    CorporateModel.DropdownValueBean getCorporateModule();

    TransactionIDResModel getTransactionModule();

    ArrayList<GetItemDetailsRes.Items> getSelectedProducts();

    void setErrorCardPaymentAmountEditText(String message);

    void setErrorCashPaymentAmountEditText(String message);

    void onClickCardPaymentBtn();

    void onClickCashPaymentBtn();

    void onClickOneApolloBtn();

    void onClickEditItemsList();

    void onSuccessGenerateTenderLine(GenerateTenderLineRes body);

    void onFailedGenerateTenderLine(GenerateTenderLineRes body);

    void onSuccessSaveRetailTransaction(SaveRetailsTransactionRes body);

    void onFailedSaveRetailsTransaction(SaveRetailsTransactionRes body);

    void onSuccessValidateOneApolloPoints(ValidatePointsResModel body);

    void onFailedValidateOneApolloPoints(ValidatePointsResModel body);
}
