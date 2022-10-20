package com.neqabty.healthcare.mega.payment.di

import com.neqabty.healthcare.mega.payment.data.api.PaymentApi
import com.neqabty.healthcare.mega.payment.data.repository.PaymentRepositoryImpl
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PaymentModule {
    companion object {
        @Provides
        fun providesPaymentApiService(@Named("mega")retrofit: Retrofit) = retrofit.create(PaymentApi::class.java)
    }
    @Binds
    internal abstract fun bindsPaymentRepository(paymentRepositoryImpl: PaymentRepositoryImpl): PaymentRepository
}