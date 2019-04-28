package com.darja.tmdb.util

import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers

fun clickMenuItem(resourceId: Int) {
    try {
        Espresso.onView(ViewMatchers.withId(resourceId)).perform(ViewActions.click())
    } catch (e: NoMatchingViewException) {
    }
}