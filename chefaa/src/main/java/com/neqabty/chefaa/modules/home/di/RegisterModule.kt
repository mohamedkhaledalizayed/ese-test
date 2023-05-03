package com.neqabty.chefaa.modules.home.di

import com.neqabty.chefaa.modules.home.data.api.HomeApi
import com.neqabty.chefaa.modules.home.data.repository.HomeRepositoryImpl
import com.neqabty.chefaa.modules.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class RegisterModule {
    companion object {
        @Provides
        fun providesRegisterApiService(
            @Named("chefaa") retrofit: Retrofit
        ) = retrofit.create(HomeApi::class.java)
    }
    @Binds
    internal abstract fun bindsRegisterRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

}