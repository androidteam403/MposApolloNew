package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionPdfScreen;

import com.apollopharmacy.mpospharmacistTest.data.DataManager;
import com.apollopharmacy.mpospharmacistTest.ui.base.BasePresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpPresenter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class PdfPresenter <V extends PdfMvpView> extends BasePresenter<V>
        implements PdfMvpPresenter<V> {
    @Inject
    public PdfPresenter(DataManager manager, SchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(manager, schedulerProvider, compositeDisposable);
    }
}
