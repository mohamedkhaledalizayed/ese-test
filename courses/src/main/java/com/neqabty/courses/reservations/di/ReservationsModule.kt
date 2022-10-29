package com.neqabty.courses.reservations.di



import com.neqabty.courses.reservations.data.api.ReservationsApi
import com.neqabty.courses.reservations.data.repository.ReservationsRepositoryImpl
import com.neqabty.courses.reservations.domain.repository.ReservationsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReservationsModule {
    companion object {
        @Provides
        fun provideOffersApi(
            @Named("course")
            retrofit: Retrofit
        ) = retrofit.create(ReservationsApi::class.java)
    }

    @Binds
    internal abstract fun bindsReservationsRepository(reservationsRepositoryImpl: ReservationsRepositoryImpl): ReservationsRepository
}