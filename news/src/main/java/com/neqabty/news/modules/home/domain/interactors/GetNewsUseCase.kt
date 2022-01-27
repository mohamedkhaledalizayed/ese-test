package com.neqabty.news.modules.home.domain.interactors

import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun build(): Flow<List<NewsEntity>> {
        return newsRepository.getNews()
    }
}