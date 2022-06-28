package com.neqabty.superneqabty.home.data.api


import com.neqabty.superneqabty.home.data.model.AdsResponse
import retrofit2.http.GET

interface AdsApi {
    @GET("api/ads")
    suspend fun getAllAds(): AdsResponse
}