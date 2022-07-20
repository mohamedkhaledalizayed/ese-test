package com.neqabty.healthcare.modules.verifyphone.di

import com.neqabty.healthcare.modules.verifyphone.data.api.VerifyPhoneApi
import com.neqabty.healthcare.modules.verifyphone.data.repository.VerifyPhoneRepositoryImpl
import com.neqabty.healthcare.modules.verifyphone.domain.repository.VerifyPhoneRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class VerifyPhoneModule {
    companion object {
        @Provides
        fun providesVerifyPhoneApi(@Named("syndicate")retrofit: Retrofit) = retrofit.create(VerifyPhoneApi::class.java)
    }
    @Binds
    internal abstract fun bindsVerifyPhoneRepository(verifyPhoneRepositoryImpl: VerifyPhoneRepositoryImpl): VerifyPhoneRepository
}