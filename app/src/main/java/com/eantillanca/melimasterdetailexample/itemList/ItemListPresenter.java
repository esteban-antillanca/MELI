package com.eantillanca.melimasterdetailexample.itemList;


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

    private final ItemListContract.View mCountersView;



    //all api methods return the complete updated list, so we're using a common callback
    private ItemDataSource.LoadItemsCallback callback;

    public ItemListPresenter(@NonNull ItemRemoteDataSource dataSource, @NonNull ItemListContract.View view) {
        this.dataSource = checkNotNull(dataSource);
        this.mCountersView = checkNotNull(view);

        mCountersView.setPresenter(this);

        callback = new ItemDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {

                if (items.size() == 0){
                    mCountersView.showNoItems();

                }
                else {
                    mCountersView.showItems(items);
                }

            }

            @Override
            public void onDataNotAvailable() {

                mCountersView.showLoadingItemsError();
                mCountersView.showNoItems();
            }

        };
    }

    @Override
    public void searchItems() {
    //TODO implementation
        dataSource.getItems(callback);

    }

    @Override
    public void start() {

        searchItems();

    }
}
