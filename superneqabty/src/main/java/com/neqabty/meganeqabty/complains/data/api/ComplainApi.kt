package com.neqabty.meganeqabty.complains.data.api


import retrofit2.http.GET
import retrofit2.http.Header

interface ComplainApi {

    @GET("api/accounts/get_account_complains")
    suspend fun getAllComplains(@Header("Authorization") token: String): String

}