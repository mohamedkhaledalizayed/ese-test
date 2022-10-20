package com.neqabty.healthcare.news.domain.interactors

import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun build(): Flow<List<NewsEntity>> {
        return newsRepository.getNews()
    }
}