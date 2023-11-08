package com.neqabty.healthcare.auth.login.di


import com.neqabty.healthcare.auth.login.data.api.AuthApi
import com.neqabty.healthcare.auth.login.data.repository.AuthRepositoryImpl
import com.neqabty.healthcare.auth.login.domain.repository.AuthRepository
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
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(AuthApi::class.java)
    }

    @Binds
    internal abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}