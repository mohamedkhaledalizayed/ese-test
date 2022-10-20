package com.neqabty.healthcare.commen.syndicates.di

import com.neqabty.healthcare.commen.syndicates.data.api.SyndicateApi
import com.neqabty.healthcare.commen.syndicates.data.repository.SyndicateRepositoryImpl
import com.neqabty.healthcare.commen.syndicates.domain.repository.SyndicateRepository
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
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(SyndicateApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(syndicateRepositoryImpl: SyndicateRepositoryImpl): SyndicateRepository
}