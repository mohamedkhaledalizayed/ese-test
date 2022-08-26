package com.neqabty.meganeqabty.complains.data.api


import retrofit2.http.GET

interface ComplainApi {

    @GET("accounts/get_account_complains")
    suspend fun getAllComplains(): String

}