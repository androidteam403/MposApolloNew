package com.apollopharmacy.mpospharmacistTest.ui.pbas.billerflow.orderdetailsscreen;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class OrderDetailsScreenPresenter <V extends OrderDetailsScreenMvpView> extends BasePresenter<V>
        implements OrderDetailsScreenMvpPresenter<V> {

    @Inject
    public OrderDetailsScreenPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onMinusCustomerDetails() {
        getMvpView().onMinusCustomerDetails();
    }

    @Override
    public void onPlusCustomerDetails() {
        getMvpView().onPlusCustomerDetails();
    }

    @Override
    public void onminusOrderDetails() {
        getMvpView().onminusOrderDetails();
    }

    @Override
    public void onplusOrderDetails() {
        getMvpView().onplusOrderDetails();
    }

    @Override
    public void onminusVendorDetails() {
        getMvpView().onminusVendorDetails();
    }

    @Override
    public void onPlusVendorDetails() {
        getMvpView().onPlusVendorDetails();
    }

    @Override
    public void onActionsContinue() {
        getMvpView().onActionsContinue();
    }

    @Override
    public void onGenerateBill() {
        getMvpView().onGenerateBill();
    }

    @Override
    public void onPrintLabel() {
        getMvpView().onPrintLabel();
    }

    @Override
    public void onPrintShippingLabel() {
        getMvpView().onPrintShippingLabel();
    }

    @Override
    public void onSendBacktoPackerLabel() {
        getMvpView().onSendBacktoPackerLabel();
    }
}

