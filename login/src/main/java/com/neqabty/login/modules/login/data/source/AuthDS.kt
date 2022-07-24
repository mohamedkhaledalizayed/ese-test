package com.neqabty.login.modules.login.data.source

import com.neqabty.login.modules.login.data.api.AuthApi
import com.neqabty.login.modules.login.data.model.LoginBody
import com.neqabty.login.modules.login.data.model.UserModel
import javax.inject.Inject

class AuthDS @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(mobile: String, password: String): UserModel {
        return authApi.login(LoginBody(mobile, password))
    }
}