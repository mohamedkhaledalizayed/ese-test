package com.neqabty.meganeqabty.syndicates.di

import com.neqabty.meganeqabty.syndicates.data.api.SyndicateApi
import com.neqabty.meganeqabty.syndicates.data.repository.SyndicateRepositoryImpl
import com.neqabty.meganeqabty.syndicates.domain.repository.SyndicateRepository
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
            @Named("syndicate")
            retrofit: Retrofit
        ) = retrofit.create(SyndicateApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(syndicateRepositoryImpl: SyndicateRepositoryImpl): SyndicateRepository
}