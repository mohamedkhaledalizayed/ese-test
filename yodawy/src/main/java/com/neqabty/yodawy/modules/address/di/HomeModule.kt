package com.neqabty.yodawy.modules.address.di

import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.repository.CourseRepositoryImpl
import com.neqabty.yodawy.modules.address.domain.repository.CoursesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    companion object {
        @Provides
        fun providesCourseApiService(retrofit: Retrofit) = retrofit.create(UserApi::class.java)
    }
    @Binds
    internal abstract fun bindsCoursesRepository(courseRepositoryImpl: CourseRepositoryImpl): CoursesRepository
}