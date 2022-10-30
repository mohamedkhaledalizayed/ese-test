package com.neqabty.shealth.news.data.api

import com.neqabty.shealth.news.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(@Query("filter{author.entity_code}") syndicateId: String): NewsResponse

    @GET("news")
    suspend fun getSyndicateNews(@Query("filter{author.entity_code}") syndicateId: String): NewsResponse

    @GET("news")
    suspend fun getNewsDetails(@Query("filter{id}") newsId: Int): NewsResponse

}