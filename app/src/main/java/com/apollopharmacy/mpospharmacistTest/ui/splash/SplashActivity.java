package com.apollopharmacy.mpospharmacistTest.ui.splash;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivitySplashBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.newadminloginsetup.NewAdminLoginSetUp;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.StoreSetupActivity;
import com.apollopharmacy.mpospharmacistTest.utils.LogFileUtil;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;

import java.io.File;
import java.lang.reflect.Field;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashMvpPresenter<SplashMvpView> mPresenter;

    private ActivitySplashBinding splashBinding;
    final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogFileUtil.createLogFolder(SplashActivity.this);
        File appDirectory = new File(Environment.getExternalStorageDirectory() + "/MposLogs");
        File logFile = new File(appDirectory, "logcat.txt");
        Log.e("LogsUtil", "File Created ===::::::::=== File Name :: " + logFile);

        String fieldName = "";
        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }


        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL + " " + android.os.Build.BRAND + " ("
                + android.os.Build.VERSION.RELEASE + ")"
                + " API-" + android.os.Build.VERSION.SDK_INT
                + fieldName;

        System.out.println("Device info-->" + model);
        System.out.println("Device info-->" + fieldName);


        double diagonalInches = UiUtils.displaymetrics(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.i("Device id---->", androidId);
        getWindow().getDecorView().setSystemUiVisibility(flags);

        splashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SplashActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();
    }

    @Override
    public void openLoginActivity() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openAdminSetupActivity() {
        startActivity(new Intent(this, NewAdminLoginSetUp.class));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        //startActivity(PharmacistLoginActivity.getStartIntent(this));
        // overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openMainActivity() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void storeSetupActivity() {
        startActivity(StoreSetupActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void startAnimation() {
        if (!isAppInLockTaskMode()) {
            startLockTask();
        }
        mPresenter.decideNextActivity();
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
        animZoomOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                mPresenter.enableKioskMode();
                mPresenter.decideNextActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}
