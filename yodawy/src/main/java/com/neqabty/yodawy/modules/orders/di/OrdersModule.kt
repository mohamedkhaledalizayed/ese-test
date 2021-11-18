package com.neqabty.yodawy.modules.address.di

import com.neqabty.yodawy.modules.orders.data.api.OrderApi
import com.neqabty.yodawy.modules.orders.data.repository.OrderRepositoryImpl
import com.neqabty.yodawy.modules.orders.domain.repository.OrderRepository
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
        fun providesCourseApiService(
            @Named("yodawy") retrofit: Retrofit) = retrofit.create(OrderApi::class.java)
    }
    @Binds
    internal abstract fun bindsOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository
}