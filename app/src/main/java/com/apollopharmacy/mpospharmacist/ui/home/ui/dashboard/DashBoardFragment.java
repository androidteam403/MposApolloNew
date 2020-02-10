package com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.FragmentDashboardBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseFragment;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterMvpView;
import com.apollopharmacy.mpospharmacist.ui.home.ui.customermaster.CustomerMasterPresenter;
import com.apollopharmacy.mpospharmacist.ui.searchcustomerdoctor.SearchCustomerDoctorDetailsActivity;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class DashBoardFragment extends BaseFragment implements DashBoardMvpView {

    @Inject
    DashBoardMvpPresenter<DashBoardMvpView> mPresenter;
    private FragmentDashboardBinding dashboardBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dashboard, container, false);
        getActivityComponent().inject(this);
        mPresenter.onAttach(DashBoardFragment.this);
        return dashboardBinding.getRoot();
    }

    @Override
    protected void setUp(View view) {
        dashboardBinding.setCallBack(mPresenter);
    }

    @Override
    public void onClickNewOrderBtn() {
        startActivity(SearchCustomerDoctorDetailsActivity.getStartIntent(getBaseActivity()));
        getBaseActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}