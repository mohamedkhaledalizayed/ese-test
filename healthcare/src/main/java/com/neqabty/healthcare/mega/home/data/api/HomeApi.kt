package com.neqabty.healthcare.mega.home.data.api


import com.neqabty.healthcare.mega.home.data.model.ComplainBody
import com.neqabty.healthcare.mega.home.data.model.ads.AdsResponse
import com.neqabty.healthcare.mega.home.data.model.complains.ComplainsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeApi {

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

    @POST("complains")
    suspend fun addComplain(@Body complainBody: ComplainBody): ComplainsModel

    @POST("complains")
    suspend fun addComplainAuth(@Header("Authorization") token: String, @Body complainBody: ComplainBody): ComplainsModel

}