package com.neqabty.shealth.news.di


import com.neqabty.shealth.news.data.api.NewsApi
import com.neqabty.shealth.news.data.repository.NewsRepositoryImpl
import com.neqabty.shealth.news.domain.repository.NewsRepository
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