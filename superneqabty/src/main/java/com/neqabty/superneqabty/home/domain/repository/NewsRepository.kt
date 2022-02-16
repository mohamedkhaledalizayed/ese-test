package com.neqabty.superneqabty.home.domain.repository

import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsEntity>>
    fun getSyndicateNews(syndicateId:Int): Flow<List<NewsEntity>>
    fun getNewsDetails(newsId:Int): Flow<NewsEntity>
}