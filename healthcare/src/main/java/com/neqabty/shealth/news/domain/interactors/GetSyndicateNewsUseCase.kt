package com.neqabty.shealth.news.domain.interactors

import com.neqabty.shealth.news.domain.entity.NewsEntity
import com.neqabty.shealth.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSyndicateNewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {
    fun build(syndicateId: String): Flow<List<NewsEntity>> {
        return newsRepository.getSyndicateNews(syndicateId)
    }
}