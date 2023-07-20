package com.neqabty.healthcare.mypackages.subscriptiondetails.di


import com.neqabty.healthcare.mypackages.subscriptiondetails.data.api.SubscriptionDetailsApi
import com.neqabty.healthcare.mypackages.subscriptiondetails.data.repository.SubscriptionDetailsRepositoryImpl
import com.neqabty.healthcare.mypackages.subscriptiondetails.domain.repository.SubscriptionDetailsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SubscriptionDetailsModule {
    companion object {
        @Provides
        fun provideSubscriptionDetailsApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(SubscriptionDetailsApi::class.java)
    }

    @Binds
    internal abstract fun bindsSubscriptionDetailsRepository(subscriptionDetailsRepositoryImpl: SubscriptionDetailsRepositoryImpl): SubscriptionDetailsRepository
}