package com.neqabty.healthcare.pharmacymart.orders.di


import com.neqabty.healthcare.pharmacymart.orders.data.api.PharmacyMartOrdersApi
import com.neqabty.healthcare.pharmacymart.orders.data.repository.PharmacyMartOrdersRepositoryImpl
import com.neqabty.healthcare.pharmacymart.orders.domain.repository.PharmacyMartOrdersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PharmacyMartOrdersModule {
    companion object {
        @Provides
        fun providesPharmacyMartOrdersApi(
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(PharmacyMartOrdersApi::class.java)
    }

    @Binds
    internal abstract fun bindsPharmacyMartOrdersRepository(orderRepositoryImpl: PharmacyMartOrdersRepositoryImpl): PharmacyMartOrdersRepository


}