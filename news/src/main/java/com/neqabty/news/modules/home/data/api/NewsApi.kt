package com.neqabty.news.modules.home.data.api

import com.neqabty.news.modules.home.data.model.NewsResponse
import retrofit2.http.GET

interface NewsApi {
    @GET("api/news")
    suspend fun getNews(): NewsResponse
}