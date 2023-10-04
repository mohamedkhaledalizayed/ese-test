package com.neqabty.healthcare.ads.data.api


import com.neqabty.healthcare.ads.data.model.AdsResponse
import retrofit2.http.GET


interface AdsApi {

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

}