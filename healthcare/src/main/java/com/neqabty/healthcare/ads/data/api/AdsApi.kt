package com.neqabty.healthcare.ads.data.api


import com.neqabty.healthcare.ads.data.model.AdsResponse
import retrofit2.http.GET
import retrofit2.http.Header


interface AdsApi {

    @GET("api/announcments")
    suspend fun getAllAds(@Header("Authorization") token: String): AdsResponse

}