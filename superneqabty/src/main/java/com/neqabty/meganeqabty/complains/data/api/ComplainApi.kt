package com.neqabty.meganeqabty.complains.data.api


import com.neqabty.meganeqabty.complains.data.model.ComplainsListModel
import retrofit2.http.GET
import retrofit2.http.Header

interface ComplainApi {

    @GET("accounts/get_account_complains")
    suspend fun getAllComplains(@Header("Authorization") token: String): ComplainsListModel

}