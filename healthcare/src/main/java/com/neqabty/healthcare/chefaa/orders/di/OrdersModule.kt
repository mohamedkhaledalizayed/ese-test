package com.neqabty.healthcare.chefaa.orders.di


import com.neqabty.healthcare.chefaa.orders.data.api.OrdersApi
import com.neqabty.healthcare.chefaa.orders.data.repository.OrderRepositoryImpl
import com.neqabty.healthcare.chefaa.orders.domain.repository.OrderRepository
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
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(OrdersApi::class.java)
    }

    @Binds
    internal abstract fun bindsOrdersRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository


}