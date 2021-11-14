package com.neqabty.courses.home.di

import com.neqabty.courses.home.data.api.CourseApi
import com.neqabty.courses.home.data.repository.CourseRepositoryImpl
import com.neqabty.courses.home.domain.repository.CoursesRepository
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
            @Named("course")
            retrofit: Retrofit
        ) = retrofit.create(CourseApi::class.java)
    }

    @Binds
    internal abstract fun bindsCoursesRepository(courseRepositoryImpl: CourseRepositoryImpl): CoursesRepository
}