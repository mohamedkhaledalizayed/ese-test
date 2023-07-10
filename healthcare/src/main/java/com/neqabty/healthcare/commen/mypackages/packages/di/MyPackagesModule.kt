package com.neqabty.healthcare.commen.mypackages.packages.di

import com.neqabty.healthcare.commen.mypackages.packages.data.api.MyPackagesApi
import com.neqabty.healthcare.commen.mypackages.packages.data.repository.MyPackagesRepositoryImpl
import com.neqabty.healthcare.commen.mypackages.packages.domain.repository.MyPackagesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class MyPackagesModule {
    companion object {
        @Provides
        fun provideMyPackagesApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(MyPackagesApi::class.java)
    }

    @Binds
    internal abstract fun bindsMyPackagesRepository(myPackagesRepositoryImpl: MyPackagesRepositoryImpl): MyPackagesRepository
}