package com.eantillanca.melimasterdetailexample;

import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.eantillanca.melimasterdetailexample.itemList.ItemListActivity;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Esteban Antillanca on 2021-04-06.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ItemListViewTest {

    @Rule
    public ActivityTestRule<ItemListActivity> mActivityTestRule = new ActivityTestRule<>(ItemListActivity.class,false,false);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void emptySearch_showError() {
        launchSearchItemActivity();

        //Perform an empty query
        onView(withId(R.id.search_edit_text)).perform(clearText(), pressImeActionButton());

        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.no_empty_search)));
    }

    /**
     * Helper method to setup the Activity to be tested.
     */
    private void launchSearchItemActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(),
                ItemListActivity.class);

        mActivityTestRule.launchActivity(intent);
    }
    @Test
    public void noResults_showError() {
        launchSearchItemActivity();

        onView(withId(R.id.search_edit_text)).perform(replaceText("mnbbccxksjsjgiwieir"), pressImeActionButton());

        onView(withId(R.id.img_no_results))
                .check(matches(isDisplayed()));
    }

}
