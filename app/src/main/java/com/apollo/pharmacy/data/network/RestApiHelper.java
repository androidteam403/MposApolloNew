package com.apollo.pharmacy.data.network;

import com.apollo.pharmacy.data.network.pojo.FeedItem;
import com.apollo.pharmacy.data.network.pojo.LoginRequest;
import com.apollo.pharmacy.data.network.pojo.UserProfile;
import com.apollo.pharmacy.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request);

    Single<WrapperResponse<List<FeedItem>>> getFeedList();
}
