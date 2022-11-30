package com.neqabty.healthcare.auth.logout.di

import com.neqabty.healthcare.auth.logout.data.api.LogoutApi
import com.neqabty.healthcare.auth.logout.data.repository.LogoutRepositoryImpl
import com.neqabty.healthcare.auth.logout.domain.repository.LogoutRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class LogoutModule {
    companion object {
        @Provides
        fun providesLogoutApiService(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(LogoutApi::class.java)
    }

    @Binds
    internal abstract fun bindsLogoutRepository(logoutRepositoryImpl: LogoutRepositoryImpl): LogoutRepository
}