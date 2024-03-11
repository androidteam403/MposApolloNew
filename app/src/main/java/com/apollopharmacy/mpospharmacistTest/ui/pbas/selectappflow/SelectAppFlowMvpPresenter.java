package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.util.List;

public interface SelectAppFlowMvpPresenter<V extends SelectAppFlowMvpView> extends MvpPresenter<V> {

    void onClickContinue();

    void onClickLogout();

    List<UserModel._DropdownValueBean> getLoginUserResult();

    String getUserId();
}
