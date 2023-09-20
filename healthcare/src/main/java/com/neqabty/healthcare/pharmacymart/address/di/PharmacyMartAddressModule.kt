package com.neqabty.healthcare.pharmacymart.address.di



import com.neqabty.healthcare.pharmacymart.address.data.api.PharmacyMartAddressApi
import com.neqabty.healthcare.pharmacymart.address.data.repository.PharmacyMartAddressRepositoryImpl
import com.neqabty.healthcare.pharmacymart.address.domain.repository.PharmacyMartAddressRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PharmacyMartAddressModule {
    companion object {
        @Provides
        fun providesPharmacyMartAddressApi(
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(PharmacyMartAddressApi::class.java)
    }
    @Binds
    internal abstract fun bindsPharmacyMartAddressRepository(addressRepositoryImpl: PharmacyMartAddressRepositoryImpl): PharmacyMartAddressRepository
}
