package com.neqabty.healthcare.news.data.api

import com.neqabty.healthcare.news.data.model.NewsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(@Query("filter{author.entity_code}") syndicateId: String): NewsModel

    @GET("news")
    suspend fun getNewsDetails(@Query("filter{id}") newsId: Int): NewsModel

}