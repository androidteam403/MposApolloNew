package com.apollopharmacy.mpospharmacistTest.data;

import android.content.Context;

import com.apollopharmacy.mpospharmacistTest.data.network.RestApiHelper;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.WrapperResponse;
import com.apollopharmacy.mpospharmacistTest.data.prefs.PreferencesHelper;
import com.apollopharmacy.mpospharmacistTest.data.utils.LoggedInMode;
import com.apollopharmacy.mpospharmacistTest.di.ApplicationContext;
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
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class BaseDataManager implements DataManager {
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
    public void storeHBPConfiRes(String json) {
        mPreferencesHelper.storeHBPConfiRes(json);
    }

    @Override
    public HBPConfigResponse getHBPConfigRes() {
        return mPreferencesHelper.getHBPConfigRes();
    }

    @Override
    public void storeTrackingWiseConfiguration(GetTrackingWiseConfing trackingWiseConfing) {
        mPreferencesHelper.storeTrackingWiseConfiguration(trackingWiseConfing);
    }

    @Override
    public GetTrackingWiseConfing getTrackingWiseConfing() {
        return mPreferencesHelper.getTrackingWiseConfing();
    }

    @Override
    public void storeAllowedPaymentMethod(AllowedPaymentModeRes allowedPaymentModeRes) {
        mPreferencesHelper.storeAllowedPaymentMethod(allowedPaymentModeRes);
    }

    @Override
    public AllowedPaymentModeRes getAllowedPaymentModeRes() {
        return mPreferencesHelper.getAllowedPaymentModeRes();
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
        return mApiHelper.getTenderType(storeId, dataAreaId, o);
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
    public void setOpenScreens(boolean isopenscreen) {
        mPreferencesHelper.setOpenScreens(isopenscreen);
    }

    @Override
    public boolean isOpenScreens() {
        return mPreferencesHelper.isOpenScreens();
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
    public String getEposURL() {
        return mPreferencesHelper.getEposURL();
    }

    @Override
    public void setEposURL(String url) {
        mPreferencesHelper.setEposURL(url);
    }

    @Override
    public void storeEposUrl(boolean url) {
        mPreferencesHelper.storeEposUrl(url);
    }

    @Override
    public boolean isEposUrl() {
        return mPreferencesHelper.isEposUrl();
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
    public String getBranchPhoneNumber() {
        return mPreferencesHelper.getBranchPhoneNumber();
    }

    @Override
    public void setBranchPhoneNumber(String num) {
        mPreferencesHelper.setBranchPhoneNumber(num);
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
    public boolean isIntializePos() {
        return mPreferencesHelper.isIntializePos();
    }

    @Override
    public void setIntializePos(boolean intializePos) {
        mPreferencesHelper.setIntializePos(intializePos);
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
    public ListDataEntity getlistDataEntity() {
        return mPreferencesHelper.getlistDataEntity();
    }

    @Override
    public void setListDataEntity(ListDataEntity listDataEntity) {
        mPreferencesHelper.setListDataEntity(listDataEntity);
    }

    @Override
    public ListDataEntity getPosiflexlistDataEntity() {
        return mPreferencesHelper.getPosiflexlistDataEntity();
    }

    @Override
    public void setPosiflexListDataEntity(ListDataEntity posiflexListDataEntity) {
        mPreferencesHelper.setPosiflexListDataEntity(posiflexListDataEntity);
    }

    @Override
    public void logoutUser() {
        mPreferencesHelper.logoutUser();
    }

    @Override
    public void setFullFillmentList(List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList) {
        mPreferencesHelper.setFullFillmentList(fullfillmentDetailList);
    }

    @Override
    public List<RacksDataResponse.FullfillmentDetail> getFullFillmentList() {
        return mPreferencesHelper.getFullFillmentList();
    }

    @Override
    public void setfullFillListOfListFiltered(List<List<RackAdapter.RackBoxModel.ProductData>> fullFillListOfListFiltered) {
        mPreferencesHelper.setfullFillListOfListFiltered(fullFillListOfListFiltered);
    }

    @Override
    public List<List<RackAdapter.RackBoxModel.ProductData>> getfullFillListOfListFiltered() {
        return mPreferencesHelper.getfullFillListOfListFiltered();
    }

    @Override
    public void setTotalOmsTransactionHeader(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        mPreferencesHelper.setTotalOmsTransactionHeader(totalOmsHeaderList);
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getTotalOmsHeaderList() {
        return mPreferencesHelper.getTotalOmsHeaderList();
    }

    @Override
    public void setTotalOmsTransactionHeaderObj(List<OMSTransactionHeaderResModel.OMSHeaderObj> totalOmsHeaderList) {
        mPreferencesHelper.setTotalOmsTransactionHeaderObj(totalOmsHeaderList);
    }

    @Override
    public List<OMSTransactionHeaderResModel.OMSHeaderObj> getTotalOmsHeaderListObj() {
        return mPreferencesHelper.getTotalOmsHeaderListObj();
    }

    @Override
    public void setLabelSize(String labelSize) {
        mPreferencesHelper.setLabelSize(labelSize);
    }

    @Override
    public String getLabelSize() {
        return mPreferencesHelper.getLabelSize();
    }

    @Override
    public void setTenderTypeResultEntity(GetTenderTypeRes.GetTenderTypeResultEntity getTenderTypeResultEntity) {
        mPreferencesHelper.setTenderTypeResultEntity(getTenderTypeResultEntity);
    }

    @Override
    public GetTenderTypeRes.GetTenderTypeResultEntity getTenderTypeResultEntity() {
        return mPreferencesHelper.getTenderTypeResultEntity();
    }

    @Override
    public void setGlobalTotalOmsTransactionHeader(List<TransactionHeaderResponse.OMSHeader> totalOmsHeaderList) {
        mPreferencesHelper.setGlobalTotalOmsTransactionHeader(totalOmsHeaderList);
    }

    @Override
    public List<TransactionHeaderResponse.OMSHeader> getGlobalTotalOmsHeaderList() {
        return mPreferencesHelper.getGlobalTotalOmsHeaderList();
    }

    @Override
    public void setMaxMinOrders(ArrayList<UserModel._DropdownValueBean> dropdownValue) {
        mPreferencesHelper.setMaxMinOrders(dropdownValue);
    }

    @Override
    public List<UserModel._DropdownValueBean> getMaxMinOrders() {
        return mPreferencesHelper.getMaxMinOrders();
    }
}
