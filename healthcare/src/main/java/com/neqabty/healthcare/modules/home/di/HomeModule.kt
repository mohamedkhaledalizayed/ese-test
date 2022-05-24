package com.neqabty.healthcare.modules.home.di

import com.neqabty.healthcare.modules.home.data.api.HealthCareApi
import com.neqabty.healthcare.modules.home.data.repository.HealthCareRepositoryImpl
import com.neqabty.healthcare.modules.home.domain.repository.HealthCareRepository
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
        fun providesCourseApiService(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(HealthCareApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(healthCareRepositoryImpl: HealthCareRepositoryImpl): HealthCareRepository
}