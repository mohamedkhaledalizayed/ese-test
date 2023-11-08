package com.neqabty.healthcare.auth.forgetpassword.di


import com.neqabty.healthcare.auth.forgetpassword.data.api.ForgetPasswordApi
import com.neqabty.healthcare.auth.forgetpassword.data.repository.ForgetPasswordRepositoryImpl
import com.neqabty.healthcare.auth.forgetpassword.domain.repository.ForgetPasswordRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ForgetPasswordModule {
    companion object {
        @Provides
        fun providesForgetPasswordApiService(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(ForgetPasswordApi::class.java)
    }

    @Binds
    internal abstract fun bindsForgetPasswordRepository(forgetPasswordRepositoryImpl: ForgetPasswordRepositoryImpl): ForgetPasswordRepository
}