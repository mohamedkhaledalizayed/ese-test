package com.neqabty.news.modules.home.data.api

import com.neqabty.news.modules.home.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(@Query("filter{author.entity_code}") syndicateId: String): NewsResponse

    @GET("news")
    suspend fun getSyndicateNews(@Query("filter{author.entity_code}") syndicateId: String): NewsResponse

    @GET("news")
    suspend fun getNewsDetails(@Query("filter{id}") newsId: Int): NewsResponse

}