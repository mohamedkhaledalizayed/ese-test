package com.neqabty.healthcare.news.domain.repository

import com.neqabty.healthcare.news.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(code: String): Flow<List<NewsEntity>>
    fun getNewsDetails(newsId:Int): Flow<NewsEntity>
}