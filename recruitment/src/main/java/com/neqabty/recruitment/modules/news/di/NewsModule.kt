package com.neqabty.recruitment.modules.news.di

import com.neqabty.recruitment.modules.news.data.api.NewsApi
import com.neqabty.recruitment.modules.news.data.repository.NewsRepositoryImpl
import com.neqabty.recruitment.modules.news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsModule {
    companion object {
        @Provides
        fun providesNewsApi(
            @Named("recruitment") retrofit: Retrofit
        ) = retrofit.create(NewsApi::class.java)
    }
    @Binds
    internal abstract fun bindsNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}