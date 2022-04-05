package com.apollopharmacy.mpospharmacistTest.ui.pbas.pojo;

;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderRequest;
import com.apollopharmacy.mpospharmacistTest.ui.pbas.openorders.model.TransactionHeaderResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("SalesTransactionService.svc/GetOMSTransactionHeader")
    Call<TransactionHeaderResponse> GET_OMS_TRANSACTION_HEADER(@Body TransactionHeaderRequest transactionHeaderRequest);

}
