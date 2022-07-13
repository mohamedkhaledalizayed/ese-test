package com.neqabty.meganeqabty.home.data.api


import com.neqabty.meganeqabty.home.data.model.AdsResponse
import retrofit2.http.GET

interface AdsApi {
    @GET("api/announcments")
    suspend fun getAllAds(): AdsResponse
}