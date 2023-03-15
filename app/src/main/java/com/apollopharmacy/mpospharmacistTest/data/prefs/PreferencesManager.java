package com.apollopharmacy.mpospharmacistTest.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacistTest.di.AdminPreferenceInfo;
import com.apollopharmacy.mpospharmacistTest.di.ApplicationContext;
import com.apollopharmacy.mpospharmacistTest.di.PreferenceInfo;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.eprescriptionslist.model.OMSTransactionHeaderResModel;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.HBPConfigResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

public class PreferencesManager implements PreferencesHelper {

    private static final String PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_LOGGED_IN_MODE";
    private static final String PREF_KEY_USER_ID = "PREF_KEY_USER_ID";
    private static final String PREF_KEY_USER_MOBILE = "PREF_KEY_CURRENT_MOBILE";
    private static final String PREF_KEY_USER_NAME = "PREF_KEY_CURRENT_USER_NAME";
    private static final String PREF_KEY_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL";
    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_FIRST_TIME = "PREF_KEY_FIRST_TIME";
    private static final String PREF_KEY_USER_PROFILE_PIC_URL = "PREF_KEY_USER_PROFILE_PIC_URL";
    private static final String PREF_KEY_COACH_MARK = "PREF_KEY_COACH_MARK";
    private static final String PREF_KEY_GLOBAL_JSON = "PREF_KEY_GLOBAL_JSON";
    private static final String PREF_KEY_HBP_JSON = "PREF_KEY_HBP_JSON";
    private static final String PREF_KEY_ADMIN_LOGIN = "PREF_KEY_ADMIN_LOGIN";
    private static final String PREF_KEY_ADMIN_LOGIN_ID = "PREF_KEY_ADMIN_LOGIN_ID";
    private static final String PREF_KEY_ADMIN_SET_UP = "PREF_KEY_ADMIN_SET_UP";
    private static final String PREF_KEY_STORE_ID = "PREF_KEY_STORE_ID";
    private static final String PREF_KEY_TERMINAL_ID = "PREF_KEY_TERMINAL_ID";
    private static final String PREF_KEY_USER_LOGIN = "PREF_KEY_USER_LOGIN";
    private static final String PREF_KEY_DATA_AREA_ID = "PREF_KEY_DATA_AREA_ID";
    private static final String PREF_KEY_VENDOR_RES = "PREF_KEY_VENDOR_RES";
    private static final String PREF_KEY_KIOSK_MODE = "PREF_KEY_KIOSK_MODE";
    private static final String PREF_KEY_ALLOWED_PAYMENT = "PREF_KEY_ALLOWED_PAYMENT";
    private static final String PREF_KEY_TRACKING_CONFIG = "PREF_KEY_TRACKING_CONFIG";
    private static final String PREF_KEY_EPOS_URL = "PREF_KEY_EPOS_URL";
    private static final String PREF_KEY_BOOLEAN_EPOS_URL = "PREF_KEY_BOOLEAN_EPOS_URL";
    private static final String PREF_KEY_BRANCH_PHONE_NUMBER = "PREF_KEY_BRANCH_PHONE_NUMBER";
    private static final String PREF_KEY_ROE_ENTITY_DATA = "PREF_KEY_ROE_ENTITY_DATA";
    private static final String PREF_KEY_POSIFLEXT_DATA = "PREF_KEY_POSIFLEXT_DATA";
    private static final String PREF_KEY_INTIALIZE_FIRST = "PREF_KEY_INTIALIZE_FIRST";
    private static final String PREF_KEY_OPEN_SCREENS = "PREF_KEY_OPEN_SCREENS";

    private static final String PREF_KEY_FULLFILLMENT_DETAILS = "PREF_KEY_FULLFILLMENT_DETAILS";
    private static final String PREF_KEY_FULLFILLMENT_LIST_OF_LIST_DETAILS = "PREF_KEY_FULLFILLMENT_LIST_OF_LIST_DETAILS";
    private static final String PREF_KEY_TOTAL_OMS_HEADER_LIST = "PREF_KEY_TOTAL_OMS_HEADER_LIST";
    private static final String PREF_KEY_TOTAL_OMS_HEADER_LIST_OBJ = "PREF_KEY_TOTAL_OMS_HEADER_LIST_OBJ";

    private static final String PREF_KEY_PAPER_LABEL_SIZE = "PREF_KEY_PAPER_LABEL_SIZE";
    private static final String PREF_KEY_GET_TENDER_TYPE_RESULT_JSON = "PREF_KEY_GET_TENDER_TYPE_RESULT_JSON";

    private static final String PREF_KEY_GLOBAL_TOTAL_OMS_HEADER_LIST = "PREF_KEY_GLOBAL_TOTAL_OMS_HEADER_LIST";

    private final SharedPreferences mPrefs;
    private final SharedPreferences mAdminPrefs;
    private Context mAppContext;

    @Inject
    public PreferencesManager(@ApplicationContext Context context,
                              @PreferenceInfo String prefFileName, @AdminPreferenceInfo String adminPrefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
        mAdminPrefs = context.getSharedPreferences(adminPrefFileName, Context.MODE_PRIVATE);
        mAppContext = context;
    }

    @Override
    public String getUserName() {
        return mPrefs.getString(PREF_KEY_USER_NAME, null);
    }

    @Override
    public String getUserId() {
        return mPrefs.getString(PREF_KEY_USER_ID, "");
    }

    @Override
    public void storeGlobalJson(String json) {
        mPrefs.edit().putString(PREF_KEY_GLOBAL_JSON, json).apply();
    }

    @Override
    public GetGlobalConfingRes getGlobalJson() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_GLOBAL_JSON, "");
        return gson.fromJson(json, GetGlobalConfingRes.class);
    }

    @Override
    public void storeHBPConfiRes(String json) {
        mPrefs.edit().putString(PREF_KEY_HBP_JSON, json).apply();
    }

    @Override
    public HBPConfigResponse getHBPConfigRes() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_HBP_JSON, "");
        return gson.fromJson(json, HBPConfigResponse.class);
    }

    @Override
    public void storeTrackingWiseConfiguration(GetTrackingWiseConfing trackingWiseConfing) {
        Gson gson = new Gson();
        String json = gson.toJson(trackingWiseConfing);
        mPrefs.edit().putString(PREF_KEY_TRACKING_CONFIG, json).apply();
    }

    @Override
    public GetTrackingWiseConfing getTrackingWiseConfing() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_TRACKING_CONFIG, "");
        return gson.fromJson(json, GetTrackingWiseConfing.class);
    }

    @Override
    public void storeAllowedPaymentMethod(AllowedPaymentModeRes allowedPaymentModeRes) {
        Gson gson = new Gson();
        String json = gson.toJson(allowedPaymentModeRes);
        mPrefs.edit().putString(PREF_KEY_ALLOWED_PAYMENT, json).apply();
    }

    @Override
    public AllowedPaymentModeRes getAllowedPaymentModeRes() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_ALLOWED_PAYMENT, "");
        return gson.fromJson(json, AllowedPaymentModeRes.class);
    }

    @Override
    public void setUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public void setUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_ID, userId).apply();
    }

    @Override
    public void updateUserDetails(String name, String email, String mobile) {

    }

    @Override
    public String getUserEmail() {
        return mPrefs.getString(PREF_KEY_USER_EMAIL, null);
    }

    @Override
    public void setUserEmail(String email) {
        mPrefs.edit().putString(PREF_KEY_USER_EMAIL, email).apply();
    }

    @Override
    public String getUserMobile() {
        return mPrefs.getString(PREF_KEY_USER_MOBILE, "");
    }

    @Override
    public void setUserMobile(String mobileNumber) {
        mPrefs.edit().putString(PREF_KEY_USER_MOBILE, mobileNumber).apply();
    }

    @Override
    public void setKioskMode(boolean isKiosk) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_KIOSK_MODE, isKiosk).apply();
    }

    @Override
    public boolean isKioskMode() {
        return mAdminPrefs.getBoolean(PREF_KEY_KIOSK_MODE, false);
    }

    @Override
    public void setOpenScreens(boolean isopenscreen) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_OPEN_SCREENS, isopenscreen).apply();
    }

    @Override
    public boolean isOpenScreens() {
        return mAdminPrefs.getBoolean(PREF_KEY_OPEN_SCREENS, false);
    }


    @Override
    public boolean isAdminLoginFinish() {
        return mAdminPrefs.getBoolean(PREF_KEY_ADMIN_LOGIN, false);
    }

    @Override
    public void setAdminLoginFinish(boolean isLogin) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_ADMIN_LOGIN, isLogin).apply();
    }

    @Override
    public String getAdminLoginId() {
        return mAdminPrefs.getString(PREF_KEY_ADMIN_LOGIN_ID, "");
    }

    @Override
    public void setAdminLoginId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_ADMIN_LOGIN_ID, id).apply();
    }

    @Override
    public String getEposURL() {
        return mAdminPrefs.getString(PREF_KEY_EPOS_URL, "");
    }

    @Override
    public void setEposURL(String url) {
        mAdminPrefs.edit().putString(PREF_KEY_EPOS_URL, url).apply();
    }

    @Override
    public void storeEposUrl(boolean url) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_BOOLEAN_EPOS_URL, url).apply();
    }

    @Override
    public boolean isEposUrl() {
        return mAdminPrefs.getBoolean(PREF_KEY_BOOLEAN_EPOS_URL, false);
    }

    @Override
    public boolean isAdminSetUpFinish() {
        return mAdminPrefs.getBoolean(PREF_KEY_ADMIN_SET_UP, false);
    }

    @Override
    public void setAdminSetUpFinish(boolean isSetUp) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_ADMIN_SET_UP, isSetUp).apply();
    }

    @Override
    public String getStoreId() {
        return mAdminPrefs.getString(PREF_KEY_STORE_ID, "");
    }

    @Override
    public void setStoreId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_STORE_ID, id).apply();
    }

    @Override
    public String getBranchPhoneNumber() {
        return mAdminPrefs.getString(PREF_KEY_BRANCH_PHONE_NUMBER, "");
    }

    @Override
    public void setBranchPhoneNumber(String num) {
        mAdminPrefs.edit().putString(PREF_KEY_BRANCH_PHONE_NUMBER, num).apply();
    }

    @Override
    public String getDataAreaId() {
        return mAdminPrefs.getString(PREF_KEY_DATA_AREA_ID, "");
    }

    @Override
    public void setDataAreaId(String dataAreaId) {
        mAdminPrefs.edit().putString(PREF_KEY_DATA_AREA_ID, dataAreaId).apply();
    }

    @Override
    public String getTerminalId() {
        return mAdminPrefs.getString(PREF_KEY_TERMINAL_ID, "");
    }

    @Override
    public void setTerminalId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_TERMINAL_ID, id).apply();
    }

    @Override
    public boolean isUserLogin() {
        return mPrefs.getBoolean(PREF_KEY_USER_LOGIN, false);
    }

    @Override
    public void setUserLogin(boolean firstTime) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGIN, firstTime).apply();
    }

    @Override
    public boolean isIntializePos() {
        return mPrefs.getBoolean(PREF_KEY_INTIALIZE_FIRST, false);
    }

    @Override
    public void setIntializePos(boolean intializePos) {
        mPrefs.edit().putBoolean(PREF_KEY_INTIALIZE_FIRST, intializePos).apply();
    }

    @Override
    public void setVendorRes(String res) {
        mPrefs.edit().putString(PREF_KEY_VENDOR_RES, res).apply();
    }

    @Override
    public VendorCheckRes getVendorRes() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_VENDOR_RES, "");
        return gson.fromJson(json, VendorCheckRes.class);
    }

    @Override
    public void adminLogOut() {
        mAdminPrefs.edit().clear().apply();
    }


    @Override
    public ListDataEntity getlistDataEntity() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_ROE_ENTITY_DATA, "");
        return gson.fromJson(json, ListDataEntity.class);
    }

    @Override
    public void setListDataEntity(ListDataEntity listDataEntity) {
        Gson gson = new Gson();
        String json = gson.toJson(listDataEntity);
        mPrefs.edit().putString(PREF_KEY_ROE_ENTITY_DATA, json).apply();
    }

    @Override
    public ListDataEntity getPosiflexlistDataEntity() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_POSIFLEXT_DATA, "");
        return gson.fromJson(json, ListDataEntity.class);
    }

    @Override
    public void setPosiflexListDataEntity(ListDataEntity posiflexListDataEntity) {
        Gson gson = new Gson();
        String json = gson.toJson(posiflexListDataEntity);
        mPrefs.edit().putString(PREF_KEY_POSIFLEXT_DATA, json).apply();
    }

    @Override
    public void logoutUser() {
        mPrefs.edit().clear().apply();
    }

    @Override
    public void setFullFillmentList(List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList) {
        mPrefs.edit().putString(PREF_KEY_FULLFILLMENT_DETAILS, new Gson().toJson(fullfillmentDetailList)).apply();
    }

    @Override
    public List<RacksDataResponse.FullfillmentDetail> getFullFillmentList() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_FULLFILLMENT_DETAILS, "");
        Type type = new TypeToken<List<RacksDataResponse.FullfillmentDetail>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setfullFillListOfListFiltered(List<List<RackAdapter.RackBoxModel.ProductData>> fullFillListOfListFiltered) {
        mPrefs.edit().putString(PREF_KEY_FULLFILLMENT_LIST_OF_LIST_DETAILS, new Gson().toJson(fullFillListOfListFiltered)).apply();
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> getfullFillListOfListFiltered() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_FULLFILLMENT_LIST_OF_LIST_DETAILS, "");
        Type type = new TypeToken<List<List<RackAdapter.RackBoxModel.ProductData>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setTotalOmsTransactionHeader(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        mPrefs.edit().putString(PREF_KEY_TOTAL_OMS_HEADER_LIST, new Gson().toJson(totalOmsHeaderList)).apply();
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_TOTAL_OMS_HEADER_LIST, "");
        Type type = new TypeToken<List<TransactionHeaderResponse.OMSHeader>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setTotalOmsTransactionHeaderObj(List<OMSTransactionHeaderResModel.OMSHeaderObj> totalOmsHeaderList) {
        mPrefs.edit().putString(PREF_KEY_TOTAL_OMS_HEADER_LIST_OBJ, new Gson().toJson(totalOmsHeaderList)).apply();
    }

    @Override
    public List<OMSTransactionHeaderResModel.OMSHeaderObj> getTotalOmsHeaderListObj() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_TOTAL_OMS_HEADER_LIST_OBJ, "");
        Type type = new TypeToken<List<OMSTransactionHeaderResModel.OMSHeaderObj>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void setLabelSize(String labelSize) {
        mPrefs.edit().putString(PREF_KEY_PAPER_LABEL_SIZE, labelSize).apply();
    }

    @Override
    public String getLabelSize() {
        return mPrefs.getString(PREF_KEY_PAPER_LABEL_SIZE, "A4");
    }

    @Override
    public void setTenderTypeResultEntity(GetTenderTypeRes.GetTenderTypeResultEntity getTenderTypeResultEntity) {
        Gson gson = new Gson();
        String json = gson.toJson(getTenderTypeResultEntity);
        mPrefs.edit().putString(PREF_KEY_GET_TENDER_TYPE_RESULT_JSON, json).apply();
    }

    @Override
    public GetTenderTypeRes.GetTenderTypeResultEntity getTenderTypeResultEntity() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_GET_TENDER_TYPE_RESULT_JSON, "");
        return gson.fromJson(json, GetTenderTypeRes.GetTenderTypeResultEntity.class);
    }

    @Override
    public void setGlobalTotalOmsTransactionHeader(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        mPrefs.edit().putString(PREF_KEY_GLOBAL_TOTAL_OMS_HEADER_LIST, new Gson().toJson(totalOmsHeaderList)).apply();
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getGlobalTotalOmsHeaderList() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_GLOBAL_TOTAL_OMS_HEADER_LIST, "");
        Type type = new TypeToken<List<TransactionHeaderResponse.OMSHeader>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
