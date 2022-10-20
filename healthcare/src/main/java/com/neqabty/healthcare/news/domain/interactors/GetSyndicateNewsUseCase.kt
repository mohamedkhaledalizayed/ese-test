package com.neqabty.healthcare.news.domain.interactors

import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun build(syndicateId: String): Flow<List<NewsEntity>> {
        return newsRepository.getSyndicateNews(syndicateId)
    }
}