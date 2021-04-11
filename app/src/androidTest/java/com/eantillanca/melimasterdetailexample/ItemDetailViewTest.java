package com.eantillanca.melimasterdetailexample;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.eantillanca.melimasterdetailexample.itemDetail.ItemDetailActivity;
import com.eantillanca.melimasterdetailexample.itemList.ItemListActivity;
import com.eantillanca.melimasterdetailexample.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_ITEM;
import static com.eantillanca.melimasterdetailexample.Constants.CURRENT_SELLER;
import static org.hamcrest.Matchers.not;

/**
 * Created by Esteban Antillanca on 4/10/21.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class ItemDetailViewTest {

    @Rule
    public ActivityTestRule<ItemDetailActivity> mActivityTestRule = new ActivityTestRule<>(ItemDetailActivity.class,false,false);

    @Before
    public void registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void buyBtnCLick_shouldShowMessage() {
        // Launch activity to add a new task
        launchItemDetailActivity();

        // Do empty search
        onView(withId(R.id.button)).perform(click());

        //Verify expected message to be shown
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(R.string.coming_soon_message)));
    }

    @Test
    public void progressBar_shouldNotBeVisible_afterItemLoad() {
        // Launch activity to add a new task
        launchItemDetailActivity();

        //Verify expected message to be shown
        onView(withId(R.id.loading_spinner))
                .check(matches(not(isDisplayed())));
    }

    private void launchItemDetailActivity() {
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(),
                ItemDetailActivity.class);

        intent.putExtra(CURRENT_ITEM, "MLA897752664");
        intent.putExtra(CURRENT_SELLER, "90205574");

        mActivityTestRule.launchActivity(intent);
    }
    @Test
    public void noResults_showError() {

        launchItemDetailActivity();

        onView(withId(R.id.search_edit_text)).perform(replaceText("mnbbccxksjsjgiwieir"), pressImeActionButton());

        onView(withId(R.id.img_no_results))
                .check(matches(isDisplayed()));
    }

}
