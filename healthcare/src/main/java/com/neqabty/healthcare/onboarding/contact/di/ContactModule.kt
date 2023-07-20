package com.neqabty.healthcare.onboarding.contact.di


import com.neqabty.healthcare.onboarding.contact.data.api.ContactApi
import com.neqabty.healthcare.onboarding.contact.data.repository.ContactRepositoryImpl
import com.neqabty.healthcare.onboarding.contact.domain.repository.ContactRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ContactModule {
    companion object {
        @Provides
        fun providesContactApiService(@Named("mega")retrofit: Retrofit) = retrofit.create(
            ContactApi::class.java)
    }
    @Binds
    internal abstract fun bindsContactRepository(contactRepositoryImpl: ContactRepositoryImpl): ContactRepository
}