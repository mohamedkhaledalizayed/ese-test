package com.neqabty.healthcare.auth.otp.di



import com.neqabty.healthcare.auth.otp.data.api.VerifyPhoneApi
import com.neqabty.healthcare.auth.otp.data.repository.VerifyPhoneRepositoryImpl
import com.neqabty.healthcare.auth.otp.domain.repository.VerifyPhoneRepository
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
        fun providesVerifyPhoneApi(@Named("otp")retrofit: Retrofit) = retrofit.create(
            VerifyPhoneApi::class.java)
    }
    @Binds
    internal abstract fun bindsVerifyPhoneRepository(verifyPhoneRepositoryImpl: VerifyPhoneRepositoryImpl): VerifyPhoneRepository
}