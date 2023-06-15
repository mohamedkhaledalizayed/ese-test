package com.neqabty.healthcare.commen.syndicateservices.di

import com.neqabty.healthcare.commen.syndicateservices.data.api.SyndicateServicesApi
import com.neqabty.healthcare.commen.syndicateservices.data.repository.SyndicateServicesRepositoryImpl
import com.neqabty.healthcare.commen.syndicateservices.domain.repository.SyndicateServicesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SyndicateServicesModule {
    companion object {
        @Provides
        fun providesSyndicateServicesApi(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(SyndicateServicesApi::class.java)
    }

    @Binds
    internal abstract fun bindsSyndicateServicesRepository(syndicateServicesRepositoryImpl: SyndicateServicesRepositoryImpl): SyndicateServicesRepository
}