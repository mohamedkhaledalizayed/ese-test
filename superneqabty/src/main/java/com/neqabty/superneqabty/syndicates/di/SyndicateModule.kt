package com.neqabty.superneqabty.syndicates.di

import com.neqabty.superneqabty.syndicates.data.api.SyndicateApi
import com.neqabty.superneqabty.syndicates.data.repository.SyndicateRepositoryImpl
import com.neqabty.superneqabty.syndicates.domain.repository.SyndicateRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SyndicateModule {
    companion object {
        @Provides
        fun providesSyndicateApiService(
            @Named("login")
            retrofit: Retrofit
        ) = retrofit.create(SyndicateApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(syndicateRepositoryImpl: SyndicateRepositoryImpl): SyndicateRepository
}