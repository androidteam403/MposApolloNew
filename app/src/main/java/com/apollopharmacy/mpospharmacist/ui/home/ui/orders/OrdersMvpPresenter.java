package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.additem.model.CalculatePosTransactionRes;
import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListReq;
import com.apollopharmacy.mpospharmacist.ui.home.ui.orders.model.OrderListRes;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrdersMvpPresenter<V extends OrdersMvpView> extends MvpPresenter<V> {
    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick(CalculatePosTransactionRes item);

    void getOrdersDetails();

    void orderServiceCall(OrderListReq orderListReq);

    void onMposTabApiCall();

    List<RowsEntity> getDataListEntity();

    void createFilePath(ResponseBody body, String fileName, boolean isFirstFile, int pos);

    void onDownloadApiCall(String filePath, String fileName, int pos);

}
