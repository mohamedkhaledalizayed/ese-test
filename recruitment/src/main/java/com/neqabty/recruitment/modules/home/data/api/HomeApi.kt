package com.neqabty.recruitment.modules.home.data.api

import com.neqabty.recruitment.modules.home.data.model.ads.AdsList
import com.neqabty.recruitment.modules.home.data.model.news.NewsList
import retrofit2.http.GET

interface HomeApi {

    @GET("announcment")
    suspend fun getAds(): AdsList

    @GET("news")
    suspend fun getNews(): NewsList

}