package com.example.courses.modules.home.di

import androidx.lifecycle.ViewModel
import com.example.courses.modules.home.data.api.CourseApi
import com.example.courses.modules.home.data.repository.CourseRepositoryImpl
import com.example.courses.modules.home.data.source.CourseDS
import com.example.courses.modules.home.domain.repository.CoursesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    companion object {
        @Provides
        fun providesCourseApiService(retrofit: Retrofit) = retrofit.create(CourseApi::class.java)
    }
    @Binds
    internal abstract fun bindsCoursesRepository(courseRepositoryImpl: CourseRepositoryImpl): CoursesRepository
}