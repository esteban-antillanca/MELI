package com.eantillanca.melimasterdetailexample.itemDetail;

import com.eantillanca.melimasterdetailexample.BasePresenter;
import com.eantillanca.melimasterdetailexample.BaseView;
import com.eantillanca.melimasterdetailexample.data.Item;
import com.eantillanca.melimasterdetailexample.itemList.ItemListContract;

import java.util.List;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
interface ItemDetailContract {

    interface View extends BaseView<ItemDetailContract.Presenter> {

        void showPaymentAction();
        void showItem(Item item);



    }

    interface Presenter extends BasePresenter {
        void goBack();


    }
}
