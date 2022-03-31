package com.apollopharmacy.mpospharmacistTest.data.network;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.WrapperResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;

import java.util.List;

import io.reactivex.Single;

public interface RestApiHelper {

    Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request);

    Single<WrapperResponse<List<FeedItem>>> getFeedList();

    Single<WrapperResponse<GetTenderTypeRes>> getTenderType(String storeId,String dataAreaId,Object o);
}
