package com.apollopharmacy.mpospharmacist.data.prefs;

import com.apollopharmacy.mpospharmacist.ui.pharmacistlogin.model.GetGlobalConfingRes;

public interface PreferencesHelper {

    boolean isAdminLoginFinish();

    void setAdminLoginFinish(boolean isLogin);

    String getAdminLoginId();

    void setAdminLoginId(String id);

    boolean isAdminSetUpFinish();

    void setAdminSetUpFinish(boolean isSetUp);

    String getStoreId();

    void setStoreId(String id);

    String getDataAreaId();

    void setDataAreaId(String dataAreaId);

    String getTerminalId();

    void setTerminalId(String id);

    boolean isUserLogin();

    void setUserLogin(boolean firstTime);

    void logoutUser();

    void setUserMobile(String mobile);

    void setUserEmail(String email);

    void setUserName(String name);

    void updateUserDetails(String name, String email, String mobile);

    String getUserMobile();

    String getUserEmail();

    String getUserName();

    void storeGlobalJson(String json);

    GetGlobalConfingRes getGlobalJson();
}
