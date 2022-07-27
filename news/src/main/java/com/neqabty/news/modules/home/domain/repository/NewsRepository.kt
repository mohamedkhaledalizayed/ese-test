package com.neqabty.news.modules.home.domain.repository

import com.neqabty.news.modules.home.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsEntity>>
    fun getSyndicateNews(syndicateId:String): Flow<List<NewsEntity>>
    fun getNewsDetails(newsId:Int): Flow<NewsEntity>
}