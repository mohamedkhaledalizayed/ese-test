package com.neqabty.superneqabty.home.data.api

import com.neqabty.superneqabty.home.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("api/news")
    suspend fun getNews(): NewsResponse

    @GET("api/news")
    suspend fun getSyndicateNews(@Query("filter{author.entity_code}") syndicateId: String): NewsResponse

    @GET("api/news/{newsId}")
    suspend fun getNewsDetails(@Path("newsId") newsId: Int): NewsResponse

}