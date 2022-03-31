package com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacistTest.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.orders.model.OrderListReq;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrdersMvpPresenter<V extends OrdersMvpView> extends MvpPresenter<V> {

    void onItemClick(CalculatePosTransactionRes item);

    void getOrdersDetails();

    void orderServiceCall(OrderListReq orderListReq);

    void detailorderServiceCall(OrderListReq orderListReq);

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

    boolean enablescreens();

}
