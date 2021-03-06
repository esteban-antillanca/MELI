package com.eantillanca.melimasterdetailexample.itemList;

import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 2021-03-29.
 * Implementation class for the ItemList presenter. This class will handle the inputs from the ItemList view, request access to the
 * data repository and handle back to the view.
 */
public class ItemListPresenter implements ItemListContract.Presenter {


    private final ItemRemoteDataSource dataSource;

    private final ItemListContract.View mItemsView;

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
                EspressoIdlingResource.decrement();

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
        if(!query.equals("")) {

            EspressoIdlingResource.increment();
            mItemsView.setFirstImageVisible(false);
            mItemsView.showLoadingIndicator(true);
            dataSource.getItems(callback, query);
        }else{
            mItemsView.showNoEmptySearchString();
        }


    }

    @Override
    public void openItemDetail(String itemID, String sellerID) {
        checkNotNull(itemID);
        checkNotNull(sellerID);
        mItemsView.showItemDetail(itemID, sellerID);
    }

    @Override
    public void start() {

    }
}
