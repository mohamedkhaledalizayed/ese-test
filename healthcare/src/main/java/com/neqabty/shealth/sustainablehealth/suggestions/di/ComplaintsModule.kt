package com.neqabty.shealth.sustainablehealth.suggestions.di



import com.neqabty.shealth.sustainablehealth.suggestions.data.api.ComplaintsApi
import com.neqabty.shealth.sustainablehealth.suggestions.data.repository.ComplaintRepositoryImpl
import com.neqabty.shealth.sustainablehealth.suggestions.domain.repository.ComplaintRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class ComplaintsModule {
    companion object {
        @Provides
        fun provideComplaintsApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(ComplaintsApi::class.java)
    }

    @Binds
    internal abstract fun bindsComplaintRepository(complaintRepositoryImpl: ComplaintRepositoryImpl): ComplaintRepository
}