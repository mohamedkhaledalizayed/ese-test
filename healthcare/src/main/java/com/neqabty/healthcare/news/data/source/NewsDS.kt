package com.neqabty.healthcare.news.data.source


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.news.data.api.NewsApi
import com.neqabty.healthcare.news.data.model.NewsItem
import com.neqabty.healthcare.news.data.model.newslist.New

import javax.inject.Inject

class NewsDS @Inject constructor(private val newsApi: NewsApi, private val preferencesHelper: PreferencesHelper) {
    suspend fun getNews(code: String): List<New> {
        return newsApi.getNews(token = preferencesHelper.token, code).results.news
    }

    suspend fun getNewsDetails(newsId: Int): New {
        return newsApi.getNewsDetails(token = preferencesHelper.token, newsId).results.news.first()
    }
}