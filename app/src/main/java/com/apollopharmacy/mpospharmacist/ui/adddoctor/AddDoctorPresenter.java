package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.data.DataManager;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAddDoctorBinding;
import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorModel;
import com.apollopharmacy.mpospharmacist.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;
import com.apollopharmacy.mpospharmacist.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AddDoctorPresenter<V extends AddDoctorMvpView> extends BasePresenter<V>
        implements AddDoctorMvpPresenter<V> {
    ActivityAddDoctorBinding addDoctorBinding;
    @Inject
    public AddDoctorPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onClickSubmit() {
        getMvpView().onSubmitClick();
    }

    @Override
    public void onActionBarBackPressed() {
        getMvpView().onClickBackPressed();
    }

    @Override
    public void userSubmit() {
        if (getMvpView().isNetworkConnected()){
            AddDoctorModel addDoctorModel = new AddDoctorModel();
            addDoctorModel.setDoctorRegNo(getMvpView().getDoctorRegNo());
            addDoctorModel.setDoctorName(getMvpView().getDoctorName());
            addDoctorModel.setSpeciality(getMvpView().getSpeciality());
            addDoctorModel.setPlaceOfPractice(getMvpView().getPlaceOfPractice());
            addDoctorModel.setAddress(getMvpView().getAddress());
            addDoctorModel.setPhoneNumber(getMvpView().getPhoneNo());
        }
    }
}
