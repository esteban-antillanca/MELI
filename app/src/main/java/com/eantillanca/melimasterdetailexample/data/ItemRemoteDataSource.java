package com.eantillanca.melimasterdetailexample.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
                        String price = objectInArray.get("price").toString();
                        String thumbnail = objectInArray.get("thumbnail").toString();
                        //Digging to find data from the seller
                        JsonObject eshopNode = objectInArray.getAsJsonObject("seller").getAsJsonObject("eshop");
                        String sellerName = eshopNode != null ? eshopNode.get("nick_name").toString() : "";
                        String qtySell = objectInArray.getAsJsonObject("seller").getAsJsonObject("seller_reputation").getAsJsonObject("transactions").get("completed").toString();;
                        String condition = objectInArray.get("condition").toString().equals("\"new\"")? "Nuevo" : "Usado";

                        Item new_item = new Item(title.replace("\"",""),id,price,thumbnail.replace("\"",""), sellerName.replace("\"",""), qtySell, condition);
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

    public interface GetDataService {

        @GET("/sites/MLA/search?")
        Call<JsonObject> getItems(@Query("q") String query);


    }
}
