package com.neqabty.trips.modules.destinations.di

import com.neqabty.trips.modules.destinations.data.api.DestinationApi
import com.neqabty.trips.modules.destinations.data.repository.DestinationRepositoryImpl
import com.neqabty.trips.modules.destinations.domain.repository.DestinationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class DestinationModule {
    companion object {
        @Provides
        fun provideDestinationApi(@Named("trips") retrofit: Retrofit) =
            retrofit.create(DestinationApi::class.java)
    }

    @Binds
    internal abstract fun bindsDestinationRepository(destinationRepositoryImpl: DestinationRepositoryImpl): DestinationRepository
}