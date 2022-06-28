package com.neqabty.meganeqabty.home.domain.repository

import com.neqabty.meganeqabty.home.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsEntity>>
    fun getSyndicateNews(syndicateId:String): Flow<List<NewsEntity>>
}