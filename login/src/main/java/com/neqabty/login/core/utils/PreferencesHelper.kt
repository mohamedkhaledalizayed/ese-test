package com.neqabty.login.core.utils

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
        private const val TOKEN = "data.source.prefs.TOKEN"
        private const val MOBILE = "data.source.prefs.MOBILE"
        private const val CODE = "data.source.prefs.CODE"
        private const val USER_IMAGE = "data.source.prefs.USER_IMAGE"
        private const val SYNDICATE_IMAGE = "data.source.prefs.IMAGE"
        private const val SYNDICATE_NAME = "data.source.prefs.SYNDICATE_NAME"
        private const val FONT_SIZE = "data.source.prefs.FONT_SIZE"
        private const val NATIONAL_ID = "data.source.prefs.NATIONAL_ID"
        private const val MEMBERSHIP_ID = "data.source.prefs.MEMBERSHIP_ID"
        private const val IS_PHONE_VERIFIED = "data.source.prefs.IS_PHONE_VERIFIED"
        private const val PHONE_VERIFIED = "data.source.prefs.PHONE_VERIFIED"
    }

    var isNotificationsEnabled
        get() = preferences.getBoolean(IS_NOTIFICATIONS_ENABLED, true)
        set(value) = preferences.edit().putBoolean(IS_NOTIFICATIONS_ENABLED, value).apply()

    var mainSyndicate
        get() = preferences.getInt(MAIN_SYNDICATE, -1)
        set(value) = preferences.edit().putInt(MAIN_SYNDICATE, value).apply()

    var token
        get() = preferences.getString(TOKEN, "")!!
        set(value) = preferences.edit().putString(TOKEN, value).apply()

    var mobile
        get() = preferences.getString(MOBILE, "")!!
        set(value) = preferences.edit().putString(MOBILE, value).apply()

    var nationalId
        get() = preferences.getString(NATIONAL_ID, "")!!
        set(value) = preferences.edit().putString(NATIONAL_ID, value).apply()

    var name
        get() = preferences.getString(NAME, "")!!
        set(value) = preferences.edit().putString(NAME, value).apply()

    var code
        get() = preferences.getString(CODE, "")!!
        set(value) = preferences.edit().putString(CODE, value).apply()

    var image
        get() = preferences.getString(SYNDICATE_IMAGE, "")!!
        set(value) = preferences.edit().putString(SYNDICATE_IMAGE, value).apply()

    var userImage
        get() = preferences.getString(USER_IMAGE, "")!!
        set(value) = preferences.edit().putString(USER_IMAGE, value).apply()

    var syndicateName
        get() = preferences.getString(SYNDICATE_NAME, "")!!
        set(value) = preferences.edit().putString(SYNDICATE_NAME, value).apply()

    var fontSize
        get() = preferences.getString(FONT_SIZE, "medium")
        set(value) = preferences.edit().putString(FONT_SIZE, value).apply()

    var isPhoneVerified
        get() = preferences.getBoolean(IS_PHONE_VERIFIED, false)
        set(value) = preferences.edit().putBoolean(IS_PHONE_VERIFIED, value).apply()

    var phoneVerified
        get() = preferences.getString(PHONE_VERIFIED, "")!!
        set(value) = preferences.edit().putString(PHONE_VERIFIED, value).apply()

    fun isSyndicateChosen(): Boolean {
        return mainSyndicate != 0
    }

    fun clearAll():Unit{
        return preferences.edit().clear().apply()
    }
}