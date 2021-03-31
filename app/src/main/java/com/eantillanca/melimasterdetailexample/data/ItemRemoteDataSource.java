package com.eantillanca.melimasterdetailexample.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 */
public class ItemRemoteDataSource implements ItemDataSource {

    private static ItemRemoteDataSource INSTANCE;

    public static ItemRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE  = new ItemRemoteDataSource();
        }

        return INSTANCE;
    }

    private ItemRemoteDataSource(){}

    @Override
    public void getItems(final LoadItemsCallback callback) {

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<Item>> call = service.getCounters();
        call.enqueue(new Callback<List<Item>>(){
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response){
                callback.onItemsLoaded(response.body());

            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {

                callback.onDataNotAvailable();

            }

        });

    }



    public interface GetDataService {

        @GET("/api/v1/counters")
        Call<List<Item>> getCounters();

        @FormUrlEncoded
        @POST("/api/v1/counter")
        Call<List<Item>> createCounter(@Field("title") String title);

        @FormUrlEncoded
        @POST("/api/v1/counter/inc")
        Call<List<Item>> incCounter(@Field("id") String id);

        @FormUrlEncoded
        @POST("/api/v1/counter/dec")
        Call<List<Item>> decCounter(@Field("id") String id);

        @FormUrlEncoded
        @HTTP(method = "DELETE", path = "/api/v1/counter", hasBody = true)
        Call<List<Item>> delCounter(@Field("id") String id);

    }
}
