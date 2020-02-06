package com.apollopharmacy.mpospharmacist.ui.splash;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.ActivitySplashBinding;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.AdminLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    private ActivitySplashBinding splashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        getActivityComponent().inject(this);
        setUp();
        mPresenter.onAttach(SplashActivity.this);
    }

    @Override
    public void openLoginActivity() {
        startActivity(DashboardActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openAdminSetupActivity() {
        startActivity(AdminLoginActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openMainActivity() {
//        Intent intent = MainActivity.getStartIntent(SplashActivity.this);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void startSyncService() {
//        SyncService.start(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp() {
        Animation animZoomOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        splashBinding.imageAppLogo.startAnimation(animZoomOut);
    }
}
