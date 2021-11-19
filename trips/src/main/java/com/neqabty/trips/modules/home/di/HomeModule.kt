package com.neqabty.trips.modules.home.di

import com.neqabty.trips.modules.home.data.api.TripsApi
import com.neqabty.trips.modules.home.data.repository.TripsRepositoryImpl
import com.neqabty.trips.modules.home.domain.repository.TripsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    companion object {
        @Provides
        fun provideTripsApi(@Named("trips")retrofit: Retrofit) = retrofit.create(TripsApi::class.java)
    }
    @Binds
    internal abstract fun bindsTripsRepository(tripsRepositoryImpl: TripsRepositoryImpl): TripsRepository
}