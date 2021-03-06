package com.eantillanca.melimasterdetailexample.itemList;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.IdlingResource;

import com.eantillanca.melimasterdetailexample.R;
import com.eantillanca.melimasterdetailexample.data.ItemRemoteDataSource;
import com.eantillanca.melimasterdetailexample.util.ActivityUtils;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;


/**
 * Created by Esteban Antillanca on 2021-03-29.
 */
public class ItemListActivity extends AppCompatActivity {

    private ItemListPresenter mItemsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_list_act);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ItemListFragment itemListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (itemListFragment == null){
            //Create the fragment
            itemListFragment = ItemListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), itemListFragment, R.id.contentFrame);
        }

        //create the presenter

        mItemsPresenter = new ItemListPresenter(ItemRemoteDataSource.getInstance(), itemListFragment);
        itemListFragment.setPresenter(mItemsPresenter);

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
