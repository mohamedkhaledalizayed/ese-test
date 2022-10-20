package com.neqabty.chefaa.modules.home.di

import com.neqabty.chefaa.modules.home.data.api.RegisterApi
import com.neqabty.chefaa.modules.home.data.repository.RegisterRepositoryImpl
import com.neqabty.chefaa.modules.home.domain.repository.RegisterRepository
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
        ) = retrofit.create(RegisterApi::class.java)
    }
    @Binds
    internal abstract fun bindsRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

}