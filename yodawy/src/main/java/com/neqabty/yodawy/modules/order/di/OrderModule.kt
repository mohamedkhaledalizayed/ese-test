package com.neqabty.yodawy.modules.order.di

import com.neqabty.yodawy.modules.order.data.api.OrderApi
import com.neqabty.yodawy.modules.order.data.repository.OrderRepositoryImpl
import com.neqabty.yodawy.modules.order.domain.repository.OrderRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class OrderModule {
    companion object{
        @Provides
        fun provideOrderApiService(
            @Named("yodawy") retrofit: Retrofit
        ) = retrofit.create(OrderApi::class.java)
    }
    @Binds
    internal abstract fun bindsOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

}