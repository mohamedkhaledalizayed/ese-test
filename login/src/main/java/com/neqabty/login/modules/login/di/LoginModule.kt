package com.neqabty.login.modules.login.di

import com.neqabty.login.modules.login.data.api.AuthApi
import com.neqabty.login.modules.login.data.repository.AuthRepositoryImpl
import com.neqabty.login.modules.login.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class LoginModule {
    companion object {
        @Provides
        fun providesAuthApiService(
            @Named("login")
            retrofit: Retrofit
        ) = retrofit.create(AuthApi::class.java)
    }

    @Binds
    internal abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}