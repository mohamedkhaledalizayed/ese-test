package com.neqabty.recruitment.modules.home.domain.usecase

import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import com.neqabty.recruitment.modules.home.domain.entity.news.NewsEntity
import com.neqabty.recruitment.modules.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    fun build(): Flow<List<AdEntity>> {
        return homeRepository.getAds()
    }

    fun getNews(): Flow<List<NewsEntity>> {
        return homeRepository.getNews()
    }

}