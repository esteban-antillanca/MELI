package com.eantillanca.melimasterdetailexample.itemList;

import android.os.Bundle;

import com.eantillanca.melimasterdetailexample.BasePresenter;
import com.eantillanca.melimasterdetailexample.BaseView;
import com.eantillanca.melimasterdetailexample.data.Item;

import java.util.List;

public interface ItemListContract {

    interface View extends BaseView<Presenter> {

        void showItems(List<Item> items);

        void showLoadingItemsError();

        void showNoItems();

        void showLoadingIndicator(Boolean loading);

        void showItemDetail(Item item);

        void showNoEmptySearchString();

        void setFirstImageVisible(Boolean visible);


    }

    interface Presenter extends BasePresenter {

        void searchItems(String query);

        void openItemDetail(Item item);

    }
}
