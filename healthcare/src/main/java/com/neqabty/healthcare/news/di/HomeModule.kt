package com.neqabty.healthcare.news.di


import com.neqabty.healthcare.news.data.api.NewsApi
import com.neqabty.healthcare.news.data.repository.NewsRepositoryImpl
import com.neqabty.healthcare.news.domain.repository.NewsRepository
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
        fun providesNewsApiService(@Named("healthcare")retrofit: Retrofit) = retrofit.create(NewsApi::class.java)
    }
    @Binds
    internal abstract fun bindsCoursesRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}