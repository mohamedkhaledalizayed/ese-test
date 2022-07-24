package com.neqabty.login.modules.login.data.api

import com.neqabty.login.modules.login.data.model.LoginBody
import com.neqabty.login.modules.login.data.model.UserModel
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth-token")
    suspend fun login(@Body loginBody: LoginBody): UserModel
}
