package com.eantillanca.melimasterdetailexample.itemDetail;


import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemDetail;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.data.Seller;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;


import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 4/3/21.
 * Implementation of the Item Detail Presenter. It will receive input from the view, request data from the data repository, and
 * ask for refreshing back to the UI through the view.
 */
public class ItemDetailPresenter implements ItemDetailContract.Presenter{

    private ItemDetailContract.View mItemDetailView;

    private final ItemRemoteDataSource dataSource;

    private ItemDataSource.LoadItemDetailCallback itemDetailCallback;

    private ItemDataSource.LoadSellerCallback sellerCallback;

    private String currentItemID;

    private String currentSellerID;

    private ItemDetail currentItemDetail;

    private Seller seller;






    public ItemDetailPresenter(@NonNull ItemDetailContract.View view, @NonNull ItemRemoteDataSource dataSource, String  currentItemID, String sellerID) {

        this.dataSource = checkNotNull(dataSource);

        this.mItemDetailView = checkNotNull(view);

        this.currentItemID = currentItemID;

        this.currentSellerID = sellerID;

        mItemDetailView.setPresenter(this);

        /**
         * Callbacks to listen Item Detail and Seller Detail data fetch. After Item Detail data has been fetched, the presenter
         * immediately request the fetch of seller data.
         */
        sellerCallback = new ItemDataSource.LoadSellerCallback() {

            @Override
            public void onSellerLoaded(Seller mseller) {

                EspressoIdlingResource.decrement();

                seller = mseller;
                mItemDetailView.showItem(currentItemDetail, seller);
                mItemDetailView.showLoadingIndicator(false);
            }

            @Override
            public void onDataNotAvailable() {

                mItemDetailView.showLoadingItemDetailError();
                mItemDetailView.showLoadingIndicator(false);

            }


        };

        itemDetailCallback = new ItemDataSource.LoadItemDetailCallback() {

            @Override
            public void onItemDetailLoaded(ItemDetail itemDetail) {

                currentItemDetail = itemDetail;
                dataSource.getSeller(sellerCallback, currentSellerID);

            }

            @Override
            public void onDataNotAvailable() {

                mItemDetailView.showLoadingItemDetailError();
            }

        };

    }

    /**
     * Item detail is fetched right after loading the view, given the Item ID received from the List View
     */
    @Override
    public void start() {

        EspressoIdlingResource.increment();

        mItemDetailView.showLoadingIndicator(true);

        dataSource.getItemDetail(itemDetailCallback, this.currentItemID);

    }
}
