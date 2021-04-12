package com.eantillanca.melimasterdetailexample.data;


import java.util.List;

/**
 * Created by Esteban Antillanca on 2020-01-08.
 * Basic contract for a repository pattern implementation. This allows to use a single-source-of-truth and also
 * enabling a future enhancement with local storage, for example. In this version of the app, only remote data is used.
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
