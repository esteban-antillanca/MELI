package com.eantillanca.melimasterdetailexample.itemList;

import com.eantillanca.melimasterdetailexample.BasePresenter;
import com.eantillanca.melimasterdetailexample.BaseView;
import com.eantillanca.melimasterdetailexample.data.Item;

import java.util.List;

public interface ItemListContract {

    interface View extends BaseView<Presenter> {

        void showItems(List<Item> items);

        void showLoadingItemsError();

        void showNoItems();


    }

    interface Presenter extends BasePresenter {

        void searchItems(String query);



    }
}
