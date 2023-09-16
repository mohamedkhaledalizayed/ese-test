package com.neqabty.healthcare.pharmacymart.home.di



import com.neqabty.healthcare.pharmacymart.home.data.api.PharmacyMartRegisterApi
import com.neqabty.healthcare.pharmacymart.home.data.repository.RegisterRepositoryImpl
import com.neqabty.healthcare.pharmacymart.home.domain.repository.RegisterRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PharmacyMartRegisterModule {
    companion object {
        @Provides
        fun providesRegisterApiService(
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(PharmacyMartRegisterApi::class.java)
    }
    @Binds
    internal abstract fun bindsRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository

}