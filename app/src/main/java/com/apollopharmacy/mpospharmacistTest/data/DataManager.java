package com.apollopharmacy.mpospharmacistTest.data;

import com.apollopharmacy.mpospharmacistTest.data.network.RestApiHelper;
import com.apollopharmacy.mpospharmacistTest.data.prefs.PreferencesHelper;
import com.apollopharmacy.mpospharmacistTest.data.utils.LoggedInMode;

public interface DataManager extends PreferencesHelper, RestApiHelper {
    void updateApiHeader(Long userId, String accessToken);

    void setUserLoggedOut();

    void updateUserInfo(
            String accessToken,
            Long userId,
            LoggedInMode loggedInMode,
            String userName,
            String email,
            String profilePicPath);

    void storeGlobalJson(String json);
}
