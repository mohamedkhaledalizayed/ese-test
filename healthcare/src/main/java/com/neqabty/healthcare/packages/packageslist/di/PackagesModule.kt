package com.neqabty.healthcare.packages.packageslist.di


import com.neqabty.healthcare.packages.packageslist.data.api.PackagesApi
import com.neqabty.healthcare.packages.packageslist.data.repository.PackagesRepositoryImpl
import com.neqabty.healthcare.packages.packageslist.domain.repository.PackagesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PackagesModule {
    companion object {
        @Provides
        fun providesSearchApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(PackagesApi::class.java)
    }

    @Binds
    internal abstract fun bindsPackagesRepository(packagesRepositoryImpl: PackagesRepositoryImpl): PackagesRepository
}