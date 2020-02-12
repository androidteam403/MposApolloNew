package com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog;

import com.apollopharmacy.mpospharmacist.ui.base.MvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

public interface AllDoctorsDialogMvpView extends MvpView {

    void dismissDialog();

    void onClickListener(DoctorSearchResModel.DropdownValueBean item);
}
