package com.neqabty.news.modules.home.data.source

import com.neqabty.news.modules.home.data.api.NewsApi
import com.neqabty.news.modules.home.data.model.News
import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(): List<News> {
        return newsApi.getNews().news
    }

    suspend fun getSyndicateNews(syndicateId:Int):List<News>{
        return newsApi.getSyndicateNews(syndicateId = syndicateId).news
    }
}