package com.apollopharmacy.mpospharmacist.ui.searchuser;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;

public interface SearchUserMvpPresenter<V extends SearchUserMvpView> extends MvpPresenter<V> {

    void onClickAdd();
}
