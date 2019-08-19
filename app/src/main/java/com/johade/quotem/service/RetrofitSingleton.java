package com.johade.quotem.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static String BASE_URL = "https://personal-web-mobile.herokuapp.com/mobile/";
    private String token;
    private static Retrofit instance;

    public synchronized static Retrofit getRetrofitInstance() {
        if (instance == null) {
            instance = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}
