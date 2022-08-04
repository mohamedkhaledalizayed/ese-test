package com.neqabty.recruitment.modules.home.domain.repository

import com.neqabty.recruitment.modules.home.domain.entity.ads.AdEntity
import com.neqabty.recruitment.modules.home.domain.entity.news.NewsEntity
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAds(): Flow<List<AdEntity>>
    fun getNews(): Flow<List<NewsEntity>>
}