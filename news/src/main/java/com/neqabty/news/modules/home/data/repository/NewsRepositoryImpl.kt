package com.neqabty.news.modules.home.data.repository

import com.neqabty.news.modules.home.data.model.News
import com.neqabty.news.modules.home.data.source.NewsDS
import com.neqabty.news.modules.home.domain.entity.NewsEntity
import com.neqabty.news.modules.home.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsDS: NewsDS) : NewsRepository {
    override fun getNews(): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getNews().map { it.toNewsEntity() })
        }
    }

    override fun getSyndicateNews(syndicateId: Int): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getSyndicateNews(syndicateId).map { it.toNewsEntity() })
        }
    }

    override fun getNewsDetails(newsId: Int): Flow<NewsEntity> {
        return flow {
            emit(newsDS.getNewsDetails(newsId).toNewsEntity())
        }
    }
}

private fun News.toNewsEntity(): NewsEntity {
    return NewsEntity(
        author, content, createdAt, headline, id, image, source, type, updatedAt, url
    )
}
