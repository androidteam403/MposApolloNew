package com.apollo.pharmacy.utils.bottomsheet;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ViewPharmacyBottomSheetBinding;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpPresenter;
import com.apollo.pharmacy.ui.searchpharmacy.SearchPharmacyMvpView;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class PharmacyBottomSheetFragment extends BottomSheetDialogFragment {
    private Pharmacy.StoreListObj model;
    private SearchPharmacyMvpPresenter<SearchPharmacyMvpView> mPresenter;

    public PharmacyBottomSheetFragment(Pharmacy.StoreListObj model, SearchPharmacyMvpPresenter<SearchPharmacyMvpView> mPresenter) {
        this.model = model;
        this.mPresenter = mPresenter;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        ViewPharmacyBottomSheetBinding pharmacyBottomSheetBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.view_pharmacy_bottom_sheet,null,false);
        dialog.setContentView(pharmacyBottomSheetBinding.getRoot());
        pharmacyBottomSheetBinding.setModel(model);
        pharmacyBottomSheetBinding.setCallback(mPresenter);

//        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) pharmacyBottomSheetBinding.bottomSheet).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
 
        //Set callback
        if (behavior != null) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}