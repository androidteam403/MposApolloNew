package com.apollopharmacy.mpospharmacist.ui.additem;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.ManualDiscCheckRes;
import com.apollopharmacy.mpospharmacist.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacist.ui.additem.model.SalesLineEntity;
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

    void onSuccessClearAll();

    void partialPaymentDialog();

    void onPayButtonClick();

    Context getContext();

    String getCashPaymentAmount();

    String getCreditPaymentAmount();

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

    ArrayList<SalesLineEntity> getSelectedProducts();

    CalculatePosTransactionRes getCalculatedPosTransactionRes();

    void setErrorCardPaymentAmountEditText(String message);

    void setErrorCashPaymentAmountEditText(String message);

    void setErrorCreditPaymentAmountEditText(String message);

    void setErrorOneApolloPointsEditText(String message);

    void setErrorOneApolloOtpEditText(String message);

    void onClickCardPaymentBtn();

    void onClickCashPaymentBtn();

    void onClickOneApolloBtn();

    void onClickWalletPaymentBtn();

    void onClickCreditPaymentBtn();

    void onClickEditItemsList();

    void onSuccessGenerateTenderLine(GenerateTenderLineRes body);

    void onFailedGenerateTenderLine(GenerateTenderLineRes body);

    void onSuccessSaveRetailTransaction(SaveRetailsTransactionRes body);

    void onFailedSaveRetailsTransaction(SaveRetailsTransactionRes body);

    void onSuccessValidateOneApolloPoints(ValidatePointsResModel body);

    ValidatePointsResModel.OneApolloProcessResultEntity onApolloPointsAvailablePoints();

    void onFailedValidateOneApolloPoints(ValidatePointsResModel body);

    void onSuccessCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes);

    void onFailedCalculatePosTransaction(CalculatePosTransactionRes posTransactionRes);

    void onSuccessOneApolloSendOtp(ValidatePointsResModel.OneApolloProcessResultEntity resultEntity);

    void onSuccessOneApolloOtp(ValidatePointsResModel.OneApolloProcessResultEntity entity);

    void isManualDisc(boolean isManualDisc);

    void onItemDeleted(int lineNumber);

    void onItemAdded(int lineNumber);

    void onItemEdit(SalesLineEntity item);

    void onClickGenerateBill();

    boolean isDonePayment();

    double orderTotalAmount();

    double orderRemainingAmount();

    void updatePayedAmount(CalculatePosTransactionRes posTransactionRes);

    void toRemovePayedAmount(int position);

    void openManualDiscDialog(ManualDiscCheckRes body);

    void errorMessageDialog(String title,String message);

    void generateOTPResponseSuccess(String otp);

    void clearOTPVIew();

    void showDoctorSelectError();

    void closeOrderSuccess();

}
