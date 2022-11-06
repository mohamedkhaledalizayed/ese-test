package com.neqabty.healthcare.commen.ads.di

import com.neqabty.healthcare.commen.ads.data.api.AdsApi
import com.neqabty.healthcare.commen.ads.data.repository.AdsRepositoryImpl
import com.neqabty.healthcare.commen.ads.domain.repository.AdsRepository
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
        fun providesAdsApi(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(AdsApi::class.java)
    }

    @Binds
    internal abstract fun bindsAdsRepository(adsRepositoryImpl: AdsRepositoryImpl): AdsRepository
}