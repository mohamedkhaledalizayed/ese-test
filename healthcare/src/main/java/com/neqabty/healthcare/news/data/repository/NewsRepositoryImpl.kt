package com.neqabty.healthcare.news.data.repository


import com.neqabty.healthcare.news.data.model.NewsItem
import com.neqabty.healthcare.news.data.model.NewsModel
import com.neqabty.healthcare.news.data.model.newslist.New
import com.neqabty.healthcare.news.data.source.NewsDS
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsDS: NewsDS) : NewsRepository {
    override fun getNews(code: String): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getNews(code).map { it.toNewsEntity() })
        }
    }

    override fun getNewsDetails(newsId: Int): Flow<NewsEntity> {
        return flow {
            emit(newsDS.getNewsDetails(newsId).toNewsEntity())
        }
    }
}

private fun New.toNewsEntity(): NewsEntity {
    return NewsEntity(
        authorId = author.id,
        authorCode = author.code,
        content = content ?: "",
        createdAt = "",
        headline = headline ?: "",
        id = id,
        image = image,
        source = source ?: "",
        type = type ?: "",
        updatedAt = "",
        url = url ?: ""
    )
}
