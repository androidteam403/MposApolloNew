package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.orders;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel.model.GetJounalOnlineOrderTransactionsResponse;

import java.util.ArrayList;
import java.util.List;

public interface OrdersMvpView extends MvpView {

    void onSuccessGetJounalOnlineOrderTransactonApi(List<GetJounalOnlineOrderTransactionsResponse> getJounalOnlineOrderTransactionsResponseList);

    void noOrderFound(int count);

    void onClickItem(GetJounalOnlineOrderTransactionsResponse getJounalOnlineOrderTransactionsResponse);

    void onSuccessdetailOrderList(ArrayList<CalculatePosTransactionRes> orderListRes);

    void onClickSearchTextClear();

    void onClickScanCode();
}
