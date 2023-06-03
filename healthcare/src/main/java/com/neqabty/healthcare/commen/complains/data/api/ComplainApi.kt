package com.neqabty.healthcare.commen.complains.data.api


import com.neqabty.healthcare.commen.complains.data.model.getcomplains.ComplainsListModel
import com.neqabty.healthcare.commen.complains.data.model.ComplainBody
import com.neqabty.healthcare.commen.complains.data.model.addcomplains.ComplainsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ComplainApi {

    @GET("accounts/get_account_complains")
    suspend fun getAllComplains(@Header("Authorization") token: String): ComplainsListModel

    @POST("complains")
    suspend fun addComplain(@Body complainBody: ComplainBody): ComplainsModel

    @POST("complains")
    suspend fun addComplainAuth(@Header("Authorization") token: String, @Body complainBody: ComplainBody): ComplainsModel

}