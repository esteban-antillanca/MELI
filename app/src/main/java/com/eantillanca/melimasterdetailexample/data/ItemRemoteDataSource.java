package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

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
        Call<JsonObject> call = service.getItems("Motorola%20G6");
        call.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
               // callback.onItemsLoaded(response.body());
                System.out.println(response.body().toString());



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(call.toString());
                System.out.println(t.toString());

                callback.onDataNotAvailable();

            }

        });

    }



    public interface GetDataService {

        @GET("/sites/MLA/search?")
        Call<JsonObject> getItems(@Query("q") String query);


    }
}
