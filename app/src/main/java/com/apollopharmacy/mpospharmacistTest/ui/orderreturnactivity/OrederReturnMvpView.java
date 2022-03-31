package com.apollopharmacy.mpospharmacistTest.ui.orderreturnactivity;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface OrederReturnMvpView extends MvpView {

    void onClickActionBarBack();

    void isCorpAllowedReturn(boolean isAllowed);

    void setTransactionId(String transactionId);

    void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll, String terminalId);

    void showCancelOrderSuccess(String title, String message);

    void partialReturnOrder(String terminalId);

    void onAlreadyItemReturnedColor();

    CalculatePosTransactionRes calculations();

    void onSucessPlayList();

    Context getContext();

    void Feedbackfromdialog();



}
