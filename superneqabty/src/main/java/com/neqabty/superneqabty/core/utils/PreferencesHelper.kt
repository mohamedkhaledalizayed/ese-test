package com.neqabty.superneqabty.core.utils

import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    private val preferences: SharedPreferences
) {

    init {
        instance = this
    }

    companion object {
        @get:Synchronized
        lateinit var instance: PreferencesHelper

        const val DEVELOP_MODE = false
        private const val IS_NOTIFICATIONS_ENABLED = "data.source.prefs.IS_NOTIFICATIONS_ENABLED"
        private const val MAIN_SYNDICATE = "data.source.prefs.MAIN_SYNDICATE"
        private const val NAME = "data.source.prefs.NAME"
        private const val MOBILE = "data.source.prefs.MOBILE"
    }

    var isNotificationsEnabled
        get() = preferences.getBoolean(IS_NOTIFICATIONS_ENABLED, true)
        set(value) = preferences.edit().putBoolean(IS_NOTIFICATIONS_ENABLED, value).apply()

    var mainSyndicate
        get() = preferences.getInt(MAIN_SYNDICATE, -1)
        set(value) = preferences.edit().putInt(MAIN_SYNDICATE, value).apply()

    var mobile
        get() = preferences.getString(MOBILE, "")!!
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var name
        get() = preferences.getString(NAME, "")!!
        set(value) = preferences.edit().putString(NAME, value).apply()

    fun isSyndicateChosen(): Boolean {
        return mainSyndicate != 0
    }

    fun clearAll():Unit{
        return preferences.edit().clear().apply()
    }
}