package com.neqabty.news.modules.home.di

import com.neqabty.news.modules.home.data.api.NewsApi
import com.neqabty.news.modules.home.data.repository.NewsRepositoryImpl
import com.neqabty.news.modules.home.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    companion object {
        @Provides
        fun providesNewsApiService(@Named("newsModule")retrofit: Retrofit) = retrofit.create(NewsApi::class.java)
    }
    @Binds
    internal abstract fun bindsCoursesRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}