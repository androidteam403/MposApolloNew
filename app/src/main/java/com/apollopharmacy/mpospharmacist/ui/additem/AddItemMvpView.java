package com.apollopharmacy.mpospharmacist.ui.additem;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ValidatePointsResModel;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacist.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GenerateTenderLineRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.PaymentMethodModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SaveRetailsTransactionRes;
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

    String getOneApolloPoints();

    String getOneApolloOtp();

    GetCustomerResponse.CustomerEntity getCustomerModule();

    DoctorSearchResModel.DropdownValueBean getDoctorModule();

    CorporateModel.DropdownValueBean getCorporateModule();

    TransactionIDResModel getTransactionModule();

    OrderPriceInfoModel getOrderPriceInfoModel();

    PaymentMethodModel getPaymentMethod();

    ValidatePointsResModel.OneApolloProcessResultEntity getValidateOneApolloPoints();

    ArrayList<GetItemDetailsRes.Items> getSelectedProducts();

    CalculatePosTransactionRes getCalculatedPosTransactionRes();

    void setErrorCardPaymentAmountEditText(String message);

    void setErrorCashPaymentAmountEditText(String message);

    void setErrorOneApolloPointsEditText(String message);

    void setErrorOneApolloOtpEditText(String message);

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

    void onSuccessCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes);

    void onFailedCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes);

    void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity);

    void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity);

    void isManualDisc(boolean isManualDisc);

    void onItemDeleted();

    void onItemAdded();

    void onItemEdit(GetItemDetailsRes.Items item);

    void onClickGenerateBill();

    boolean isDonePayment();

    double orderTotalAmount();

    void updatePayedAmount(CalculatePosTransactionRes posTransactionRes);

    void toRemovePayedAmount(double amount);

    void openManualDiscDialog(ManualDiscCheckRes body);

    void errorMessageDialog(String title,String message);

    void generateOTPResponseSuccess(String otp);
}
