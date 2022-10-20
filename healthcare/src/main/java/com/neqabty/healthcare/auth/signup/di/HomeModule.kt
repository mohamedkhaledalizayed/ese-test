package com.neqabty.healthcare.auth.signup.di

import com.neqabty.healthcare.auth.signup.data.api.SignupApi
import com.neqabty.healthcare.auth.signup.data.repository.SignupRepositoryImpl
import com.neqabty.healthcare.auth.signup.domain.repository.SignupRepository
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
        fun providesSignupApiService(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(SignupApi::class.java)
    }

    @Binds
    internal abstract fun bindsSignupRepository(signupRepositoryImpl: SignupRepositoryImpl): SignupRepository
}