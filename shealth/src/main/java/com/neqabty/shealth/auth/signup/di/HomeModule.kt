package com.neqabty.shealth.auth.signup.di

import com.neqabty.shealth.auth.signup.data.api.SignupApi
import com.neqabty.shealth.auth.signup.data.repository.SignupRepositoryImpl
import com.neqabty.shealth.auth.signup.domain.repository.SignupRepository
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