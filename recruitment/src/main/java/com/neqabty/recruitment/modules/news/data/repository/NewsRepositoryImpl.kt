package com.neqabty.recruitment.modules.news.data.repository





import com.neqabty.recruitment.modules.news.data.model.NewsModel
import com.neqabty.recruitment.modules.news.data.source.NewsDS
import com.neqabty.recruitment.modules.news.domain.entity.NewsEntity
import com.neqabty.recruitment.modules.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(private val newsDS: NewsDS): NewsRepository {

    override fun getNews(): Flow<List<NewsEntity>> {
        return flow {
            emit(newsDS.getNews().map { it.toNewsEntity() })
        }
    }

    override fun getNewsDetails(id: Int): Flow<NewsEntity> {
        return flow {
            emit(newsDS.getNewsDetails(id).toNewsEntity() )
        }
    }
}

private fun NewsModel.toNewsEntity(): NewsEntity{
    return NewsEntity(
        content = content,
        id = id,
        imageFile = imageFile,
        title = title,
        date = createdAt
    )
}