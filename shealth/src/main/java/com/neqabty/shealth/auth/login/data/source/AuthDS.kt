package com.neqabty.shealth.auth.login.data.source


import com.neqabty.shealth.auth.login.data.api.AuthApi
import com.neqabty.shealth.auth.login.data.model.LoginBody
import com.neqabty.shealth.auth.login.data.model.UserModel
import javax.inject.Inject

class AuthDS @Inject constructor(private val authApi: AuthApi) {
    suspend fun login(mobile: String, password: String): UserModel {
        return authApi.login(LoginBody(mobile, password))
    }
}