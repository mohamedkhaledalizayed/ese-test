package com.neqabty.healthcare.clinido.di


import com.neqabty.healthcare.clinido.data.api.ClinidoApi
import com.neqabty.healthcare.clinido.data.repository.ClinidoRepositoryImpl
import com.neqabty.healthcare.clinido.domain.repository.ClinidoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ClinidoModule {
    companion object {
        @Provides
        fun providesClinidoApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(ClinidoApi::class.java)
    }

    @Binds
    internal abstract fun bindsClinidoRepository(clinidoRepositoryImpl: ClinidoRepositoryImpl): ClinidoRepository
}