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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Esteban Antillanca on 2020-01-14.
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
    public void emptyCounter_isNotSaved() {
        // Launch activity to add a new task
        launchCounterListActivity();

        // Add empty counter name
        //onView(withId(R.id.add_counter_input)).perform(clearText());

        //Try to save
        //onView(withId(R.id.add_counter_btn)).perform(click());

        //Verify expected message to be shown
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.add_counter_no_title_err)));
    }

    private void launchCounterListActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(),
                ItemListActivity.class);

        mActivityTestRule.launchActivity(intent);
    }
}
