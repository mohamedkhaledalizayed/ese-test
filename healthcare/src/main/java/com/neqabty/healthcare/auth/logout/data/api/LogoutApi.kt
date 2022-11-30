package com.neqabty.healthcare.auth.logout.data.api



import retrofit2.http.*

interface LogoutApi {

    @POST("logout")
    suspend fun logout(@Header("Authorization") token: String): String

}