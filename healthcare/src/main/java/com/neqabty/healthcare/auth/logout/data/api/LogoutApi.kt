package com.neqabty.healthcare.auth.logout.data.api



import com.neqabty.healthcare.auth.logout.data.model.LogoutModel
import retrofit2.http.*

interface LogoutApi {

    @POST("api/logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutModel

}