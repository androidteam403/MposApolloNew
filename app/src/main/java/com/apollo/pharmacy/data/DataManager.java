package com.apollo.pharmacy.data;

import com.apollo.pharmacy.data.network.RestApiHelper;
import com.apollo.pharmacy.data.prefs.PreferencesHelper;
import com.apollo.pharmacy.data.utils.LoggedInMode;

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
}
