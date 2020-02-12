package com.apollopharmacy.mpospharmacist.ui.doctordetails.dialog;

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

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.DialogAllDoctorsBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseDialog;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.DoctorDetailsMvpView;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.adapter.AllDoctorsListAdapter;
import com.apollopharmacy.mpospharmacist.ui.doctordetails.model.DoctorSearchResModel;

import java.util.ArrayList;

import javax.inject.Inject;

public class AllDoctorsDialog extends BaseDialog implements AllDoctorsDialogMvpView {
    private DialogAllDoctorsBinding allDoctorsBinding;
    private ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList = new ArrayList<>();
    private DoctorDetailsMvpView doctorDetailsMvpView;

    @Inject
    AllDoctorsDialogMvpPresenter<AllDoctorsDialogMvpView> mPresenter;

    public static AllDoctorsDialog newInstance() {
        AllDoctorsDialog dialog = new AllDoctorsDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setDoctorsArray(ArrayList<DoctorSearchResModel.DropdownValueBean> doctorSearchArrayList) {
        this.doctorSearchArrayList = doctorSearchArrayList;
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
        AllDoctorsListAdapter doctorsListAdapter = new AllDoctorsListAdapter(getActivity(), doctorSearchArrayList);
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
                doctorsListAdapter.getFilter().filter(s);
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
        doctorDetailsMvpView.onSelectDoctor(item);
        dismissDialog("");
    }
}
