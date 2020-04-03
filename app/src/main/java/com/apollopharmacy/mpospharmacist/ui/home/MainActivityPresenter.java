package com.apollopharmacy.mpospharmacist.ui.home;

import com.apollopharmacy.mpospharmacist.BuildConfig;
import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivityPresenter<V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityMvpPresenter<V> {
    @Inject
    public MainActivityPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getLoginUserName() {
        return getDataManager().getUserName() +"\n"+ getDataManager().getUserId();
    }

    @Override
    public String getLoinStoreLocation() {
        return getDataManager().getGlobalJson().getStoreName() +"\n"+ getDataManager().getStoreId();
    }

    @Override
    public void logoutUser() {
        getDataManager().logoutUser();
        getMvpView().navigateLoginActivity();
    }

    @Override
    public void onCheckBuildDetails() {
        VendorCheckRes.BUILDDETAILSEntity buildDetailsEntity = getDataManager().getVendorRes().getBUILDDETAILS();
        if (buildDetailsEntity != null) {
            if (buildDetailsEntity.getAPPAVALIBALITY()) {
                if (Double.parseDouble(buildDetailsEntity.getBUILDVERSION()) > Double.parseDouble((BuildConfig.VERSION_NAME))) {
                    if (buildDetailsEntity.getFORCEDOWNLOAD()) {
                        getMvpView().displayAppInfoDialog("Update Available", buildDetailsEntity.getBUILDMESSAGE(), "Update", "");
                    } else {
                        getMvpView().displayAppInfoDialog("Update Available", buildDetailsEntity.getBUILDMESSAGE(), "Update Now", "Later");
                    }
                }
            } else {
                getMvpView().displayAppInfoDialog("Info", buildDetailsEntity.getAVABILITYMESSAGE(), "", "");
            }
        }
    }
}
