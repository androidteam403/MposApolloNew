package com.apollo.pharmacy.ui.storesetup;

import com.apollo.pharmacy.ui.adduser.AddUserMvpView;
import com.apollo.pharmacy.ui.base.MvpPresenter;
import com.apollo.pharmacy.ui.base.MvpView;

public interface StoreSetupMvpPresenter <V extends StoreSetupMvpView> extends MvpPresenter<V> {
    void onClickSelectStore();

}
