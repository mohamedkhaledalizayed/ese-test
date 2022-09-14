package com.neqabty.healthcare.modules.payment.di

import com.neqabty.healthcare.modules.payment.data.api.PaymentApi
import com.neqabty.healthcare.modules.payment.data.repository.SehaPaymentRepositoryImpl
import com.neqabty.healthcare.modules.payment.domain.repository.SehaPaymentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SehaPaymentModule {
    companion object {
        @Provides
        fun providesPaymentApiService(@Named("syndicate")retrofit: Retrofit) = retrofit.create(
            PaymentApi::class.java)
    }
    @Binds
    internal abstract fun bindsPaymentRepository(paymentRepositoryImpl: SehaPaymentRepositoryImpl): SehaPaymentRepository
}