package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import com.apollopharmacy.mpospharmacist.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;

import java.util.List;

import okhttp3.ResponseBody;

public interface NewAdminLoginMvpPresenter <V extends NewAdminLoginMvpView> extends MvpPresenter<V> {

     void onAdminLoginClick();
}
