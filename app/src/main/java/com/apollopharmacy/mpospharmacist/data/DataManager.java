package com.apollopharmacy.mpospharmacist.data;

import com.apollopharmacy.mpospharmacist.data.network.RestApiHelper;
import com.apollopharmacy.mpospharmacist.data.prefs.PreferencesHelper;
import com.apollopharmacy.mpospharmacist.data.utils.LoggedInMode;

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
