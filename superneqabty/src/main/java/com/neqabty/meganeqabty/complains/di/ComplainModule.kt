package com.neqabty.meganeqabty.complains.di


import com.neqabty.meganeqabty.complains.data.api.ComplainApi
import com.neqabty.meganeqabty.complains.data.repository.ComplainRepositoryImpl
import com.neqabty.meganeqabty.complains.domain.repository.ComplainRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ComplainModule {
    companion object {
        @Provides
        fun providesComplainApi(
            @Named("ads")
            retrofit: Retrofit
        ) = retrofit.create(ComplainApi::class.java)
    }

    @Binds
    internal abstract fun bindsComplainRepository(complainRepositoryImpl: ComplainRepositoryImpl): ComplainRepository
}