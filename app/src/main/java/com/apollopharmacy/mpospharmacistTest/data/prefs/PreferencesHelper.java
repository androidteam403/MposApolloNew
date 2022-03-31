package com.apollopharmacy.mpospharmacistTest.data.prefs;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.VendorCheckRes;
import com.apollopharmacy.mpospharmacistTest.ui.home.ui.dashboard.model.ListDataEntity;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.adapter.RackAdapter;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.pickupprocess.model.RacksDataResponse;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.AllowedPaymentModeRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetGlobalConfingRes;
import com.apollopharmacy.mpospharmacistTest.ui.pharmacistlogin.model.GetTrackingWiseConfing;

import java.util.List;

public interface PreferencesHelper {

    void setKioskMode(boolean isKiosk);

    void setOpenScreens(boolean isKiosk);


    boolean isKioskMode();

    boolean isOpenScreens();


    boolean isAdminLoginFinish();

    void setAdminLoginFinish(boolean isLogin);

    String getAdminLoginId();

    void setAdminLoginId(String id);

    String getEposURL();

    void setEposURL(String url);

    void storeEposUrl(boolean url);

    boolean isEposUrl();

    boolean isAdminSetUpFinish();

    void setAdminSetUpFinish(boolean isSetUp);

    String getStoreId();

    void setStoreId(String id);

    String getBranchPhoneNumber();

    void setBranchPhoneNumber(String num);

    String getDataAreaId();

    void setDataAreaId(String dataAreaId);

    String getTerminalId();

    void setTerminalId(String id);

    boolean isUserLogin();

    void setUserLogin(boolean firstTime);

    boolean isIntializePos();

    void setIntializePos(boolean intializePos);

    void logoutUser();

    void setUserMobile(String mobile);

    void setUserEmail(String email);

    void setUserName(String name);

    void setUserId(String userId);

    void updateUserDetails(String name, String email, String mobile);

    String getUserMobile();

    String getUserEmail();

    String getUserName();

    String getUserId();


    void storeGlobalJson(String json);

    GetGlobalConfingRes getGlobalJson();

    void storeTrackingWiseConfiguration(GetTrackingWiseConfing trackingWiseConfing);

    GetTrackingWiseConfing getTrackingWiseConfing();

    void storeAllowedPaymentMethod(AllowedPaymentModeRes allowedPaymentModeRes);

    AllowedPaymentModeRes getAllowedPaymentModeRes();

    void setVendorRes(String res);

    VendorCheckRes getVendorRes();

    void adminLogOut();

    ListDataEntity getlistDataEntity();

    void setListDataEntity(ListDataEntity listDataEntity);

    ListDataEntity getPosiflexlistDataEntity();

    void setPosiflexListDataEntity(ListDataEntity posiflexListDataEntity);

    void setFullFillmentList(List<RacksDataResponse.FullfillmentDetail> fullfillmentDetailList);

    List<RacksDataResponse.FullfillmentDetail> getFullFillmentList();

    void setfullFillListOfListFiltered(List<List<RackAdapter.RackBoxModel.ProductData>> fullFillListOfListFiltered);

    List<List<RackAdapter.RackBoxModel.ProductData>> getfullFillListOfListFiltered();

}
