package com.neqabty.healthcare.commen.mypackages.addfollower.di

import com.neqabty.healthcare.commen.mypackages.addfollower.data.api.AddFollowerApi
import com.neqabty.healthcare.commen.mypackages.addfollower.data.repository.AddFollowerRepositoryImpl
import com.neqabty.healthcare.commen.mypackages.addfollower.domain.repository.AddFollowerRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AddFollowersModule {
    companion object {
        @Provides
        fun provideAddFollowerApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(AddFollowerApi::class.java)
    }

    @Binds
    internal abstract fun bindsAddFollowerRepository(addFollowerRepositoryImpl: AddFollowerRepositoryImpl): AddFollowerRepository
}