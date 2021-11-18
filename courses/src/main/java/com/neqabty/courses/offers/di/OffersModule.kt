package com.neqabty.courses.offers.di

import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.data.repository.CourseRepositoryImpl
import com.neqabty.courses.home.domain.repository.CoursesRepository
import com.neqabty.courses.offers.data.api.OffersApi
import com.neqabty.courses.offers.data.repository.OffersRepositoryImpl
import com.neqabty.courses.offers.domain.repository.OffersRepository
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
        fun provideOffersApi(
            @Named("course")
            retrofit: Retrofit
        ) = retrofit.create(OffersApi::class.java)
    }

    @Binds
    internal abstract fun bindsOffersRepository(offersRepositoryImpl: OffersRepositoryImpl): OffersRepository
}