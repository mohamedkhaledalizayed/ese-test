package com.neqabty.valify.modules.home.data.api

import com.neqabty.valify.modules.home.data.model.GetToken
import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ValifyApi {

    @GET("valify/token")
    suspend fun getToken(): GetToken

    @POST("valify/verify-user")
    suspend fun verifyUser(@Body verifyUserBody: VerifyUserBody): VerifyUserResponse
}