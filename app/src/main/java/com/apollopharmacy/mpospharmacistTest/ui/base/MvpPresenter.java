package com.apollopharmacy.mpospharmacistTest.ui.base;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(Throwable error);

    void handleApiError(int code);

    void setUserAsLoggedOut();
}

