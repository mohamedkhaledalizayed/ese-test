package com.neqabty.shealth.sustainablehealth.home.data.api

import com.neqabty.shealth.sustainablehealth.home.data.model.ads.AdsResponse
import retrofit2.http.GET

interface AdsApi {

    @GET("announcments")
    suspend fun getAllAds(): AdsResponse

}