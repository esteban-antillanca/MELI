package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 * Implementation of the data source of the app, and a part of the Model in MVP. This implementation is for remote data only,
 * and it has all the logic for consuming the given API's. Basic serialization and deserialization was used in order to simplify
 * the development
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
    public void getItems(final LoadItemsCallback callback, String query) {

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);
        String search_query = "";
        try {
             search_query = URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8.toString());
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        Call<JsonObject> call = service.getItems(search_query);
        call.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response){
                    JsonArray items_array = response.body().getAsJsonArray("results");
                    ArrayList<Item> items = new ArrayList<Item>();

                    for (int i = 0; i < items_array.size(); i++)
                    {
                        JsonObject objectInArray = items_array.get(i).getAsJsonObject();
                        String title = objectInArray.get("title").toString();
                        String id = objectInArray.get("id").toString();
                        String price = "$" + objectInArray.get("price").toString();
                        String thumbnail = objectInArray.get("thumbnail").toString();
                        String sellerID = objectInArray.get("seller").getAsJsonObject().get("id").toString();

                        Item new_item = new Item(title.replace("\"",""),id.replace("\"",""),price,thumbnail.replace("\"",""), sellerID);
                        items.add(new_item);
                    }

                    callback.onItemsLoaded(items);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                callback.onDataNotAvailable();

            }

        });

    }

    @Override
    public void getItemDetail(LoadItemDetailCallback callback, String id) {
        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        Call<JsonArray> call = service.getItemDetail(id);
        call.enqueue(new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response){

                JsonObject object = response.body().get(0).getAsJsonObject().get("body").getAsJsonObject();
                String title = object.get("title").toString();
                String price = "$" + object.get("price").toString();
                String condition = object.get("condition").toString().equals("\"new\"")? "Nuevo" : "Usado";

                JsonArray arrayPictures = object.get("pictures").getAsJsonArray();
                ArrayList<String> pictures = new ArrayList<String>();
                for ( int i = 0; i < arrayPictures.size(); i++)
                {
                    pictures.add(arrayPictures.get(i).getAsJsonObject().get("secure_url").toString().replace("\"",""));

                }

                callback.onItemDetailLoaded(new ItemDetail(title.replace("\"",""),condition,price, pictures.toArray(new String[0])));
                System.out.println("");
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                callback.onDataNotAvailable();

            }

        });

    }

    @Override
    public void getSeller(LoadSellerCallback callback, String id) {

        GetDataService service = RetrofitInstance.getRetrofitInstance().create(GetDataService.class);

        Call<JsonArray> call = service.getSeller(id);
        call.enqueue(new Callback<JsonArray>(){
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response){

                JsonObject object = response.body().get(0).getAsJsonObject().get("body").getAsJsonObject();
                String nickname = object.get("nickname").toString();
                String total = object.get("seller_reputation").getAsJsonObject().get("transactions").getAsJsonObject().get("total").toString();

                callback.onSellerLoaded(new Seller(nickname.replace("\"",""),total));
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                callback.onDataNotAvailable();

            }

        });

    }

    public interface GetDataService {

        @GET("/sites/MLA/search?")
        Call<JsonObject> getItems(@Query("q") String query);

        @GET("/items?")
        Call<JsonArray> getItemDetail(@Query("ids") String ids);

        @GET("/users?")
        Call<JsonArray> getSeller(@Query("ids") String ids);


    }
}
