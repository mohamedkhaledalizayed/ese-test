package com.neqabty.healthcare.modules.offers.di



import com.neqabty.healthcare.modules.offers.data.api.OffersApi
import com.neqabty.healthcare.modules.offers.data.repository.OffersRepositoryImpl
import com.neqabty.healthcare.modules.offers.domain.repository.OffersRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class OffersModule {
    companion object {
        @Provides
        fun providesOffersApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(OffersApi::class.java)
    }

    @Binds
    internal abstract fun bindsOffersRepository(offersRepositoryImpl: OffersRepositoryImpl): OffersRepository
}