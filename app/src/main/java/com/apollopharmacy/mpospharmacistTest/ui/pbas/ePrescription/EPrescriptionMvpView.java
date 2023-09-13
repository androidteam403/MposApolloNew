package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription;

import com.apollopharmacy.mpospharmacistTest.ui.base.MvpView;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;

import java.util.List;

public interface EPrescriptionMvpView extends MvpView {

    void onSucessPrescriptionList(List<EPrescriptionModelClassResponse> prescriptionLine);

    void onClickRightArrow(EPrescriptionModelClassResponse ePrescription);

    void noOrderFound(int count);
}
