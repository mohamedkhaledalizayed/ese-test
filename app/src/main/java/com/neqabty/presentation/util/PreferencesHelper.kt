package com.neqabty.presentation.util

import android.content.Context
import android.preference.PreferenceManager

class PreferencesHelper(context: Context) {
    companion object {
        const val DEVELOP_MODE = false
        private const val MAIN_SYNDICATE = "data.source.prefs.MAIN_SYNDICATE"
        private const val SUB_SYNDICATE = "data.source.prefs.SUB_SYNDICATE"
        private const val MOBILE = "data.source.prefs.MOBILE"
        private const val TOKEN = "data.source.prefs.TOKEN"
        private const val IS_REGISTERED = "data.source.prefs.IS_REGISTERED"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var mainSyndicate = preferences.getString(MAIN_SYNDICATE, "")
        set(value) = preferences.edit().putString(MAIN_SYNDICATE, value).apply()

    var subSyndicate = preferences.getString(SUB_SYNDICATE, "")
        set(value) = preferences.edit().putString(SUB_SYNDICATE, value).apply()


    var mobile = preferences.getString(MOBILE, "")
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var token = preferences.getString(TOKEN, "")
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var isRegistered = preferences.getBoolean(IS_REGISTERED, false)
        set(value) = preferences.edit().putBoolean(IS_REGISTERED, value).apply()


    fun isSyndicateChosen():Boolean{
        return mainSyndicate.isNotBlank()
    }

    fun isUserRegistered():Boolean{
        return isRegistered
    }

}