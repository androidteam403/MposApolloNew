package com.apollopharmacy.mpospharmacistTest.ui.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogDroppingBinding;
import com.apollopharmacy.mpospharmacistTest.databinding.DialogIdleBinding;
import com.apollopharmacy.mpospharmacistTest.di.component.ActivityComponent;
import com.apollopharmacy.mpospharmacistTest.di.component.DaggerActivityComponent;
import com.apollopharmacy.mpospharmacistTest.di.module.ActivityModule;
import com.apollopharmacy.mpospharmacistTest.root.ApolloMposApp;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.NetworkUtils;
import com.google.android.material.snackbar.Snackbar;

import static android.content.pm.PackageManager.GET_META_DATA;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {


    private ProgressDialog mProgressDialog;
    private static final long IDLE_TIME_MILLIS = 300000; // 1 minute idle time
    private Handler idleHandler;
    private Runnable idleRunnable;
    private ActivityComponent mActivityComponent;
    // private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((ApolloMposApp) getApplication()).getComponent())
                .build();
        idleHandler = new Handler();
        idleRunnable = new Runnable() {
            @Override
            public void run() {
                // Show your dialog here
                showDialog();
            }
        };
//        if (getResources().getBoolean(R.bool.portrait_only)) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start the idle timer when the activity is resumed
        startIdleTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop the idle timer when the activity is paused
        stopIdleTimer();
    }

    private void startIdleTimer() {
        // Post a delayed task to run the idleRunnable after the specified idle time
        idleHandler.postDelayed(idleRunnable, IDLE_TIME_MILLIS);
    }

    private void stopIdleTimer() {
        // Remove the callbacks to stop the idle timer
        idleHandler.removeCallbacks(idleRunnable);
    }

    private void resetIdleTimer() {
        // Reset the idle timer when user interacts with the activity
        stopIdleTimer();
        startIdleTimer();
    }

    // Override relevant event listeners to reset the idle timer
    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        resetIdleTimer();
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        DialogIdleBinding dialogIdleBinding = DataBindingUtil.inflate(LayoutInflater.from(this),
                R.layout.dialog_idle, null, false);

        dialog.setContentView(dialogIdleBinding.getRoot());
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogIdleBinding.ok.setOnClickListener(view -> {
            dialog.dismiss();
            resetIdleTimer();
        }
        );

        dialogIdleBinding.close.setOnClickListener(view -> {
                    dialog.dismiss();
                    resetIdleTimer();
                }
        );
        dialog.show();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showLoading() {
        hideLoading();
        hideKeyboard();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    private void resetTitles() {
        try {
            ActivityInfo info = getPackageManager().getActivityInfo(getComponentName(), GET_META_DATA);
            if (info.labelRes != 0) {
                setTitle(info.labelRes);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }


    @Override
    public void onError(String message) {
        if (message != null) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.some_error));
        }
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    @Override
    public void showMessage(String message) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.some_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(@StringRes int resId) {
        showMessage(getString(resId));
    }

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void openActivityOnTokenExpire() {
    }

    @Override
    public void openLoginActivity() {
    }

    protected abstract void setUp();

    @SuppressLint("NewApi")
    public boolean isAppInLockTaskMode() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        // When SDK version is 23
        int lockTaskMode = 0;
        if (activityManager != null) {
            lockTaskMode = activityManager.getLockTaskModeState();
        }
        return lockTaskMode != ActivityManager.LOCK_TASK_MODE_NONE;
    }


}