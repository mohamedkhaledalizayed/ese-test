package com.neqabty.recruitment.modules.home.data.api

import com.neqabty.recruitment.modules.home.data.model.ads.AdsList
import retrofit2.http.GET

interface HomeApi {

    @GET("announcment")
    suspend fun getAds(): AdsList

}