package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GeneratePdfbyFlidResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;

import java.util.List;

public interface ShippingLabelMvpView extends MvpView {

    void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList);

    void onClickPrintLabel(GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse);

    void onSuccessGeneratepdfbyFlidApiCall(GeneratePdfbyFlidResponse generatePdfbyFlidResponse);

    void noShippinfLabelFound(int count);

    void showPdf();

    void onClickScanCode();

    void onClickSearchTextClear();

    void onClickPrevPage();

    void onClickNextPage();
}
