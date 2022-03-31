package com.apollopharmacy.mpospharmacistTest.ui.doctordetails.dialog;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogAllDoctorsBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.adapter.AllDoctorsListAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.doctordetails.model.DoctorSearchResModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class AllDoctorsDialog extends BaseDialog implements AllDoctorsDialogMvpView {
    private DialogAllDoctorsBinding allDoctorsBinding;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList = new ArrayList<>();
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayListOnElse = new ArrayList<>();
    private DoctorDetailsMvpView doctorDetailsMvpView;
    private AllDoctorsListAdapter doctorsListAdapter;
    boolean isListFiltered = false;

    @Inject
    AllDoctorsDialogMvpPresenter<AllDoctorsDialogMvpView> mPresenter;

    public static AllDoctorsDialog newInstance() {
        AllDoctorsDialog dialog = new AllDoctorsDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setDoctorsArray(ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList1) {
        this.doctorSearchArrayList = doctorSearchArrayList1;
        doctorSearchArrayListOnElse.addAll(doctorSearchArrayList1);
    }

    public void setDoctorDetailsMvpView(DoctorDetailsMvpView detailsMvpView) {
        this.doctorDetailsMvpView = detailsMvpView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        allDoctorsBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_all_doctors, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AllDoctorsDialog.this);
        return allDoctorsBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        allDoctorsBinding.setCallback(mPresenter);
        doctorsListAdapter = new AllDoctorsListAdapter(getActivity(), doctorSearchArrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        allDoctorsBinding.allDoctorsRecyclerView.setLayoutManager(mLayoutManager);
        doctorsListAdapter.onClickListener(this);
        allDoctorsBinding.allDoctorsRecyclerView.setAdapter(doctorsListAdapter);
        allDoctorsBinding.doctorNameSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 3) {
                    mPresenter.searchDoctorDetailsByName();
                } else if (s.toString().isEmpty()) {
                    mPresenter.defaultDoctorList();
                }
            }
        });
    }

    @Override
    public void openLoginActivity() {

    }

    @Override
    public void dismissDialog() {
        dismissDialog("");
    }

    @Override
    public void onClickListener(DoctorSearchResModel.DropdownValueBean item) {
        doctorDetailsMvpView.onSelectDoctor(item, doctorSearchArrayListOnElse);
        dismissDialog("");
    }

    @Override
    public void onAddDoctorClick() {
        doctorDetailsMvpView.onAddDoctorClick();
        dismissDialog("");
    }

    @Override
    public String doctorSearch() {
        return allDoctorsBinding.doctorNameSearch.getText().toString();
    }

    @Override
    public void onSuccessDoctorSearch(DoctorSearchResModel body) {
        if (body.get_DropdownValue().size() > 0) {
            doctorSearchArrayList.clear();
            allDoctorsBinding.allDoctorsRecyclerView.setVisibility(View.VISIBLE);
            allDoctorsBinding.setNoDoctor(false);
            doctorSearchArrayList.addAll(body.get_DropdownValue());
            doctorsListAdapter.clearDate();
            doctorsListAdapter.add(body.get_DropdownValue());
        } else {
            doctorsListAdapter.clearDate();
            allDoctorsBinding.setNoDoctor(false);
        }
    }

    @Override
    public void onSuccessDefaultDoctorSearch(DoctorSearchResModel body) {
        if (body.get_DropdownValue().size() > 0) {
            doctorSearchArrayList.clear();
            allDoctorsBinding.allDoctorsRecyclerView.setVisibility(View.VISIBLE);
            allDoctorsBinding.setNoDoctor(false);
            doctorSearchArrayList.addAll(body.get_DropdownValue());
            doctorsListAdapter.clearDate();
            doctorsListAdapter.add(body.get_DropdownValue());
        }
    }

    @Override
    public void onFailureDoctorSerach() {
        allDoctorsBinding.allDoctorsRecyclerView.setVisibility(View.GONE);
        allDoctorsBinding.setNoDoctor(true);
    }
}
