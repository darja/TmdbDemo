package com.darja.tmdb.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object ScreenUtil {
    fun hideSoftKeyboard(activity: Activity?) {
        val view = activity?.currentFocus ?: return
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
