package com.eantillanca.melimasterdetailexample.itemDetail;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDataSource;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.itemList.ItemListContract;

import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
class ItemDetailPresenter implements ItemDetailContract.Presenter{

    private ItemDetailContract.View mItemDetailView;

    public ItemDetailPresenter(@NonNull ItemDetailContract.View view, Bundle b) {
        this.mItemDetailView = checkNotNull(view);

        mItemDetailView.setPresenter(this);

        Item item = new Item(b.getString("title"), b.getString("id"),b.getString("price"),b.getString("thumbnail"),b.getString("sellerName"),b.getString("qtySells"),b.getString("condition"));

        mItemDetailView.showItem(item);

    }


    @Override
    public void goBack() {

    }

    @Override
    public void start() {


    }
}
