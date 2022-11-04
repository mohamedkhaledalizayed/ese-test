package com.neqabty.healthcare.mega.home.di

import com.neqabty.healthcare.mega.home.data.api.HomeApi
import com.neqabty.healthcare.mega.home.data.repository.HomeRepositoryImpl
import com.neqabty.healthcare.mega.home.domain.repository.HomeRepository
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
        fun providesHomeApiService(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(HomeApi::class.java)
    }

    @Binds
    internal abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}