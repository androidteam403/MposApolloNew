package com.apollopharmacy.mpospharmacist.ui.home.ui.orders;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface OrdersMvpView extends MvpView {

    void onReturnClick();

    void onCancelCLick();

    void onReOrderClick();

    void onItemClick();

}
