package com.apollopharmacy.mpospharmacist.ui.storesetup;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.apollopharmacy.mpospharmacist.R;
import com.apollopharmacy.mpospharmacist.data.db.model.StoreDetails;
import com.apollopharmacy.mpospharmacist.data.db.realm.RealmController;
import com.apollopharmacy.mpospharmacist.databinding.ActivityStoreSetupBinding;
import com.apollopharmacy.mpospharmacist.ui.base.BaseActivity;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.PharmacistLoginActivity;
import com.apollopharmacy.mpospharmacist.ui.storesetup.dialog.GetStoresDialog;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.DeviceSetupResModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreListResponseModel;
import com.apollopharmacy.mpospharmacist.ui.storesetup.model.StoreSetupModel;
import com.apollopharmacy.mpospharmacist.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

import io.realm.Realm;

public class StoreSetupActivity extends BaseActivity implements StoreSetupMvpView {

    @Inject
    StoreSetupMvpPresenter<StoreSetupMvpView> mPresenter;
    ActivityStoreSetupBinding activityStoreSetupBinding;
    private String deviceId;
    private StoreListResponseModel storeListObj = null;
    private StoreListResponseModel.StoreListObj selectedStoreId = null;
    private String deviceType = "";
    private String registerDate = "";
    private String userID = "";
    double latitude = 0.0;
    double longitude = 0.0;

    public static Intent getStartIntent(Context context, double latitude, double longitude, String userID) {
        Intent intent = new Intent(context, StoreSetupActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("user_id", userID);
        return intent;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityStoreSetupBinding = DataBindingUtil.setContentView(this, R.layout.activity_store_setup);
        getActivityComponent().inject(this);
        mPresenter.onAttach(StoreSetupActivity.this);
        setUp();
    }

    @Override
    protected void setUp() {
        activityStoreSetupBinding.setCallback(mPresenter);
        mPresenter.getStoreList();
        deviceId = CommonUtils.getDeviceId(this);

        if (getIntent() != null) {
            latitude = (double) getIntent().getSerializableExtra("latitude");
            longitude = (double) getIntent().getSerializableExtra("longitude");
            userID = (String) getIntent().getSerializableExtra("user_id");
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            registerDate = currentDate.format(c);
            deviceType = android.os.Build.MODEL;
            if (latitude != 0.0 && longitude != 0.0) {
                StoreSetupModel storeSetupModel = new StoreSetupModel();
                storeSetupModel.setMacId(deviceId);
                storeSetupModel.setDeviceName(deviceType);
                storeSetupModel.setDeviceType(Build.DEVICE);
                storeSetupModel.setStoreDate(registerDate);
                storeSetupModel.setStoreLatitude(latitude);
                storeSetupModel.setStoreLongitude(longitude);
                activityStoreSetupBinding.setStoremodel(storeSetupModel);
            }
        }
    }

    @Override
    public void onSelectStoreSearch() {
        if (storeListObj != null) {
            if (storeListObj.getStoreListArr().size() > 0) {
                GetStoresDialog dialog = GetStoresDialog.newInstance();
                dialog.setStoreDetailsMvpView(this);
                dialog.setStoreListArray(storeListObj.getStoreListArr());
                dialog.show(getSupportFragmentManager(), "");
            }
        }
    }

    @Override
    public void onSaveBtnClick() {
        if (validations()) {
            mPresenter.handleStoreSetupService();
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
        adminSetup();
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
    }

    @Override
    public String getDeviceId() {
        return deviceId;
    }

    @Override
    public String getFcmKey() {
        return getSharedPreferences("ApolloMPOS", MODE_PRIVATE).getString("fcm_token", "empty");
    }

    @Override
    public String getStoreId() {
        return selectedStoreId.getStoreId();
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
}
