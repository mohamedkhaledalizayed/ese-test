package com.neqabty.shealth.news.domain.interactors

import com.neqabty.shealth.news.domain.entity.NewsEntity
import com.neqabty.shealth.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDetailsUseCase @Inject constructor(private val repository: NewsRepository) {
    fun build(newsId: Int): Flow<NewsEntity> {
        return repository.getNewsDetails(newsId)
    }
}