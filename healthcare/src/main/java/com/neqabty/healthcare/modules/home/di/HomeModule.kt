package com.neqabty.healthcare.modules.home.di

import com.neqabty.healthcare.modules.home.data.api.HomeApi
import com.neqabty.healthcare.modules.home.data.repository.HomeRepositoryImpl
import com.neqabty.healthcare.modules.home.domain.repository.HomeRepository
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
        fun providesHomeApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(HomeApi::class.java)
    }

    @Binds
    internal abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}