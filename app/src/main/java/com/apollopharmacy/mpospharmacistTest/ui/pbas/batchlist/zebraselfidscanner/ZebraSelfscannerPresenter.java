package com.apollopharmacy.mpospharmacistTest.ui.pbas.batchlist.zebraselfidscanner;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class ZebraSelfscannerPresenter <V extends ZebraSelfscannerMvpView> extends BasePresenter<V>
    implements  ZebraSelfscannerMvpPresenter<V>
{
    @Inject
    public ZebraSelfscannerPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickClose() {
        getMvpView().onClickClose();
    }

    @Override
    public GetGlobalConfingRes getGlobalConfiguration() {
        return getDataManager().getGlobalJson();
    }
}
