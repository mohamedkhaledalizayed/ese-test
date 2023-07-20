package com.neqabty.healthcare.commen.packages.payment.di

import com.neqabty.healthcare.commen.packages.payment.data.api.PaymentApi
import com.neqabty.healthcare.commen.packages.payment.data.repository.PaymentRepositoryImpl
import com.neqabty.healthcare.commen.packages.payment.domain.repository.PaymentRepository
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
        fun providesPaymentApiService(@Named("mega")retrofit: Retrofit) = retrofit.create(
            PaymentApi::class.java)
    }
    @Binds
    internal abstract fun bindsPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository
}