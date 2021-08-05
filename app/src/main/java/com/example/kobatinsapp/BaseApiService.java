package com.example.kobatinsapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("android/login.php")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("android/pemasukan.php")
    Call<ResponseBody> pemasukanRequest(@Field("nama") String nama,
                                        @Field("jumlah") String jumlah);
}

