package com.apollopharmacy.mpospharmacistTest.ui.pbas.pickerhome.ui.shippinglabel;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

import java.util.List;

public interface ShippingLabelMvpView extends MvpView {

    void onSuccessJounalTransactonApi(List<CalculatePosTransactionRes> calculatePosTransactionRes);
}
