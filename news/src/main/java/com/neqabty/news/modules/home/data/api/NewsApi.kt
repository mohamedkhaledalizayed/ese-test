package com.neqabty.news.modules.home.data.api

import com.neqabty.news.modules.home.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("api/news")
    suspend fun getNews(): NewsResponse

    @GET("api/news")
    suspend fun getSyndicateNews(@Query("filter{author}")syndicateId:Int):NewsResponse
}