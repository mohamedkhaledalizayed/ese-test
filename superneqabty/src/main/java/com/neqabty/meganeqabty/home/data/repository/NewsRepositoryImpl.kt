package com.neqabty.meganeqabty.home.data.repository

import com.neqabty.meganeqabty.home.data.model.News
import com.neqabty.meganeqabty.home.data.source.NewsDS
import com.neqabty.meganeqabty.home.domain.entity.NewsEntity
import com.neqabty.meganeqabty.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsDS: NewsDS) : NewsRepository {
    override fun getNews(): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getNews().map { it.toNewsEntity() })
        }
    }

    override fun getSyndicateNews(syndicateId: String): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getSyndicateNews(syndicateId).map { it.toNewsEntity() })
        }
    }

}

private fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        author.id, author.entityCode, content, createdAt, headline, id, image, source, type, updatedAt, url
    )
}