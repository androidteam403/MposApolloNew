package com.apollopharmacy.mpospharmacist.ui.adddoctor;

import com.apollopharmacy.mpospharmacist.ui.adddoctor.model.AddDoctorResModel;
import com.apollopharmacy.mpospharmacist.ui.base.MvpView;

public interface AddDoctorMvpView extends MvpView {

    void onSubmitBtnClick();

    void onClickBackPressed();

    void addDoctorSuccess(AddDoctorResModel addDoctorResModel);

    void addDoctorFailed(String errMsg);
}
