package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.billerOrdersScreen;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.OrderPriceInfoModel;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.SalesLineEntity;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.corporatedetails.model.CorporateModel;
import com.apollopharmacy.mpospharmacistTest.ui.customerdetails.model.GetCustomerResponse;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;

import java.util.ArrayList;

public interface BillerOrdersMvpView extends MvpView {


    void onclickScanCode();
    void onRightArrowClickedContinue(int position);
    void noOrderFound(int count);

    void onSuccessRackApi(RacksDataResponse body);
    void onSucessfullFulfilmentIdList(TransactionHeaderResponse omsHeader);
    void onClickFilterIcon();
}
