package com.apollopharmacy.mpospharmacistTest.ui.pbas.selectappflow;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;

public interface SelectAppFlowMvpView extends MvpView {
    void onClickContinue();

    void onClickSelectAppFlowItem(int pos);

    void onClickLogout();
}
