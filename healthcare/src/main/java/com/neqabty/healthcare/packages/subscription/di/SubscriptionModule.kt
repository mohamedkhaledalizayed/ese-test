package com.neqabty.healthcare.packages.subscription.di


import com.neqabty.healthcare.packages.subscription.data.api.SubscriptionApi
import com.neqabty.healthcare.packages.subscription.data.repository.SubscriptionRepositoryImpl
import com.neqabty.healthcare.packages.subscription.domain.repository.SubscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SubscriptionModule {
    companion object {
        @Provides
        fun provideSubscriptionApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(SubscriptionApi::class.java)
    }

    @Binds
    internal abstract fun bindsSubscriptionRepository(subscriptionRepositoryImpl: SubscriptionRepositoryImpl): SubscriptionRepository
}