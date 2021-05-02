package com.codetest.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

object KeyUtil {

    private const val KEY = "api_key"

    private fun preferences(applicationContext: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(applicationContext)

    fun getKey(applicationContext: Context): String {
        preferences(applicationContext).getString(KEY, null)?.let {
            return it
        } ?: kotlin.run {
            val apiKey = UUID.randomUUID().toString()
            preferences(applicationContext)
                .edit()
                .putString(KEY, apiKey)
                .apply()
            return apiKey
        }
    }
}