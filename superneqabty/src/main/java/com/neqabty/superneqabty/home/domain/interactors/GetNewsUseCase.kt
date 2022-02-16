package com.neqabty.superneqabty.home.domain.interactors

import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import com.neqabty.superneqabty.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun build(): Flow<List<NewsEntity>> {
        return newsRepository.getNews()
    }
}