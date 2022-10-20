package com.neqabty.healthcare.auth.login.data.source


import com.neqabty.healthcare.auth.login.data.api.AuthApi
import com.neqabty.healthcare.auth.login.data.model.LoginBody
import com.neqabty.healthcare.auth.login.data.model.UserModel
import javax.inject.Inject

class AuthDS @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(mobile: String, password: String): UserModel {
        return authApi.login(LoginBody(mobile, password))
    }
}