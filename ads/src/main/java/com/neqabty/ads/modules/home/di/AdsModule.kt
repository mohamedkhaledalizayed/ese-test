package com.neqabty.ads.modules.home.di

import com.neqabty.ads.modules.home.data.api.AdsApi
import com.neqabty.ads.modules.home.data.repository.AdsRepositoryImpl
import com.neqabty.ads.modules.home.domain.repository.AdsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AdsModule {
    companion object {
        @Provides
        fun providesAdsApiService(
            @Named("ads")
            retrofit: Retrofit
        ) = retrofit.create(AdsApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(adsRepositoryImpl: AdsRepositoryImpl): AdsRepository
}