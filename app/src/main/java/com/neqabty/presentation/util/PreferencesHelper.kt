package com.neqabty.presentation.util

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(context: Context) {
    companion object {
        const val DEVELOP_MODE = false
        private const val MAIN_SYNDICATE = "data.source.prefs.MAIN_SYNDICATE"
        private const val SUB_SYNDICATE = "data.source.prefs.SUB_SYNDICATE"
        private const val USER = "data.source.prefs.USER"
        private const val NAME = "data.source.prefs.NAME"
        private const val MOBILE = "data.source.prefs.MOBILE"
        private const val TOKEN = "data.source.prefs.TOKEN"
        private const val IS_REGISTERED = "data.source.prefs.IS_REGISTERED"
        private const val NOTIFICATION_COUNT = "data.source.prefs.NOTIFICATION_COUNT"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var mainSyndicate = preferences.getInt(MAIN_SYNDICATE, 0)
        set(value) = preferences.edit().putInt(MAIN_SYNDICATE, value).apply()

    var subSyndicate = preferences.getInt(SUB_SYNDICATE, 0)
        set(value) = preferences.edit().putInt(SUB_SYNDICATE, value).apply()

    var mobile = preferences.getString(MOBILE, "")
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var token = preferences.getString(TOKEN, "")
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var isRegistered = preferences.getBoolean(IS_REGISTERED, false)
        set(value) = preferences.edit().putBoolean(IS_REGISTERED, value).apply()

    var user = preferences.getString(USER, "")
        set(value) = preferences.edit().putString(USER, value).apply()

    var name = preferences.getString(NAME, "")
        set(value) = preferences.edit().putString(NAME, value).apply()

    var notificationsCount = preferences.getInt(NOTIFICATION_COUNT, 0)
        set(value) = preferences.edit().putInt(NOTIFICATION_COUNT, value).apply()

    fun isSyndicateChosen(): Boolean {
        return mainSyndicate != 0
    }

//    fun isUserRegistered():Boolean{
//        return isRegistered
//    }
}