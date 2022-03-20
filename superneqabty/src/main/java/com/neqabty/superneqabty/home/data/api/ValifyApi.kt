package com.neqabty.superneqabty.home.data.api

import com.neqabty.superneqabty.home.data.model.VerifyUserBody
import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.data.model.verifyuser.VerifyUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ValifyApi {

    @GET("valify/token")
    suspend fun getToken(): GetToken

    @POST("valify/verify-user")
    suspend fun verifyUser(@Body verifyUserBody: VerifyUserBody): VerifyUserResponse

}