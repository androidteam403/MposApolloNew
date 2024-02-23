package com.apollopharmacy.mpospharmacistTest.ui.pbas.orderreturnactivity;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;

public interface OrederReturnMvpView extends MvpView {

    void onClickActionBarBack();

    void isCorpAllowedReturn(boolean isAllowed);

    void setTransactionId(String transactionId, boolean isModifiedTransactionId);

    void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll, String terminalId);

    void showCancelOrderSuccess(String title, String message, boolean isShowPdf, CalculatePosTransactionRes calculatePosTransactionRes);

    void partialReturnOrder(String terminalId);

    void onAlreadyItemReturnedColor();

    CalculatePosTransactionRes calculations();

    void onSucessPlayList();

    Context getContext();

    void Feedbackfromdialog();

    void onClickBillReprint();

    void onSuccessBillPrintResponse(PdfModelResponse body, boolean isModifiedTransactionId);

    void onFailureBillPrintResponse(PdfModelResponse body);

}
