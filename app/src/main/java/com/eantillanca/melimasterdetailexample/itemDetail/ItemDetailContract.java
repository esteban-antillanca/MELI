package com.eantillanca.melimasterdetailexample.itemDetail;

import com.eantillanca.melimasterdetailexample.BasePresenter;
import com.eantillanca.melimasterdetailexample.BaseView;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.data.ItemDetail;
import com.eantillanca.melimasterdetailexample.data.Seller;
import com.eantillanca.melimasterdetailexample.itemList.ItemListContract;

import java.util.List;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
public interface ItemDetailContract {

    interface View extends BaseView<ItemDetailContract.Presenter> {

        void showPaymentAction();
        void showItem(ItemDetail item, Seller seller);
        void showLoadingIndicator(Boolean loading);
        void showLoadingItemDetailError();



    }

    interface Presenter extends BasePresenter {


    }
}
