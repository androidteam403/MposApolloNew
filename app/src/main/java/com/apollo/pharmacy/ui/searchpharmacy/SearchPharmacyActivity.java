package com.apollo.pharmacy.ui.searchpharmacy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.apollo.pharmacy.R;
import com.apollo.pharmacy.data.db.model.StoreDetails;
import com.apollo.pharmacy.data.db.realm.RealmController;
import com.apollo.pharmacy.databinding.ActivitySearchPharmacyBinding;
import com.apollo.pharmacy.ui.base.BaseActivity;
import com.apollo.pharmacy.ui.dashboard.DashboardActivity;
import com.apollo.pharmacy.ui.searchpharmacy.adapter.SearchPharmacyListAdapter;
import com.apollo.pharmacy.ui.searchpharmacy.model.Pharmacy;
import com.apollo.pharmacy.ui.searchproduct.SearchProductActivity;
import com.apollo.pharmacy.utils.bottomsheet.PharmacyBottomSheetFragment;
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
import com.google.android.gms.maps.model.Dash;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.realm.Realm;

public class SearchPharmacyActivity extends BaseActivity implements SearchPharmacyMvpView, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    @Inject
    SearchPharmacyMvpPresenter<SearchPharmacyMvpView> mPresenter;

    private ActivitySearchPharmacyBinding searchPharmacyBinding;
    private SearchPharmacyListAdapter mAdapter;
    private ArrayList<Pharmacy.StoreListObj> pharmacyArrList;
    private GoogleApiClient googleApiClient;
    private Location mylocation;

    private final static int REQUEST_CHECK_SETTINGS_GPS = 0x1;
    private final static int REQUEST_ID_MULTIPLE_PERMISSIONS = 0x2;
    private boolean isLocationFetched = false;
    private DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd'T'HH:mm:ss", Locale.getDefault());

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SearchPharmacyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        searchPharmacyBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_pharmacy);
        getActivityComponent().inject(this);
        mPresenter.onAttach(SearchPharmacyActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        searchPharmacyBinding.setCallback(mPresenter);

        pharmacyArrList = new ArrayList<>();
        mAdapter = new SearchPharmacyListAdapter(this, pharmacyArrList, mPresenter);

        searchPharmacyBinding.pharmaResultsRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        searchPharmacyBinding.pharmaResultsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchPharmacyBinding.pharmaResultsRecyclerView.setAdapter(mAdapter);
        mPresenter.getPharmacyList();

//        setUpGClient();
    }

    @Override
    public void activityFinish() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    @Override
    public void onSuccessGetPharmacy(Pharmacy response) {
        // adding recipes to cart list
        pharmacyArrList.clear();
        pharmacyArrList.addAll(response.getStoreIdArrList());

        // refreshing recycler view
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Pharmacy.StoreListObj model) {
        PharmacyBottomSheetFragment bottomSheetDialogFragment = new PharmacyBottomSheetFragment(model, mPresenter);
        bottomSheetDialogFragment.show(getSupportFragmentManager(), "Bottom Sheet Dialog Fragment");
    }

    @Override
    public void onSetUpClick() {
        startActivity(DashboardActivity.getStartIntent(this));
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//        adminSetup();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
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
            googleApiClient.stopAutoManage(SearchPharmacyActivity.this);
            googleApiClient.disconnect();
        }
        mPresenter.onDetach();
    }

    @Override
    public void onNavigateHomeScreen() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
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
        int permissionLocation = ContextCompat.checkSelfPermission(SearchPharmacyActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (permissionLocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
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
        int permissionLocation = ContextCompat.checkSelfPermission(SearchPharmacyActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
            getMyLocation();
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(SearchPharmacyActivity.this,
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
                                            .checkSelfPermission(SearchPharmacyActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    try {
                                        status.startResolutionForResult(SearchPharmacyActivity.this,
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
