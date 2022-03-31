package com.apollopharmacy.mpospharmacistTest.data.network;

import com.apollopharmacy.mpospharmacistTest.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacistTest.data.network.pojo.WrapperResponse;
import com.apollopharmacy.mpospharmacistTest.ui.additem.model.GetTenderTypeRes;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RestApiManager implements RestApiHelper {

    NetworkService mService;

    @Inject
    public RestApiManager(NetworkService apiService) {
        mService = apiService;
    }

    @Override
    public Single<WrapperResponse<UserProfile>> doLoginApiCall(LoginRequest request) {
        return mService.doLoginApiCall(request);
    }

    @Override
    public Single<WrapperResponse<List<FeedItem>>> getFeedList() {
        return mService.getFeedList();
    }

    @Override
    public Single<WrapperResponse<GetTenderTypeRes>> getTenderType(String storeId,String dataAreaId,Object o) {
        return mService.GET_TENDER_TYPE_RES_CALL(storeId,dataAreaId,o);
    }
}
