package com.eantillanca.melimasterdetailexample.itemDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemDetail;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.data.Seller;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_ITEM;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
class ItemDetailPresenter implements ItemDetailContract.Presenter{

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

        sellerCallback = new ItemDataSource.LoadSellerCallback() {

            @Override
            public void onSellerLoaded(Seller mseller) {
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


    @Override
    public void goBack() {

    }

    @Override
    public void start() {

        mItemDetailView.showLoadingIndicator(true);

        dataSource.getItemDetail(itemDetailCallback, this.currentItemID);

    }
}
