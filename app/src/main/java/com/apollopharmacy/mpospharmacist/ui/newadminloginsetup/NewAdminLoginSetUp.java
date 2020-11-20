package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.NewAdminloginSetupBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.home.ui.dashboard.model.RowsEntity;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupActivity;
import com.apollopharmacy.mpospharmacist.utils.FileUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class NewAdminLoginSetUp extends BaseActivity implements NewAdminLoginMvpView {

    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    double latitude = 0.0;
    double longitude = 0.0;

    @Inject
    NewAdminLoginMvpPresenter<NewAdminLoginMvpView> newAdminLoginPresenter;

    NewAdminloginSetupBinding newAdminloginSetupBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newAdminloginSetupBinding = DataBindingUtil.setContentView(this, R.layout.new_adminlogin_setup);
        getActivityComponent().inject(this);
        newAdminLoginPresenter.onAttach(NewAdminLoginSetUp.this);
        setUp();
    }

    @Override
    protected void setUp() {
        newAdminloginSetupBinding.setCallback(newAdminLoginPresenter);
        newAdminloginSetupBinding.password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        newAdminloginSetupBinding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Activity", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        getSharedPreferences("ApolloMPOS", MODE_PRIVATE).edit().putString("fcm_token", token).apply();
                        Log.e("Activity", "Got the token: " + token);
                    }
                });

        newAdminloginSetupBinding.password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    newAdminLoginPresenter.onAdminLoginClick();
                    return true;
                }
                return false;
            }
        });
    }


    @Override
    public String getUserID() {
        return Objects.requireNonNull(newAdminloginSetupBinding.userId.getText()).toString();
    }

    @Override
    public String getPassword() {
        return Objects.requireNonNull(newAdminloginSetupBinding.password.getText()).toString();
    }

    @Override
    public void userLoginSuccess(AdminLoginResModel loginResModel) {
        startActivity(StoreSetupActivity.getStartIntent(this, latitude, longitude, getUserID()));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void userLoginFailed(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean validation() {
        String userID = Objects.requireNonNull(newAdminloginSetupBinding.userId.getText()).toString();
        String password = Objects.requireNonNull(newAdminloginSetupBinding.password.getText()).toString();
        if (userID.isEmpty()) {
            newAdminloginSetupBinding.userId.setError("Please Enter User ID");
            newAdminloginSetupBinding.userId.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            newAdminloginSetupBinding.password.setError("Please Enter Password");
            newAdminloginSetupBinding.password.requestFocus();
            return false;
        }
        return true;
    }

}