package com.neqabty.recruitment.modules.news.domain.repository



import com.neqabty.recruitment.modules.news.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getNews(): Flow<List<NewsEntity>>
    fun getNewsDetails(id: Int): Flow<NewsEntity>
}