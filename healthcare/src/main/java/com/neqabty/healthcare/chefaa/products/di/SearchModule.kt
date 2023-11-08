package com.neqabty.healthcare.chefaa.products.di


import com.neqabty.healthcare.chefaa.products.data.api.SearchApi
import com.neqabty.healthcare.chefaa.products.data.repository.SearchRepositoryImpl
import com.neqabty.healthcare.chefaa.products.domain.repository.SearchRepository
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
            @Named("healthcare") retrofit: Retrofit
        ) = retrofit.create(SearchApi::class.java)
    }

    @Binds
    internal abstract fun bindsSearchRepository(producSearchRepositoryImpl: SearchRepositoryImpl):SearchRepository

}
