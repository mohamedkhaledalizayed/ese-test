package com.neqabty.yodawy.modules.address.di

import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.repository.UserRepositoryImpl
import com.neqabty.yodawy.modules.address.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class UserModule {
    companion object {
        @Provides
        fun providesUserApiService(
            @Named("yodawy") retrofit: Retrofit) = retrofit.create(UserApi::class.java)
    }
    @Binds
    internal abstract fun bindsUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}