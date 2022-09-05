package com.neqabty.recruitment.modules.personalinfo.di



import com.neqabty.recruitment.modules.personalinfo.data.api.PersonalInfo
import com.neqabty.recruitment.modules.personalinfo.data.repository.PersonalInfoRepositoryImpl
import com.neqabty.recruitment.modules.personalinfo.domain.repository.PersonalInfoRepository
import com.neqabty.recruitment.modules.profile.data.api.ProfileApi
import com.neqabty.recruitment.modules.profile.data.repository.ProfileRepositoryImpl
import com.neqabty.recruitment.modules.profile.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class PersonalInfoModule {
    companion object {
        @Provides
        fun providesPersonalInfo(
            @Named("recruitment") retrofit: Retrofit
        ) = retrofit.create(PersonalInfo::class.java)
    }
    @Binds
    internal abstract fun bindsPersonalInfoRepository(personalInfoRepositoryImpl: PersonalInfoRepositoryImpl): PersonalInfoRepository
}