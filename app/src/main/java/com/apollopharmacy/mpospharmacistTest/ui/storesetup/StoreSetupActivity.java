package com.apollopharmacy.mpospharmacistTest.ui.storesetup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacistTest.R;
import com.apollopharmacy.mpospharmacistTest.data.db.model.StoreDetails;
import com.apollopharmacy.mpospharmacistTest.data.db.realm.RealmController;
import com.apollopharmacy.mpospharmacistTest.databinding.ActivityStoreSetupBinding;
import com.apollopharmacy.mpospharmacistTest.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.dialog.GetStoresDialog;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreListResponseModel;
import com.apollopharmacy.mpospharmacistTest.ui.storesetup.model.StoreSetupModel;
import com.apollopharmacy.mpospharmacistTest.utils.CommonUtils;
import com.apollopharmacy.mpospharmacistTest.utils.UiUtils;
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
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.realm.Realm;

public class StoreSetupActivity extends BaseActivity implements StoreSetupMvpView, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    @Inject
    StoreSetupMvpPresenter<StoreSetupMvpView> mPresenter;
    ActivityStoreSetupBinding activityStoreSetupBinding;
    private String deviceId;
    private StoreListResponseModel storeListObj = null;
    private StoreListResponseModel.StoreListObj selectedStoreId = null;
    private StoreListResponseModel.StoreListObj selectedStoreContactNum = null;
    private String deviceType = "";
    private String registerDate = "";
    private String userID = "";
    double latitude = 0.0;
    double longitude = 0.0;

    private GoogleApiClient googleApiClient;
    private Location mylocation;
    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;

    public static Intent getStartIntent(Context context, double latitude, double longitude, String userID) {
        Intent intent = new Intent(context, StoreSetupActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("user_id", userID);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        return intent;

    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, StoreSetupActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        double diagonalInches = UiUtils.displaymetrics(this);
        if (diagonalInches >= 10) {
            Log.i("Tab inches-->", "10 inches");
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        } else {
            Log.i("Tab inches below 7 and 7 inces-->", "7 inches");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }
        activityStoreSetupBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_setup);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StoreSetupActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityStoreSetupBinding.setCallback(mPresenter);

        deviceId = CommonUtils.getDeviceId(this);

        if (getIntent() != null) {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            registerDate = currentDate.format(c);
            deviceType = android.os.Build.MODEL;
            StoreSetupModel storeSetupModel = new StoreSetupModel();
            storeSetupModel.setMacId(deviceId);
            storeSetupModel.setDeviceName(deviceType);
            storeSetupModel.setDeviceType(Build.DEVICE);
            storeSetupModel.setStoreDate(registerDate);
            storeSetupModel.setStoreLatitude(latitude);
            storeSetupModel.setStoreLongitude(longitude);
            activityStoreSetupBinding.setStoremodel(storeSetupModel);
        }
        setUpGClient();
        mPresenter.getStoreList();
    }

    private boolean isShowDialog = false;

    @Override
    public void onSelectStoreSearch() {
        if (storeListObj != null) {
            if (storeListObj.getStoreListArr().size() > 0) {
                GetStoresDialog dialog = GetStoresDialog.newInstance();
                dialog.setStoreDetailsMvpView(this);
                dialog.setStoreListArray(storeListObj.getStoreListArr());
                if (!isShowDialog) {
                    isShowDialog = true;
                    dialog.show(getSupportFragmentManager(), "");
                }
            }
        }
    }


    private boolean validations() {
        String terminalId = Objects.requireNonNull(activityStoreSetupBinding.terminalIdText.getText()).toString();
        if (terminalId.isEmpty()) {
            activityStoreSetupBinding.terminalIdText.setError("Please Enter Terminal ID");
            activityStoreSetupBinding.terminalIdText.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onCancelBtnClick() {
        selectedStoreId = null;
        activityStoreSetupBinding.setStoreinfo(selectedStoreId);
    }

    @Override
    public void storeSetupSuccess(DeviceSetupResModel storeResModel) {
        mPresenter.insertAdminLoginDetails();
    }

    @Override
    public void setStoresList(StoreListResponseModel storesList) {
        storeListObj = storesList;
    }

    @Override
    public void onSelectStore(StoreListResponseModel.StoreListObj item) {
        activityStoreSetupBinding.setStoreinfo(item);
        selectedStoreId = item;
        selectedStoreContactNum = item;
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getFcmKey() {
        return FirebaseInstanceId.getInstance().getToken();
    }

    @Override
    public String getStoreId() {
        return selectedStoreId.getStoreId();
    }

    @Override
    public String getStoreContactNum() {
        return selectedStoreContactNum.getContactNumber();
    }

    @Override
    public StoreListResponseModel.StoreListObj getStoreDetails() {
        return activityStoreSetupBinding.getStoreinfo();
    }

    @Override
    public String getTerminalId() {
        return activityStoreSetupBinding.terminalIdText.getText().toString();
    }

    @Override
    public String getUserId() {
        return userID;
    }

    @Override
    public String getDeviceType() {
        return deviceType;
    }

    @Override
    public String getRegisteredDate() {
        return registerDate;
    }

    @Override
    public String getLatitude() {
        return String.valueOf(latitude);
    }

    @Override
    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    public void onNavigateHomeScreen() {
        startActivity(PharmacistLoginActivity.getStartIntent(this));
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        finish();
    }

    @Override
    public void dialogCloseListiner() {
        isShowDialog = false;
    }

    @Override
    public void onVerifyClick() {
        if (isValidate()) {
            mPresenter.checkConfingApi();
        }
    }

    @Override
    public String getEposURL() {
        return activityStoreSetupBinding.baseUrl.getText().toString();
    }


    //TODO for website validation
    private boolean isValidate() {
        String url = activityStoreSetupBinding.baseUrl.getText().toString().trim();
        if (url.isEmpty()) {
            activityStoreSetupBinding.baseUrl.setError("Please Enter Epos Url");
            activityStoreSetupBinding.baseUrl.requestFocus();
            return false;
        }
        return true;
    }

    private void adminSetup() {
        Realm realm = RealmController.with(this).getRealm();
        StoreDetails book = new StoreDetails();
        book.setId(1);
        book.setStoreId(Integer.parseInt(getStoreId()));
        book.setStoreLat(latitude);
        book.setStoreLang(longitude);
        book.setRegistrationDate(registerDate);
        book.setRegisteredBy(getUserId());
        book.setUserId(getUserId());
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
            googleApiClient.stopAutoManage(StoreSetupActivity.this);
            googleApiClient.disconnect();
        }
        mPresenter.onDetach();
    }

    @Override
    public void onLocationChanged(Location location) {
        mylocation = location;
        if (mylocation != null) {
            latitude = mylocation.getLatitude();
            longitude = mylocation.getLongitude();
            activityStoreSetupBinding.getStoremodel().setStoreLatitude(mylocation.getLatitude());
            activityStoreSetupBinding.getStoremodel().setStoreLongitude(mylocation.getLongitude());
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
        int permissionLocation = ContextCompat.checkSelfPermission(StoreSetupActivity.this,
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
        int permissionLocation = ContextCompat.checkSelfPermission(StoreSetupActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        } else {
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(StoreSetupActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    @SuppressLint("RestrictedApi") LocationRequest locationRequest = new LocationRequest();
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
                                            .checkSelfPermission(StoreSetupActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        status.startResolutionForResult(StoreSetupActivity.this,
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
