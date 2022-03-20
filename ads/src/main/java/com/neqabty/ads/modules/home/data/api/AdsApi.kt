package com.neqabty.ads.modules.home.data.api

import com.neqabty.ads.modules.home.data.model.AdsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AdsApi {
    @GET("api/ads")
    suspend fun getAllAds(): AdsResponse

    @GET("api/ads/")
    suspend fun getSyndicateAd(@Query("filter{entity}") syndicateId: Int): AdsResponse
}