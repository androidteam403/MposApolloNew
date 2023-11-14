package com.apollopharmacy.mpospharmacistTest.ui.ordersummary;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.ordersummary.model.PdfModelResponse;

public interface OrderSummaryMvpView extends MvpView {

    void onBackOrderPressed();

    void onNewPlaceOrderClicked();

    void onDownloadPdfButton();

    void onSucessPlayList();

    Context getContext();

    void onSuccessPdfResponse(PdfModelResponse body);

    void onFailurePdfResponse(PdfModelResponse body);

    void onClickBillPrint();
}
