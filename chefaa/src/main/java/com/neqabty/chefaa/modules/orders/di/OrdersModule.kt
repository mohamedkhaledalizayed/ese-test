package com.neqabty.chefaa.modules.orders.di

import com.neqabty.chefaa.modules.orders.data.api.OrdersApi
import com.neqabty.chefaa.modules.orders.data.repository.OrderRepositoryImpl
import com.neqabty.chefaa.modules.orders.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class OrdersModule {
    companion object {
        @Provides
        fun providesOrdersApiService(
            @Named("chefaa") retrofit: Retrofit
        ) = retrofit.create(OrdersApi::class.java)
    }

    @Binds
    internal abstract fun bindsOrdersRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository


}