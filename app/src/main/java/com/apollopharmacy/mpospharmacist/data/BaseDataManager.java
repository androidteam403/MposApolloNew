package com.apollopharmacy.mpospharmacist.data;

import android.content.Context;

import com.apollopharmacy.mpospharmacist.data.network.RestApiHelper;
import com.apollopharmacy.mpospharmacist.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacist.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacist.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacist.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacist.data.network.pojo.WrapperResponse;
import com.apollopharmacy.mpospharmacist.data.prefs.PreferencesHelper;
import com.apollopharmacy.mpospharmacist.data.utils.LoggedInMode;
import com.apollopharmacy.mpospharmacist.di.ApplicationContext;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;
import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class BaseDataManager  implements DataManager {
    private static final String TAG = "BaseDataManager";


    private final Context mContext;
    private final PreferencesHelper mPreferencesHelper;
    private final RestApiHelper mApiHelper;

    @Inject
    public BaseDataManager(@ApplicationContext Context context,
                           PreferencesHelper preferencesHelper,
                           RestApiHelper apiHelper) {
        mContext = context;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {

    }

    @Override
    public void setUserLoggedOut() {
        logoutUser();
    }

    @Override
    public void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode, String userName, String email, String profilePicPath) {
        mPreferencesHelper.setUserName(userName);
        mPreferencesHelper.setUserEmail(email);
    }

    @Override
    public void storeGlobalJson(String json) {
        mPreferencesHelper.storeGlobalJson(json);
    }

    @Override
    public GetGlobalConfingRes getGlobalJson() {
        return mPreferencesHelper.getGlobalJson();
    }

    @Override
    public Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request) {
        return mApiHelper.doLoginApiCall(request);
    }

    @Override
    public Single<WrapperResponse<List<FeedItem>>> getFeedList() {
        return mApiHelper.getFeedList();
    }

    @Override
    public Single<WrapperResponse<GetTenderTypeRes>> getTenderType(String storeId, String dataAreaId, Object o) {
        return mApiHelper.getTenderType(storeId,dataAreaId,o);
    }

    @Override
    public String getUserName() {
        return mPreferencesHelper.getUserName();
    }

    @Override
    public String getUserId() {
        return mPreferencesHelper.getUserId();
    }

    @Override
    public void setUserName(String userName) {
        mPreferencesHelper.setUserName(userName);
    }

    @Override
    public void setUserId(String userId) {
        mPreferencesHelper.setUserId(userId);
    }

    @Override
    public void updateUserDetails(String name, String email, String mobile) {

    }

    @Override
    public String getUserEmail() {
        return mPreferencesHelper.getUserEmail();
    }

    @Override
    public void setUserEmail(String email) {
        mPreferencesHelper.setUserEmail(email);
    }

    @Override
    public String getUserMobile() {
        return mPreferencesHelper.getUserMobile();
    }

    @Override
    public void setUserMobile(String mobileNumber) {
        mPreferencesHelper.setUserMobile(mobileNumber);
    }

    @Override
    public void setKioskMode(boolean isKiosk) {
        mPreferencesHelper.setKioskMode(isKiosk);
    }

    @Override
    public boolean isKioskMode() {
        return mPreferencesHelper.isKioskMode();
    }

    @Override
    public boolean isAdminLoginFinish() {
        return mPreferencesHelper.isAdminLoginFinish();
    }

    @Override
    public void setAdminLoginFinish(boolean isLogin) {
        mPreferencesHelper.setAdminLoginFinish(isLogin);
    }

    @Override
    public String getAdminLoginId() {
        return mPreferencesHelper.getAdminLoginId();
    }

    @Override
    public void setAdminLoginId(String id) {
            mPreferencesHelper.setAdminLoginId(id);
    }

    @Override
    public boolean isAdminSetUpFinish() {
        return mPreferencesHelper.isAdminSetUpFinish();
    }

    @Override
    public void setAdminSetUpFinish(boolean isSetUp) {
        mPreferencesHelper.setAdminSetUpFinish(isSetUp);
    }

    @Override
    public String getStoreId() {
        return mPreferencesHelper.getStoreId();
    }

    @Override
    public void setStoreId(String id) {
        mPreferencesHelper.setStoreId(id);
    }

    @Override
    public String getDataAreaId() {
        return mPreferencesHelper.getDataAreaId();
    }

    @Override
    public void setDataAreaId(String dataAreaId) {
        mPreferencesHelper.setDataAreaId(dataAreaId);
    }

    @Override
    public String getTerminalId() {
        return mPreferencesHelper.getTerminalId();
    }

    @Override
    public void setTerminalId(String id) {
        mPreferencesHelper.setTerminalId(id);
    }

    @Override
    public boolean isUserLogin() {
        return mPreferencesHelper.isUserLogin();
    }

    @Override
    public void setUserLogin(boolean firstTime) {
        mPreferencesHelper.setUserLogin(firstTime);
    }

    @Override
    public void setVendorRes(String res) {
        mPreferencesHelper.setVendorRes(res);
    }

    @Override
    public VendorCheckRes getVendorRes() {
        return mPreferencesHelper.getVendorRes();
    }

    @Override
    public void adminLogOut() {
        mPreferencesHelper.adminLogOut();
    }

    @Override
    public void logoutUser() {
        mPreferencesHelper.logoutUser();
    }
}
