package com.apollopharmacy.mpospharmacist.data.network;

import com.apollopharmacy.mpospharmacist.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacist.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacist.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacist.data.network.pojo.WrapperResponse;

import java.util.List;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request);

    Single<WrapperResponse<List<FeedItem>>> getFeedList();
}
