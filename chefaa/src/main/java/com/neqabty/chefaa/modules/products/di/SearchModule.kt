package com.neqabty.chefaa.modules.products.di

import com.neqabty.chefaa.modules.products.data.api.SearchApi
import com.neqabty.chefaa.modules.products.data.repository.SearchRepositoryImpl
import com.neqabty.chefaa.modules.products.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchModule {

    companion object {
        @Provides
        fun providesSearchApiService(
            @Named("chefaa") retrofit: Retrofit
        ) = retrofit.create(SearchApi::class.java)
    }

    @Binds
    internal abstract fun bindsSearchRepository(producSearchRepositoryImpl: SearchRepositoryImpl):SearchRepository

}
