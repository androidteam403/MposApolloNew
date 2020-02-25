package com.apollopharmacy.mpospharmacist.ui.newadminloginsetup;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.databinding.NewAdminloginSetupBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.newadminloginsetup.model.AdminLoginResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.StoreSetupActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

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
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        newAdminloginSetupBinding = DataBindingUtil.setContentView(this, R.layout.new_adminlogin_setup);
        getActivityComponent().inject(this);
        newAdminLoginPresenter.onAttach(NewAdminLoginSetUp.this);
        setUp();
    }

    @Override
    protected void setUp() {
        newAdminloginSetupBinding.setCallback(newAdminLoginPresenter);

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

    }

    @Override
    public void onLoginClick() {
        if (validations()) {
            newAdminLoginPresenter.onAdminLoginClick();
        }
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

    private boolean validations() {
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