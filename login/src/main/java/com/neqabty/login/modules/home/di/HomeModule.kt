package com.neqabty.login.modules.home.di

import com.neqabty.login.modules.home.data.api.SyndicateApi
import com.neqabty.login.modules.home.data.repository.SyndicateRepositoryImpl
import com.neqabty.login.modules.home.domain.repository.SyndicateRepository
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
        fun providesSyndicateApiService(
            @Named("login")
            retrofit: Retrofit
        ) = retrofit.create(SyndicateApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(syndicateRepositoryImpl: SyndicateRepositoryImpl): SyndicateRepository
}