package com.neqabty.healthcare.commen.ads.data.api


import com.neqabty.healthcare.commen.ads.data.model.AdsResponse
import retrofit2.http.GET


interface AdsApi {

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

}