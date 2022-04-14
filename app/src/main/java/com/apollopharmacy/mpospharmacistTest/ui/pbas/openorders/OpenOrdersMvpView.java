package com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.modelclass.GetOMSTransactionResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.List;

public interface OpenOrdersMvpView extends MvpView {
    void onFullfillmentItemClick(int pos);

    void onRightArrowClickedContinue(int pos);

    void onClickContinue();

    void onSuccessRackApi(RacksDataResponse body);

    void onClickFilterIcon();

    void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader);

    void onClickScanCode();

    void onClickStausIcon(int fullFillmentPos, int pos);

    void ondownArrowClicked(int position);

    void onSucessGetOmsTransaction(List<GetOMSTransactionResponse> body);

    void onSuccessGetOmsTransactionItemClick(List<GetOMSTransactionResponse> getOMSTransactionResponseList);

    void noOrderFound(int count);

}
