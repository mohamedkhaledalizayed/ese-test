package com.neqabty.healthcare.chefaa.verifyuser.di



import com.neqabty.healthcare.chefaa.verifyuser.data.api.VerifyApi
import com.neqabty.healthcare.chefaa.verifyuser.data.repository.VerificationRepositoryImpl
import com.neqabty.healthcare.chefaa.verifyuser.domain.repository.VerificationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class VerificationModule {
    companion object {
        @Provides
        fun providesVerifyApiService(
            @Named("chefaa") retrofit: Retrofit
        ) = retrofit.create(VerifyApi::class.java)
    }

    @Binds
    internal abstract fun bindsVerificationRepository(verificationRepositoryImpl: VerificationRepositoryImpl): VerificationRepository

}