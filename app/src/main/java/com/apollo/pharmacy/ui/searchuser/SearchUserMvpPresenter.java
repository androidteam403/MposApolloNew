package com.apollo.pharmacy.ui.searchuser;

import com.apollo.pharmacy.ui.base.MvpPresenter;

public interface SearchUserMvpPresenter<V extends SearchUserMvpView> extends MvpPresenter<V> {

    void onClickAdd();
}
