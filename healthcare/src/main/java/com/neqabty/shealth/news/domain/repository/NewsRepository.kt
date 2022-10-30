package com.neqabty.shealth.news.domain.repository

import com.neqabty.shealth.news.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsEntity>>
    fun getSyndicateNews(syndicateId:String): Flow<List<NewsEntity>>
    fun getNewsDetails(newsId:Int): Flow<NewsEntity>
}