package com.neqabty.recruitment.modules.news.domain.usecase



import com.neqabty.recruitment.modules.news.domain.entity.NewsEntity
import com.neqabty.recruitment.modules.news.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    fun build(): Flow<List<NewsEntity>> {
        return newsRepository.getNews()
    }

    fun build(id: Int): Flow<NewsEntity> {
        return newsRepository.getNewsDetails(id)
    }

}