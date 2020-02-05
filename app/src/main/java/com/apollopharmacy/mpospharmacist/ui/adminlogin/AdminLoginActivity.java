package com.apollopharmacy.mpospharmacist.ui.adminlogin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.db.model.StoreDetails;
import com.apollopharmacy.mpospharmacist.data.db.realm.RealmController;
import com.apollopharmacy.mpospharmacist.databinding.ActivityAdminLoginBinding;
import com.apollopharmacy.mpospharmacist.ui.adminlogin.model.SpinnerPojo;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.dashboard.DashboardActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.realm.Realm;

public class AdminLoginActivity extends BaseActivity implements AdminLoginMvpView, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    @Inject
    AdminLoginMvpPresenter<AdminLoginMvpView> mPresenter;
    private ActivityAdminLoginBinding adminLoginBinding;

    private GoogleApiClient googleApiClient;
    private Location mylocation;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private boolean isLocationFetched = false;
    private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss", Locale.getDefault());
    String strFont = null;
    private ArrayList<SpinnerPojo> loginTypeArr;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AdminLoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        adminLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_admin_login);
        getActivityComponent().inject(this);
        mPresenter.onAttach(AdminLoginActivity.this);

        setUp();
    }

    @SuppressLint("ResourceType")
    @Override
    protected void setUp() {
        adminLoginBinding.setHandlers(mPresenter);

        setUpGClient();

        ArrayAdapter<SpinnerPojo> adapter = new ArrayAdapter<SpinnerPojo>(this,
                android.R.layout.simple_spinner_dropdown_item, getLoginTypes());

        strFont = this.getString(R.font.roboto_regular);
        Typeface tt = Typeface.createFromAsset(getAssets(), "font/roboto_regular.ttf");

        adminLoginBinding.spinner.setTypeface(tt);
        adminLoginBinding.spinner.setAdapter(adapter);

        adminLoginBinding.spinner.setOnItemClickListener((materialSpinner, view, i, l) -> {
            SpinnerPojo item = (SpinnerPojo) materialSpinner.getSelectedItem();
            updateStorInfo(Objects.requireNonNull(item));
        });


        adminLoginBinding.relative.setOnTouchListener((v, event) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(adminLoginBinding.relative.getWindowToken(), 0);
            return false;
        });
    }

    private void updateStorInfo(SpinnerPojo item) {
        adminLoginBinding.siteName.setText(item.getSiteName());
        adminLoginBinding.siteAddress.setText(item.getSiteAddress());
        adminLoginBinding.dcId.setText(item.getDcId());
        adminLoginBinding.dcName.setText(item.getDcName());
        adminLoginBinding.siteName.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoginClick() {
        startActivity(DashboardActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void onSetUpClick() {

    }

    @Override
    public void onNavigateHomeScreen() {

    }

    private ArrayList<SpinnerPojo> getLoginTypes() {
        loginTypeArr = new ArrayList<>();
        SpinnerPojo item = new SpinnerPojo();
        item.setLoginType("Apollo-Ameerpet");
        item.setSiteName("Apollomadhapur");
        item.setSiteAddress("Madhapur");
        item.setDcId("1234");
        item.setDcName("Dollo");
        loginTypeArr.add(item);

        item = new SpinnerPojo();
        item.setLoginType("Apollo-Madhapur");
        item.setSiteName("Apolloameerpet");
        item.setSiteAddress("Ameerpet");
        item.setDcId("5678");
        item.setDcName("Pharma");
        loginTypeArr.add(item);

        item = new SpinnerPojo();
        item.setLoginType("Apollo-Hitech");
        item.setSiteName("Apollohitetch");
        item.setSiteAddress("Hitech");
        item.setDcId("2654");
        item.setDcName("Saradon");
        loginTypeArr.add(item);
        return loginTypeArr;
    }

    private void adminSetup() {
        Date date = new Date();
        Realm realm = RealmController.with(this).getRealm();
        StoreDetails book = new StoreDetails();
        book.setId(1);
        book.setStoreId(12345);
        book.setStoreLat(mylocation.getLatitude());
        book.setStoreLang(mylocation.getLongitude());
        book.setRegistrationDate(sdf.format(date));
        book.setRegisteredBy("Jagadish");
        book.setUserId("testid");
        book.setEmail("Entered User Name"); //adminLoginBinding.userName.getText().toString()
        book.setPhone("9160147044");
        realm.beginTransaction();
        realm.copyToRealm(book);
        realm.commitTransaction();
        mPresenter.insertAdminLoginDetails();
    }

    private synchronized void setUpGClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, 0, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(AdminLoginActivity.this);
            googleApiClient.disconnect();
        }
        mPresenter.onDetach();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            //if (!isLocationFetched) {
            // isLocationFetched = true;
            double latitude = mylocation.getLatitude();
            double longitude = mylocation.getLongitude();

            //}
        } else {
            getMyLocation();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        checkPermissions();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void checkPermissions() {
        int permissionLocation = ContextCompat.checkSelfPermission(AdminLoginActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                        REQUEST_ID_MULTIPLE_PERMISSIONS);
            }
        } else {
            getMyLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        int permissionLocation = ContextCompat.checkSelfPermission(AdminLoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(AdminLoginActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(3000);
                    locationRequest.setFastestInterval(3000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                        @Override
                        public void onResult(@NonNull LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(AdminLoginActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        status.startResolutionForResult(AdminLoginActivity.this,
                                                REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                    }
                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    break;
                            }
                        }
                    });
                }
            } else {
                setUpGClient();
            }
        }
    }
}