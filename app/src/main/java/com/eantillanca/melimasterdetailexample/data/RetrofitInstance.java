package com.eantillanca.melimasterdetailexample.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eantillanca.melimasterdetailexample.Constants.BASE_URL;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 * Basic singleton instance for using the repository pattern. Retrofit is the tool selected for the remote data access
 */
public class RetrofitInstance {

    private static Retrofit retrofit;


    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
