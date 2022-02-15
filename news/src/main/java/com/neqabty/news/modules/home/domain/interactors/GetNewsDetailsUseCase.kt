package com.neqabty.news.modules.home.domain.interactors

import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsDetailsUseCase @Inject constructor(private val repository: NewsRepository) {
    fun build(newsId: Int): Flow<NewsEntity> {
        return repository.getNewsDetails(newsId)
    }
}