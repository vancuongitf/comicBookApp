package com.example.cuongcaov.clonedata.draft;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit.
 *
 * @author CuongCV
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://freestory.000webhostapp.com";

    private static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static APIService getApiService(){
        return getClient().create(APIService.class);
    }
}