package com.apollopharmacy.mpospharmacist.ui.storesetup.dialog;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;

public interface GetStoresDialogMvpView extends MvpView {

    void dismissDialog();

    void onClickListener(StoreListResponseModel.StoreListObj item);
}
