package com.darja.tmdb.util.ext

import androidx.annotation.AnimRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.darja.tmdb.R

fun AppCompatActivity.replaceFragmentSafely(
    fragment: Fragment,
    tag: String?,
    @IdRes containerViewId: Int,
    allowStateLoss: Boolean = false,
    addToBackStack: Boolean = false,
    @AnimRes enterAnimation: Int = 0,
    @AnimRes exitAnimation: Int = 0,
    @AnimRes popEnterAnimation: Int = 0,
    @AnimRes popExitAnimation: Int = 0
) {
    val ft = supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(enterAnimation, exitAnimation, popEnterAnimation, popExitAnimation)
        .replace(containerViewId, fragment, tag)
    if (addToBackStack) {
        ft.addToBackStack(tag)
    } else {
        ft.disallowAddToBackStack()
    }

    if (!supportFragmentManager.isStateSaved) {
        ft.commit()
    } else if (allowStateLoss) {
        ft.commitAllowingStateLoss()
    }
}

fun AppCompatActivity.replaceFragmentWithSlide(
    fragment: Fragment,
    tag: String,
    @IdRes containerViewId: Int,
    addToBackStack: Boolean = false
) {
    replaceFragmentSafely(fragment, tag, containerViewId,
        enterAnimation = R.anim.slide_in,
        exitAnimation = R.anim.fade_out,
        popEnterAnimation = R.anim.fade_in,
        popExitAnimation = R.anim.slide_out,
        addToBackStack = addToBackStack)
}