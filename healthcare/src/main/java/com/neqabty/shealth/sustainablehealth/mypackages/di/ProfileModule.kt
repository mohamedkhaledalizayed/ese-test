package com.neqabty.shealth.sustainablehealth.mypackages.di

import com.neqabty.shealth.sustainablehealth.mypackages.data.api.ProfileApi
import com.neqabty.shealth.sustainablehealth.mypackages.data.repository.ProfileRepositoryImpl
import com.neqabty.shealth.sustainablehealth.mypackages.domain.repository.ProfileRepository
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
        fun provideProfileApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(ProfileApi::class.java)
    }

    @Binds
    internal abstract fun bindsProfileRepository(profileRepositoryImpl: ProfileRepositoryImpl): ProfileRepository
}