package com.neqabty.recruitment.modules.news.data.api


import com.neqabty.recruitment.modules.news.data.model.NewsList
import com.neqabty.recruitment.modules.news.data.model.newsdetails.NewsDetailsModel
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApi {

    @GET("news")
    suspend fun getNews(): NewsList

    @GET("news/{id}")
    suspend fun getNewsDetails(@Path("id") id: Int): NewsDetailsModel

}