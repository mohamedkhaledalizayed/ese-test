package com.neqabty.healthcare.news.data.source


import com.neqabty.healthcare.news.data.api.NewsApi
import com.neqabty.healthcare.news.data.model.NewsItem
import com.neqabty.healthcare.news.data.model.newslist.New

import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(code: String): List<New> {
        return newsApi.getNews(code).results.news
    }

    suspend fun getNewsDetails(newsId: Int): New {
        return newsApi.getNewsDetails(newsId).results.news.first()
    }
}