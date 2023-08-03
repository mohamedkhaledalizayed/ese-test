package com.neqabty.healthcare.retirement.di



import com.neqabty.healthcare.retirement.data.api.RetirementApi
import com.neqabty.healthcare.retirement.data.repository.RetirementRepositoryImpl
import com.neqabty.healthcare.retirement.domain.repository.RetirementRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PensionModule {
    companion object {
        @Provides
        fun providesRetirementApi(@Named("retirement")retrofit: Retrofit) = retrofit.create(RetirementApi::class.java)
    }
    @Binds
    internal abstract fun bindsRetirementRepository(retirementRepositoryImpl: RetirementRepositoryImpl): RetirementRepository
}