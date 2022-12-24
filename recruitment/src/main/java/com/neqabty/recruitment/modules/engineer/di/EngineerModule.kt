package com.neqabty.recruitment.modules.engineer.di



import com.neqabty.recruitment.modules.engineer.data.api.EngineerApi
import com.neqabty.recruitment.modules.engineer.data.repository.EngineerRepositoryImpl
import com.neqabty.recruitment.modules.engineer.domain.repository.EngineerRepository
import com.neqabty.recruitment.modules.personalinfo.data.api.PersonalInfo
import com.neqabty.recruitment.modules.personalinfo.data.repository.PersonalInfoRepositoryImpl
import com.neqabty.recruitment.modules.personalinfo.domain.repository.PersonalInfoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class EngineerModule {
    companion object {
        @Provides
        fun providesPersonalInfo(
            @Named("recruitment") retrofit: Retrofit
        ) = retrofit.create(EngineerApi::class.java)
    }
    @Binds
    internal abstract fun bindsEngineerRepository(engineerRepositoryImpl: EngineerRepositoryImpl): EngineerRepository
}