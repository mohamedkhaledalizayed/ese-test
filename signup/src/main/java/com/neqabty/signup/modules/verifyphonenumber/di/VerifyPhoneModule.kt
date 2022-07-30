package com.neqabty.signup.modules.verifyphonenumber.di


import com.neqabty.signup.modules.verifyphonenumber.data.api.VerifyPhoneApi
import com.neqabty.signup.modules.verifyphonenumber.data.repository.VerifyPhoneRepositoryImpl
import com.neqabty.signup.modules.verifyphonenumber.domain.repository.VerifyPhoneRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class VerifyPhoneModule {
    companion object {
        @Provides
        fun providesVerifyPhoneApi(@Named("syndicate")retrofit: Retrofit) = retrofit.create(
            VerifyPhoneApi::class.java)
    }
    @Binds
    internal abstract fun bindsVerifyPhoneRepository(verifyPhoneRepositoryImpl: VerifyPhoneRepositoryImpl): VerifyPhoneRepository
}