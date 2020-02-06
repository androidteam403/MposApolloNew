package com.apollopharmacy.mpospharmacist.ui.storesetup;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivityStoreSetupBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;

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
