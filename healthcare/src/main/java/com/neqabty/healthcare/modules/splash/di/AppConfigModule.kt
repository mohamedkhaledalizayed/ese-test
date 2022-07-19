package com.neqabty.healthcare.modules.splash.di

import com.neqabty.healthcare.modules.splash.data.api.AppConfigApi
import com.neqabty.healthcare.modules.splash.data.repository.AppConfigRepositoryImpl
import com.neqabty.healthcare.modules.splash.domain.repository.AppConfigRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppConfigModule {
    companion object {
        @Provides
        fun providesAppConfigApiService(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(AppConfigApi::class.java)
    }

    @Binds
    internal abstract fun bindsAppConfigRepository(appConfigRepositoryImpl: AppConfigRepositoryImpl): AppConfigRepository
}