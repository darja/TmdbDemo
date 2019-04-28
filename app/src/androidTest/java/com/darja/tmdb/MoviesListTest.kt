package com.darja.tmdb

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.darja.tmdb.ui.common.BindingViewHolder
import com.darja.tmdb.util.RecyclerViewItemCountAssertion
import com.darja.tmdb.util.getVisibility
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@Suppress("BooleanLiteralArgument")
@RunWith(AndroidJUnit4::class)
class MoviesListTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testLoadingProgress() {
        onView(withId(R.id.progressBar)).check(getVisibility(Visibility.VISIBLE))
        Thread.sleep(2500)
        onView(withId(R.id.progressBar)).check(getVisibility(Visibility.GONE))
    }

    @Test
    fun testLoadedItems() {
        onView(withId(R.id.moviesGrid)).check(RecyclerViewItemCountAssertion(0))
        Thread.sleep(2500)
        onView(withId(R.id.moviesGrid)).check(RecyclerViewItemCountAssertion(20))
    }

    @Test
    fun testOpenFirstMovieDetails() {
        Thread.sleep(2500)
        onView(withId(R.id.moviesGrid)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BindingViewHolder>(0, click())
        )
        Thread.sleep(2500)
        onView(withId(R.id.title)).check(matches(withText("Hellboy")))
        onView(withId(R.id.description)).check(matches(withText("Hellboy comes to England, where he must defeat Nimue, Merlin's consort and the Blood Queen. But their battle will bring about the end of the world, a fate he desperately tries to turn away.")))
    }

    @Test
    fun testOpenMovieDetailsFromTheMiddle() {
        Thread.sleep(2500)
        onView(withId(R.id.moviesGrid)).perform(
            RecyclerViewActions.scrollToPosition<BindingViewHolder>(10)
        )
        onView(withId(R.id.moviesGrid)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BindingViewHolder>(10, click())
        )
        Thread.sleep(2500)
        onView(withId(R.id.title)).check(matches(withText("Dumbo")))
        onView(withId(R.id.description)).check(matches(withText("A young elephant, whose oversized ears enable him to fly, helps save a struggling circus, but when the circus plans a new venture, Dumbo and his friends discover dark secrets beneath its shiny veneer.")))
    }

    @Test
    fun testToolbarTitles() {
        onView(allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(Toolbar::class.java)))
        ).check(
            matches(withText("Movies"))
        )

        Thread.sleep(2500)
        onView(withId(R.id.moviesGrid)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BindingViewHolder>(0, click())
        )
        onView(allOf(
            isAssignableFrom(TextView::class.java),
            withParent(isAssignableFrom(Toolbar::class.java)))
        ).check(
            matches(withText("Movie Details"))
        )
    }
}
