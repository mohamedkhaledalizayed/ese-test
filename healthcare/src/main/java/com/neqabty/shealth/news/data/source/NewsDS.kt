package com.neqabty.shealth.news.data.source

import com.neqabty.shealth.core.data.Constants.NEQABTY_CODE
import com.neqabty.shealth.news.data.api.NewsApi
import com.neqabty.shealth.news.data.model.News

import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(): List<News> {
        return newsApi.getNews(NEQABTY_CODE).news
    }

    suspend fun getSyndicateNews(syndicateId: String): List<News> {
        return newsApi.getSyndicateNews(syndicateId = syndicateId).news
    }

    suspend fun getNewsDetails(newsId: Int): News {
        return newsApi.getNewsDetails(newsId).news.first()
    }
}