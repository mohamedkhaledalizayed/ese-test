package com.neqabty.healthcare.profile.di



import com.neqabty.healthcare.profile.data.api.ProfileApi
import com.neqabty.healthcare.profile.data.repository.ProfileRepositoryImpl
import com.neqabty.healthcare.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProfileModule {
    companion object {
        @Provides
        fun providesProfileApi(@Named("healthcare")retrofit: Retrofit) = retrofit.create(ProfileApi::class.java)
    }
    @Binds
    internal abstract fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}