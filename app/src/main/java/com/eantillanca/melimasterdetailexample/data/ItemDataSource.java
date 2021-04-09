package com.eantillanca.melimasterdetailexample.data;


import java.util.List;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 */
public interface ItemDataSource {

    interface LoadItemsCallback {

        void onItemsLoaded(List<Item> items);
        void onDataNotAvailable();
    }

    interface LoadItemDetailCallback {
        void onItemDetailLoaded(ItemDetail itemDetail);
        void onDataNotAvailable();
    }

    interface LoadSellerCallback{
        void onSellerLoaded(Seller seller);
        void onDataNotAvailable();
    }

    void getItems(LoadItemsCallback callback, String query);
    void getItemDetail(LoadItemDetailCallback callback, String id);
    void getSeller(LoadSellerCallback callback, String id);



}
