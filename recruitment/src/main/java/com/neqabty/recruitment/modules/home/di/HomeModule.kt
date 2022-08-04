package com.neqabty.recruitment.modules.home.di

import com.neqabty.recruitment.modules.home.data.api.HomeApi
import com.neqabty.recruitment.modules.home.data.repository.HomeRepositoryImpl
import com.neqabty.recruitment.modules.home.domain.repository.HomeRepository
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
            @Named("recruitment") retrofit: Retrofit
        ) = retrofit.create(HomeApi::class.java)
    }
    @Binds
    internal abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}