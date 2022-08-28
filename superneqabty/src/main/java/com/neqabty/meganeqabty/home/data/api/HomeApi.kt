package com.neqabty.meganeqabty.home.data.api


import com.neqabty.meganeqabty.home.data.model.ComplainBody
import com.neqabty.meganeqabty.home.data.model.ads.AdsResponse
import com.neqabty.meganeqabty.home.data.model.complains.ComplainsModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface HomeApi {

    @GET("api/announcments")
    suspend fun getAllAds(): AdsResponse

    @POST("api/complains")
    suspend fun addComplain(@Body complainBody: ComplainBody): ComplainsModel

    @POST("api/complains")
    suspend fun addComplainAuth(@Header("Authorization") token: String, @Body complainBody: ComplainBody): ComplainsModel

}