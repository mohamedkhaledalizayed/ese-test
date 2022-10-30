package com.neqabty.healthcare.sustainablehealth.home.di

import com.neqabty.healthcare.sustainablehealth.home.data.api.AdsApi
import com.neqabty.healthcare.sustainablehealth.home.data.repository.AdsRepositoryImpl
import com.neqabty.healthcare.sustainablehealth.home.domain.repository.AdsRepository
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
        fun providesHomeApi(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(AdsApi::class.java)
    }

    @Binds
    internal abstract fun bindsHomeRepository(adsRepositoryImpl: AdsRepositoryImpl): AdsRepository
}