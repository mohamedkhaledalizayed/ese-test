package com.neqabty.healthcare.news.data.source


import com.neqabty.healthcare.news.data.api.NewsApi
import com.neqabty.healthcare.news.data.model.NewsItem

import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi) {
    suspend fun getNews(code: String): List<NewsItem> {
        return newsApi.getNews(code).news
    }

    suspend fun getNewsDetails(newsId: Int): NewsItem {
        return newsApi.getNewsDetails(newsId).news.first()
    }
}