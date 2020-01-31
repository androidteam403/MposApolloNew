package com.apollo.pharmacy.ui.adduser;

import com.apollo.pharmacy.ui.base.MvpPresenter;
import com.apollo.pharmacy.ui.searchuser.SearchUserMvpView;

public interface AddUserMvpPresenter<V extends AddUserMvpView> extends MvpPresenter<V> {

    void onDateClick();
}
