package com.neqabty.trips.modules.home.di

import com.neqabty.trips.modules.home.data.api.CitiesApi
import com.neqabty.trips.modules.home.data.repository.CitiesRepositoryImpl
import com.neqabty.trips.modules.home.domain.repository.CitiesRepository
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
        fun provideTripsApi(@Named("trips")retrofit: Retrofit) = retrofit.create(CitiesApi::class.java)
    }
    @Binds
    internal abstract fun bindsCitiesRepository(citiesRepositoryImpl: CitiesRepositoryImpl): CitiesRepository
}