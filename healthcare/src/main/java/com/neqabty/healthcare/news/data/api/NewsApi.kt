package com.neqabty.healthcare.news.data.api

import com.neqabty.healthcare.news.data.model.newslist.NewsListModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApi {
    @GET("api/news")
    suspend fun getNews(@Header("Authorization") token: String, @Query("filter{author.code}") syndicateId: String): NewsListModel

    @GET("api/news")
    suspend fun getNewsDetails(@Header("Authorization") token: String, @Query("filter{id}") newsId: Int): NewsListModel

}