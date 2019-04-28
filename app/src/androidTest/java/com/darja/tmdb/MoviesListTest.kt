package com.darja.tmdb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.darja.tmdb.util.RecyclerViewItemCountAssertion
import com.darja.tmdb.util.getVisibility
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MoviesListTest {
    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, true)

    @Test
    fun testLoadingProgress() {
        onView(withId(R.id.progressBar)).check(getVisibility(ViewMatchers.Visibility.VISIBLE))
        Thread.sleep(2500)
        onView(withId(R.id.progressBar)).check(getVisibility(ViewMatchers.Visibility.GONE))
    }

    @Test
    fun testLoadedItems() {
        onView(withId(R.id.moviesGrid)).check(RecyclerViewItemCountAssertion(0))
        Thread.sleep(2500)
        onView(withId(R.id.moviesGrid)).check(RecyclerViewItemCountAssertion(20))
    }

}
