package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;

public interface AllDoctorsDialogMvpView extends MvpView {

    void dismissDialog();

    void onClickListener(DoctorSearchResModel.DropdownValueBean item);

    void onAddDoctorClick();

    String doctorSearch();

    void onSuccessDoctorSearch(DoctorSearchResModel body);

    void onSuccessDefaultDoctorSearch(DoctorSearchResModel body);


    void onFailureDoctorSerach();

}
