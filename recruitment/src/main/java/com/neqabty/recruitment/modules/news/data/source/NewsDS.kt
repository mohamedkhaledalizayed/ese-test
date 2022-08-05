package com.neqabty.recruitment.modules.news.data.source



import com.neqabty.recruitment.modules.news.data.api.NewsApi
import com.neqabty.recruitment.modules.news.data.model.NewsModel
import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi) {

    suspend fun getNews(): List<NewsModel>{
        return newsApi.getNews().news
    }

    suspend fun getNewsDetails(id: Int): NewsModel{
        return newsApi.getNewsDetails(id).news
    }

}