package com.apollo.pharmacy.ui.storesetup;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.databinding.ActivityStoreSetupBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;

import javax.inject.Inject;

public class StoreSetupActivity extends BaseActivity implements StoreSetupMvpView {
    @Inject
    StoreSetupMvpPresenter<StoreSetupMvpView> mPresenter;

    ActivityStoreSetupBinding activityStoreSetupBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoreSetupBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_setup);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StoreSetupActivity.this);
        getSupportActionBar().hide();
        setUp();
    }

    @Override
    protected void setUp() {
        activityStoreSetupBinding.setCallback(mPresenter);
    }


    @Override
    public void onSelectStoreClick() {
        activityStoreSetupBinding.relativeDetails.setVisibility(View.VISIBLE);
    }
}
