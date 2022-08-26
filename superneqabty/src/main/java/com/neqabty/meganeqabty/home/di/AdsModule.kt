package com.neqabty.meganeqabty.home.di




import com.neqabty.meganeqabty.home.data.api.HomeApi
import com.neqabty.meganeqabty.home.data.repository.HomeRepositoryImpl
import com.neqabty.meganeqabty.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class AdsModule {
    companion object {
        @Provides
        fun providesHomeApiService(
            @Named("ads")
            retrofit: Retrofit
        ) = retrofit.create(HomeApi::class.java)
    }

    @Binds
    internal abstract fun bindsHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}