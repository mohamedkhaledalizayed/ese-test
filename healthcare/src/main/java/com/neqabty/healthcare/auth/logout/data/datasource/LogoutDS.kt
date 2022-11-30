package com.neqabty.healthcare.auth.logout.data.datasource



import com.neqabty.healthcare.auth.logout.data.api.LogoutApi
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class LogoutDS @Inject constructor(private val logoutApi: LogoutApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun logout(): Boolean {
        return logoutApi.logout(preferencesHelper.token).success
    }

}