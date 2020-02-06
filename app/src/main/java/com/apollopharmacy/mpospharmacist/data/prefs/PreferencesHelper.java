package com.apollopharmacy.mpospharmacist.data.prefs;

public interface PreferencesHelper {
    boolean isAdminSetUpFinish();

    void setAdminSetUpFinish(boolean isSetUp);

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
}
