package com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

public interface AllDoctorsDialogMvpView extends MvpView {

    void dismissDialog();

    void onClickListener(DoctorSearchResModel.DropdownValueBean item);

    void updateNoDoctorView(int arrayCnt);

    void onAddDoctorClick();

    String doctorSearch();

    void onSuccessDoctorSearch(DoctorSearchResModel body);

    void onSuccessDefaultDoctorSearch(DoctorSearchResModel body);


    void onFailureDoctorSerach();

}
