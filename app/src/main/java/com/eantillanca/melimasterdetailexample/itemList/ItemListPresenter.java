package com.eantillanca.melimasterdetailexample.itemList;


import android.os.Bundle;

import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 2021-03-29.
 */
public class ItemListPresenter implements ItemListContract.Presenter {


    private final ItemRemoteDataSource dataSource;

    private final ItemListContract.View mItemsView;



    //all api methods return the complete updated list, so we're using a common callback
    private ItemDataSource.LoadItemsCallback callback;

    public ItemListPresenter(@NonNull ItemRemoteDataSource dataSource, @NonNull ItemListContract.View view) {
        this.dataSource = checkNotNull(dataSource);
        this.mItemsView = checkNotNull(view);

        mItemsView.setPresenter(this);

        callback = new ItemDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {

                if (items.size() == 0){
                    mItemsView.showNoItems();

                }
                else {
                    mItemsView.showItems(items);
                }

                mItemsView.showLoadingIndicator(false);

            }

            @Override
            public void onDataNotAvailable() {

                mItemsView.showLoadingItemsError();
                mItemsView.showNoItems();
            }

        };
    }

    @Override
    public void searchItems(String query) {
    //TODO implementation
        mItemsView.showLoadingIndicator(true);
        dataSource.getItems(callback, query);

    }

    @Override
    public void prepareItemDetail(Item item) {

        Bundle b = new Bundle();
        b.putString("title", item.getTitle());
        b.putString("id", item.getTitle());
        b.putString("price", item.getPrice());
        b.putString("thumbnail", item.getThumbnail());
        b.putString("sellerName", item.getSellerName());
        b.putString("qtySells", item.getQtySells());
        b.putString("condition", item.getCondition());

        mItemsView.showItemDetail(b);


    }

    @Override
    public void start() {

    }
}
