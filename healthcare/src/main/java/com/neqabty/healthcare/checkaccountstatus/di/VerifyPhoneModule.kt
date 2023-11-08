package com.neqabty.healthcare.checkaccountstatus.di

import com.neqabty.healthcare.checkaccountstatus.data.api.CheckAccountApi
import com.neqabty.healthcare.checkaccountstatus.data.repository.CheckAccountRepositoryImpl
import com.neqabty.healthcare.checkaccountstatus.domain.repository.CheckAccountRepository
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
        fun providesCheckAccountApi(@Named("healthcare")retrofit: Retrofit) = retrofit.create(CheckAccountApi::class.java)
    }
    @Binds
    internal abstract fun bindsCheckAccountRepository(verifyPhoneRepositoryImpl: CheckAccountRepositoryImpl): CheckAccountRepository
}