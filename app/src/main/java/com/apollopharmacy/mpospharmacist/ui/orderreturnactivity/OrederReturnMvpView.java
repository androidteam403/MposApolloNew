package com.apollopharmacy.mpospharmacist.ui.orderreturnactivity;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface OrederReturnMvpView extends MvpView {

    void onClickActionBarBack();

    void isCorpAllowedReturn(boolean isAllowed);

    void setTransactionId(String transactionId);

    void showInfoPopup(String title, String message, boolean isCancelOrder, boolean isReturnAll);

    void showCancelOrderSuccess(String title, String message);

    void partialReturnOrder();
}
