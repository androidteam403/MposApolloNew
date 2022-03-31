package com.apollopharmacy.mpospharmacistTest.ui.pbas.main;


import com.apollopharmacy.mpospharmacistTest.ui.base.MvpPresenter;

/**
 * Created on : Nov 02, 2021
 * Author     : NAVEEN
 */
public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void onViewPrepared();
}
