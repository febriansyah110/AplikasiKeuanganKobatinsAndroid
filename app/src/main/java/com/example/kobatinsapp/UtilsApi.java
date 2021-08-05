package com.example.kobatinsapp;

public class UtilsApi {

    public static BaseApiService getAPIService(){

        return RetrofitClient.getClient("http://192.168.1.35/keuangan_kbtns/").create(BaseApiService.class);

    }
}
