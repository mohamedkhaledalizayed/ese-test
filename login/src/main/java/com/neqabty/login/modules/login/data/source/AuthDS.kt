package com.neqabty.login.modules.login.data.source

import com.neqabty.login.modules.login.data.api.AuthApi
import com.neqabty.login.modules.login.data.model.LoginBody
import com.neqabty.login.modules.login.data.model.LoginResponse
import javax.inject.Inject

class AuthDS @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(mobile: String, password: String): LoginResponse {
        return authApi.login(LoginBody(mobile, password))
    }
}