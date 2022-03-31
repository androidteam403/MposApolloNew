package com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;

public interface GetStoresDialogMvpView extends MvpView {

    void dismissDialog();

    void onClickListener(StoreListResponseModel.StoreListObj item);
}
