package com.neqabty.valify.modules.home.di

import com.neqabty.valify.modules.home.data.api.ValifyApi
import com.neqabty.valify.modules.home.data.repository.ValifyRepositoryImpl
import com.neqabty.valify.modules.home.domain.repository.ValifyRepository
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
        fun providesValifyApiService(
            @Named("valify") retrofit: Retrofit
        ) = retrofit.create(ValifyApi::class.java)
    }

    @Binds
    internal abstract fun bindsValifyRepository(valifyRepositoryImpl: ValifyRepositoryImpl): ValifyRepository
}