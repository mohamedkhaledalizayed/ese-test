package com.neqabty.yodawy.modules.products.di

import com.neqabty.yodawy.modules.address.data.api.UserApi
import com.neqabty.yodawy.modules.address.data.repository.CourseRepositoryImpl
import com.neqabty.yodawy.modules.address.domain.repository.CoursesRepository
import com.neqabty.yodawy.modules.products.data.api.ProductApi
import com.neqabty.yodawy.modules.products.data.repository.ProductRepositoryImpl
import com.neqabty.yodawy.modules.products.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductModule {
    companion object{
        @Provides
        fun provideProductApiService(
            @Named("yodawy") retrofit: Retrofit) = retrofit.create(ProductApi::class.java)
    }
    @Binds
    internal abstract fun bindsProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}