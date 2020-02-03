package com.apollo.pharmacy.data.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("addUser.php")
    public Call<ResponseBody> registerUser(@Field("name") String name,
                                           @Field("email") String password,
                                           @Field("password") String email, @Field("image") String image);

    @FormUrlEncoded
    @POST("updateUser.php")
    public Call<ResponseBody> updateUser(@Field("id") Integer id,
                                         @Field("name") String password,
                                         @Field("image") String image);

    @FormUrlEncoded
    @POST("updatePasswordUser.php")
    public Call<ResponseBody> updatePasswordUser(@Field("id") Integer id,
                                                 @Field("password") String password
    );

//    @GET("780a4b58-2bca-11ea-8649-0d2b23060fa9")
//    Call<List<Recipe>> getProducts();

//    @GET("381352b5-3e69-11ea-be6c-f9dd68604caf")
//    Call<Pharmacy> getPharmacyList();
}
