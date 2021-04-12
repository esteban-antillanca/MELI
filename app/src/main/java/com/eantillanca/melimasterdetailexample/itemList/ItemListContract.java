package com.eantillanca.melimasterdetailexample.itemList;

import com.eantillanca.melimasterdetailexample.BasePresenter;
import com.eantillanca.melimasterdetailexample.BaseView;
import com.eantillanca.melimasterdetailexample.data.Item;

import java.util.List;

/**
 * This is the contract between view and presenter of the Item List feature
 */

public interface ItemListContract {

    interface View extends BaseView<Presenter> {

        void showItems(List<Item> items);

        void showLoadingItemsError();

        void showNoItems();

        void showLoadingIndicator(Boolean loading);

        void showItemDetail(String itemID, String sellerID);

        void showNoEmptySearchString();

        void setFirstImageVisible(Boolean visible);


    }

    interface Presenter extends BasePresenter {

        void searchItems(String query);

        void openItemDetail(String itemID, String sellerID);

    }
}
