package com.neqabty.healthcare.invoices.di


import com.neqabty.healthcare.invoices.data.api.InvoicesApi
import com.neqabty.healthcare.invoices.data.repository.InvoicesRepositoryImpl
import com.neqabty.healthcare.invoices.domain.repository.InvoicesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class InvoicesModule {
    companion object {
        @Provides
        fun providesInvoicesApi(
            @Named("mega")
            retrofit: Retrofit
        ) = retrofit.create(InvoicesApi::class.java)
    }

    @Binds
    internal abstract fun bindsInvoicesRepository(invoicesRepositoryImpl: InvoicesRepositoryImpl): InvoicesRepository
}