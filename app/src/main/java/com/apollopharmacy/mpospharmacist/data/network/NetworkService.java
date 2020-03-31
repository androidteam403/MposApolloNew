package com.apollopharmacy.mpospharmacist.data.network;

import com.apollopharmacy.mpospharmacist.data.network.pojo.FeedItem;
import com.apollopharmacy.mpospharmacist.data.network.pojo.LoginRequest;
import com.apollopharmacy.mpospharmacist.data.network.pojo.UserProfile;
import com.apollopharmacy.mpospharmacist.data.network.pojo.WrapperResponse;
import com.apollopharmacy.mpospharmacist.ui.additem.model.GetTenderTypeRes;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created on : Jan 19, 2019
 * Author     : AndroidWave
 * Email    : info@androidwave.com
 */
public interface NetworkService {
    /**
     * @return Observable feed response
     */
    @GET("feed.json")
    Single<WrapperResponse<List<FeedItem>>> getFeedList();


    @POST("login")
    Single<WrapperResponse<UserProfile>> doLoginApiCall(@Body LoginRequest mRequest);

    @POST("SalesTransactionService.svc/GetTenderType/{storeId}/{DataAreaId}")
    Single<WrapperResponse<GetTenderTypeRes>> GET_TENDER_TYPE_RES_CALL (@Path("storeId") String storeId, @Path("DataAreaId") String dataAreaId, @Body  Object o);

}
