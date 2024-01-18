package com.dictionmaster.search

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar

class ApiCallManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "ApiCallPrefs"
        private const val KEY_CALL_COUNT = "callCount"
        private const val KEY_LAST_CALL_TIMESTAMP = "lastCallTimestamp"
        private const val MAX_CALLS_PER_DAY = 10
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun canMakeApiCall(): Boolean {
        // Check if the last call was made on a different day
        val today = Calendar.getInstance()
        val lastCallTimestamp = prefs.getLong(KEY_LAST_CALL_TIMESTAMP, 0)

        val lastCall = Calendar.getInstance().apply {
            timeInMillis = lastCallTimestamp
        }
        val callCount = if (today.get(Calendar.DAY_OF_YEAR) != lastCall.get(Calendar.DAY_OF_YEAR)) 0
        else prefs.getInt(KEY_CALL_COUNT, 0)


        return callCount < MAX_CALLS_PER_DAY
    }

    fun updateCallCountAndTimestamp() {
        val editor = prefs.edit()

        // Increment call count
        val callCount = prefs.getInt(KEY_CALL_COUNT, 0) + 1
        editor.putInt(KEY_CALL_COUNT, callCount)

        // Update last call timestamp
        val currentTimestamp = System.currentTimeMillis()
        editor.putLong(KEY_LAST_CALL_TIMESTAMP, currentTimestamp)

        editor.apply()
    }
}
