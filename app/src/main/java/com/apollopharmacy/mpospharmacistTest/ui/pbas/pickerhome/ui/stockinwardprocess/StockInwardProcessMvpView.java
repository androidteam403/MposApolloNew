package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.stockinwardprocess;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.GetInventoryTransactionDetailsResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.stockinwardprocessdetails.model.PrsInventTransactionDetailsResponse;

public interface StockInwardProcessMvpView extends MvpView {
    void onClickFromDate();

    void onClickToDate();

    void onClickItem(int position, String getTicketId, String referenceId);

    void onSuccessgetInventoryTransactionDetails(GetInventoryTransactionDetailsResponse body);

    void onClickShow();

    void onSuccessPrsInventoryDetails(PrsInventTransactionDetailsResponse body, String type);

    void onClickShowPrStatus(String ticketId, String referenceId);
}
