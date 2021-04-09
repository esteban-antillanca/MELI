package com.eantillanca.melimasterdetailexample.itemDetail;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.IdlingResource;

import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.itemList.ItemListFragment;
import com.eantillanca.melimasterdetailexample.itemList.ItemListPresenter;
import com.eantillanca.melimasterdetailexample.util.ActivityUtils;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;

import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_ITEM;
import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_SELLER;

/**
 * Created by Esteban Antillanca on 4/3/21.
 */
public class ItemDetailActivity extends AppCompatActivity {

    private ItemDetailPresenter mItemDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ItemDetailFragment itemDetailFragment = (ItemDetailFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (itemDetailFragment == null){
            //Create the fragment
            itemDetailFragment = ItemDetailFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), itemDetailFragment, R.id.contentFrame);
        }

        String itemID = getIntent().getStringExtra(CURRENT_ITEM);
        String sellerID = getIntent().getStringExtra(CURRENT_SELLER);

        //create the presenter
        mItemDetailPresenter = new ItemDetailPresenter(itemDetailFragment, ItemRemoteDataSource.getInstance(),itemID, sellerID);
        itemDetailFragment.setPresenter(mItemDetailPresenter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return false;
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
