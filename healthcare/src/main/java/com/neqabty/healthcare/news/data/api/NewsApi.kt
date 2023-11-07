package com.neqabty.healthcare.news.data.api

import com.neqabty.healthcare.news.data.model.NewsModel
import com.neqabty.healthcare.news.data.model.newslist.NewsListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("api/news")
    suspend fun getNews(@Query("filter{author.code}") syndicateId: String): NewsListModel

    @GET("api/news")
    suspend fun getNewsDetails(@Query("filter{id}") newsId: Int): NewsListModel

}