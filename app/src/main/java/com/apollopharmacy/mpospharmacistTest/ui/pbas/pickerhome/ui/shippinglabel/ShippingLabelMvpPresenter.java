package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

import java.io.File;

public interface ShippingLabelMvpPresenter<V extends ShippingLabelMvpView> extends MvpPresenter<V> {

    void getJounalOnlineOrderTransactionApiCall();

    void generatePdfbyFlidApiCall(String flid, String paperSize);

    void doDownloadPdf(String pdfUrl, File file);

    void setPaperLabelSize(String paperLabelSize);

    String getPaperLabelSize();

    void onClickScanCode();

    void onClickSearchTextClear();

    void onClickPrevPage();

    void onClickNextPage();

//    void pdfApiCall();

    void pdfApiCall_(String refno);
}
