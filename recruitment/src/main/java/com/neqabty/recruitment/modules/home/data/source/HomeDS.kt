package com.neqabty.recruitment.modules.home.data.source

import com.neqabty.recruitment.modules.home.data.api.HomeApi
import com.neqabty.recruitment.modules.home.data.model.ads.AdModel
import com.neqabty.recruitment.modules.home.data.model.news.NewsList
import com.neqabty.recruitment.modules.home.data.model.news.NewsModel
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi) {

    suspend fun getAds(): List<AdModel>{
        return homeApi.getAds().ads
    }

    suspend fun getNews(): List<NewsModel>{
        return homeApi.getNews().news
    }

}