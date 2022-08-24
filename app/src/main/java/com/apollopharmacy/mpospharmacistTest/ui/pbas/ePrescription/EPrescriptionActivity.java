package com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityEPrescription2Binding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacistTest.ui.home.MainActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.adapter.EPrescriptionListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescription.model.EPrescriptionModelClassResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.ePrescriptionflow.ePrescriptionLineTransaction.EPrescriptionMedicineDetailsActivity;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class EPrescriptionActivity extends BaseFragment implements MainActivity.UserIneractionListener, MainActivity.OnBackPressedListener, EPrescriptionMvpView {

    @Inject
    EPrescriptionMvpPresenter<EPrescriptionMvpView> mPresenter;
    EPrescriptionListAdapter ePrescriptionListAdapter;

    private ActivityEPrescription2Binding activityEPrescription2Binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityEPrescription2Binding = DataBindingUtil.inflate(inflater, R.layout.activity_e_prescription2, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(EPrescriptionActivity.this);

        return activityEPrescription2Binding.getRoot();


    }


    @Override
    protected void setUp(View view) {
        mPresenter.fetchFulfilmentOrderList();
        searchByFulfilmentId();
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnUserIneractionListener(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setOnBackPressedListener(this);

        activityEPrescription2Binding.deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityEPrescription2Binding.searchByfulfimentid.setText("");
                activityEPrescription2Binding.searchIcon.setVisibility(View.VISIBLE);
                activityEPrescription2Binding.deleteCancel.setVisibility(View.GONE);
//                recyclerView();

            }
        });

    }

    private void searchByFulfilmentId() {
        activityEPrescription2Binding.searchByfulfimentid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() >= 2) {
                    activityEPrescription2Binding.searchIcon.setVisibility(View.GONE);
                    activityEPrescription2Binding.deleteCancel.setVisibility(View.VISIBLE);
                    if (ePrescriptionListAdapter != null) {

                        ePrescriptionListAdapter.getFilter().filter(editable);
                    }

                } else if (activityEPrescription2Binding.searchByfulfimentid.getText().toString().equals("")) {
                    if (ePrescriptionListAdapter != null) {
                        ePrescriptionListAdapter.getFilter().filter("");
                    }
                    activityEPrescription2Binding.searchIcon.setVisibility(View.VISIBLE);
                    activityEPrescription2Binding.deleteCancel.setVisibility(View.GONE);
                } else {
                    if (ePrescriptionListAdapter != null) {
                        ePrescriptionListAdapter.getFilter().filter("");
                    }
                }
            }
        });
    }

    List<EPrescriptionModelClassResponse> prescriptionLine;

    @Override
    public void onSucessPrescriptionList(List<EPrescriptionModelClassResponse> prescriptionLine) {
        this.prescriptionLine = prescriptionLine;

        activityEPrescription2Binding.setCallback(mPresenter);
        activityEPrescription2Binding.siteId.setText(prescriptionLine.get(0).getShopId());
        activityEPrescription2Binding.siteName.setText(mPresenter.getLoinStoreLocation());
        activityEPrescription2Binding.terminalId.setText(mPresenter.getTerminalId());
//        Toast.makeText(getContext(), "" + prescriptionLine.size(), Toast.LENGTH_SHORT).show();
//        PickerNavigationActivity.mInstance.setWelcome("Total " + prescriptionLine.size() + " orders");
        ePrescriptionListAdapter = new EPrescriptionListAdapter(getContext(), prescriptionLine, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        activityEPrescription2Binding.fullfilmentRecycler.setLayoutManager(mLayoutManager);
        activityEPrescription2Binding.fullfilmentRecycler.setAdapter(ePrescriptionListAdapter);
    }

    @Override
    public void onClickRightArrow(EPrescriptionModelClassResponse ePrescription) {
        try {
            int position = -1;
            for (int i = 0; i < prescriptionLine.size(); i++) {
                if (prescriptionLine.get(i).getPrescriptionNo().equalsIgnoreCase(ePrescription.getPrescriptionNo())) {
                    position = i;
                }
            }
            if (position != -1) {
                startActivity(EPrescriptionMedicineDetailsActivity.getStartActivity(getContext(), prescriptionLine, position, mPresenter.getLoinStoreLocation(), mPresenter.getTerminalId()));
                getActivity().overridePendingTransition(R.anim.slide_from_right_p, R.anim.slide_to_left_p);
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "*****************************************"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void noOrderFound(int count) {
        if (count > 0) {
            activityEPrescription2Binding.noOrderFoundText.setVisibility(View.GONE);
        } else {
            activityEPrescription2Binding.noOrderFoundText.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void userInteraction() {

    }

    @Override
    public void doBack() {

    }
}