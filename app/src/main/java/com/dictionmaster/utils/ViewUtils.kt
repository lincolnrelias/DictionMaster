package com.dictionmaster.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object ViewUtils {

    fun hideKeyboardAndExecute(context: Context, view: View?, action: () -> Unit) {
        if (view != null) {
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            view.post {
                action()
            }
        } else {
            action()
        }
    }
}
