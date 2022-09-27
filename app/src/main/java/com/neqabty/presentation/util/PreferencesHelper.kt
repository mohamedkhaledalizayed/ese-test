package com.neqabty.presentation.util

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
        private const val IS_INTRO_SKIPPED = "data.source.prefs.IS_INTRO_SKIPPED"
        private const val IS_FORCE_LOGOUT = "data.source.prefs.IS_FORCE_SIGNIN"
        private const val IS_NOTIFICATIONS_ENABLED = "data.source.prefs.IS_NOTIFICATIONS_ENABLED"
        private const val MAIN_SYNDICATE = "data.source.prefs.MAIN_SYNDICATE"
        private const val SUB_SYNDICATE = "data.source.prefs.SUB_SYNDICATE"
        private const val PP = "data.source.prefs.PP"
        private const val USER = "data.source.prefs.USER"
        private const val NAME = "data.source.prefs.NAME"
        private const val MOBILE = "data.source.prefs.MOBILE"
        private const val TOKEN = "data.source.prefs.TOKEN"
        private const val JWT = "data.source.prefs.JWT"
        private const val IS_REGISTERED = "data.source.prefs.IS_REGISTERED"
        private const val NOTIFICATION_COUNT = "data.source.prefs.NOTIFICATION_COUNT"
        private const val USER_TYPE = "data.source.prefs.USER_TYPE"
        private const val FONT_SIZE = "data.source.prefs.FONT_SIZE"
        private const val USER_SYNDICATE = "data.source.prefs.USER_SYNDICATE"
        private const val USER_SECTION = "data.source.prefs.USER_SECTION"
        private const val USER_SYNDICATE_ID = "data.source.prefs.USER_SYNDICATE_ID"
        private const val USER_SECTION_ID = "data.source.prefs.USER_SECTION_ID"
    }

    var isNotificationsEnabled
        get() = preferences.getBoolean(IS_NOTIFICATIONS_ENABLED, true)
        set(value) = preferences.edit().putBoolean(IS_NOTIFICATIONS_ENABLED, value).apply()

    var isIntroSkipped
        get() = preferences.getBoolean(IS_INTRO_SKIPPED, false)
        set(value) = preferences.edit().putBoolean(IS_INTRO_SKIPPED, value).apply()

    var isForceLogout
        get() = preferences.getBoolean(IS_FORCE_LOGOUT, true)
        set(value) = preferences.edit().putBoolean(IS_FORCE_LOGOUT, value).apply()

    var mainSyndicate
        get() = preferences.getInt(MAIN_SYNDICATE, 0)
        set(value) = preferences.edit().putInt(MAIN_SYNDICATE, value).apply()

    var subSyndicate
        get() = preferences.getInt(SUB_SYNDICATE, 0)
        set(value) = preferences.edit().putInt(SUB_SYNDICATE, value).apply()

    var mobile
        get() = preferences.getString(MOBILE, "")!!
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var token
        get() = preferences.getString(TOKEN, "")!!
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var jwt
        get() = preferences.getString(JWT, "")
        set(value) = preferences.edit().putString(JWT, value).apply()

    var isRegistered
        get() = preferences.getBoolean(IS_REGISTERED, false)
        set(value) = preferences.edit().putBoolean(IS_REGISTERED, value).apply()

    var photo
        get() = preferences.getString(PP, "")!!
        set(value) = preferences.edit().putString(PP, value).apply()

    var user
        get() = preferences.getString(USER, "")!!
        set(value) = preferences.edit().putString(USER, value).apply()

    var name
        get() = preferences.getString(NAME, "")!!
        set(value) = preferences.edit().putString(NAME, value).apply()

    var notificationsCount
        get() = preferences.getInt(NOTIFICATION_COUNT, 0)
        set(value) = preferences.edit().putInt(NOTIFICATION_COUNT, value).apply()

    var userType
        get() = preferences.getString(USER_TYPE, "")
        set(value) = preferences.edit().putString(USER_TYPE, value).apply()

    var fontSize
        get() = preferences.getString(FONT_SIZE, "medium")
        set(value) = preferences.edit().putString(FONT_SIZE, value).apply()

    var userSyndicate
        get() = preferences.getString(USER_SYNDICATE, "")
        set(value) = preferences.edit().putString(USER_SYNDICATE, value).apply()

    var userSection
        get() = preferences.getString(USER_SECTION, "")
        set(value) = preferences.edit().putString(USER_SECTION, value).apply()

    var userSyndicateID
        get() = preferences.getInt(USER_SYNDICATE_ID, -1)
        set(value) = preferences.edit().putInt(USER_SYNDICATE_ID, value).apply()

    var userSectionID
        get() = preferences.getInt(USER_SECTION_ID, -1)
        set(value) = preferences.edit().putInt(USER_SECTION_ID, value).apply()

    fun isSyndicateChosen(): Boolean {
        return mainSyndicate != 0
    }

    fun clearAll():Unit{
        return preferences.edit().clear().apply()
    }
}