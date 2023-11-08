package com.neqabty.healthcare.chefaa.address.di

import com.neqabty.healthcare.chefaa.address.data.api.AddressApi
import com.neqabty.healthcare.chefaa.address.data.repository.AddressRepositoryImpl
import com.neqabty.healthcare.chefaa.address.domain.repository.AddressRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddressModule {
    companion object {
        @Provides
        fun providesAddressApiService(
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(AddressApi::class.java)
    }
    @Binds
    internal abstract fun bindsAddressRepository(addressRepositoryImpl: AddressRepositoryImpl): AddressRepository
}
