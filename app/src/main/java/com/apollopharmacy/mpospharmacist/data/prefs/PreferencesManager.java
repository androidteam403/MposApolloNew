package com.apollopharmacy.mpospharmacist.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.di.AdminPreferenceInfo;
import com.apollopharmacy.mpospharmacist.di.ApplicationContext;
import com.apollopharmacy.mpospharmacist.di.PreferenceInfo;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.google.gson.Gson;

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
    private static final String PREF_KEY_ADMIN_LOGIN = "PREF_KEY_ADMIN_LOGIN";
    private static final String PREF_KEY_ADMIN_LOGIN_ID = "PREF_KEY_ADMIN_LOGIN_ID";
    private static final String PREF_KEY_ADMIN_SET_UP = "PREF_KEY_ADMIN_SET_UP";
    private static final String PREF_KEY_STORE_ID = "PREF_KEY_STORE_ID";
    private static final String PREF_KEY_TERMINAL_ID ="PREF_KEY_TERMINAL_ID";
    private static final String PREF_KEY_USER_LOGIN = "PREF_KEY_USER_LOGIN";
    private static final String PREF_KEY_DATA_AREA_ID = "PREF_KEY_DATA_AREA_ID";
    private static final String PREF_KEY_VENDOR_RES = "PREF_KEY_VENDOR_RES";

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
        return mPrefs.getString(PREF_KEY_USER_ID,"");
    }

    @Override
    public void storeGlobalJson(String json) {
        mAdminPrefs.edit().putString(PREF_KEY_GLOBAL_JSON,json).apply();
    }

    @Override
    public GetGlobalConfingRes getGlobalJson() {
        Gson gson = new Gson();
        String json = mAdminPrefs.getString(PREF_KEY_GLOBAL_JSON,"");
        return gson.fromJson(json, GetGlobalConfingRes.class);
    }

    @Override
    public void setUserName(String userName) {
        mPrefs.edit().putString(PREF_KEY_USER_NAME, userName).apply();
    }

    @Override
    public void setUserId(String userId) {
        mPrefs.edit().putString(PREF_KEY_USER_ID,userId).apply();
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
    public boolean isAdminLoginFinish() {
        return mAdminPrefs.getBoolean(PREF_KEY_ADMIN_LOGIN,false);
    }

    @Override
    public void setAdminLoginFinish(boolean isLogin) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_ADMIN_LOGIN,isLogin).apply();
    }

    @Override
    public String getAdminLoginId() {
        return mAdminPrefs.getString(PREF_KEY_ADMIN_LOGIN_ID,"");
    }

    @Override
    public void setAdminLoginId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_ADMIN_LOGIN_ID,id).apply();
    }

    @Override
    public boolean isAdminSetUpFinish() {
        return mAdminPrefs.getBoolean(PREF_KEY_ADMIN_SET_UP,false);
    }

    @Override
    public void setAdminSetUpFinish(boolean isSetUp) {
        mAdminPrefs.edit().putBoolean(PREF_KEY_ADMIN_SET_UP,isSetUp).apply();
    }

    @Override
    public String getStoreId() {
        return mAdminPrefs.getString(PREF_KEY_STORE_ID,"");
    }

    @Override
    public void setStoreId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_STORE_ID,id).apply();
    }

    @Override
    public String getDataAreaId() {
        return mAdminPrefs.getString(PREF_KEY_DATA_AREA_ID,"");
    }

    @Override
    public void setDataAreaId(String dataAreaId) {
        mAdminPrefs.edit().putString(PREF_KEY_DATA_AREA_ID,dataAreaId).apply();
    }

    @Override
    public String getTerminalId() {
        return mAdminPrefs.getString(PREF_KEY_TERMINAL_ID,"");
    }

    @Override
    public void setTerminalId(String id) {
        mAdminPrefs.edit().putString(PREF_KEY_TERMINAL_ID,id).apply();
    }

    @Override
    public boolean isUserLogin() {
        return mPrefs.getBoolean(PREF_KEY_USER_LOGIN,false);
    }

    @Override
    public void setUserLogin(boolean firstTime) {
        mPrefs.edit().putBoolean(PREF_KEY_USER_LOGIN,firstTime).apply();
    }

    @Override
    public void setVendorRes(String res) {
        mPrefs.edit().putString(PREF_KEY_VENDOR_RES,res).apply();
    }

    @Override
    public VendorCheckRes getVendorRes() {
        Gson gson = new Gson();
        String json = mPrefs.getString(PREF_KEY_VENDOR_RES,"");
        return gson.fromJson(json, VendorCheckRes.class);
    }

    @Override
    public void adminLogOut() {
        mAdminPrefs.edit().clear().apply();
    }

    @Override
    public void logoutUser() {
        mPrefs.edit().clear().apply();
    }


}
