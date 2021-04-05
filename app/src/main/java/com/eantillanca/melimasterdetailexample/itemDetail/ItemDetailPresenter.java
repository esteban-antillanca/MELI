package com.eantillanca.melimasterdetailexample.itemDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.Item;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_ITEM;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
class ItemDetailPresenter implements ItemDetailContract.Presenter{

    private ItemDetailContract.View mItemDetailView;
    private Item currentItem;

    public ItemDetailPresenter(@NonNull ItemDetailContract.View view, Bundle b) {
        this.mItemDetailView = checkNotNull(view);

        mItemDetailView.setPresenter(this);

        currentItem = b.getParcelable(CURRENT_ITEM);

    }


    @Override
    public void goBack() {

    }

    @Override
    public void start() {
        mItemDetailView.showItem(currentItem);
    }
}
