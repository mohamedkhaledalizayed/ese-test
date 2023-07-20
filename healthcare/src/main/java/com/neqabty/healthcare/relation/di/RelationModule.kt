package com.neqabty.healthcare.relation.di


import com.neqabty.healthcare.relation.data.api.RelationApi
import com.neqabty.healthcare.relation.data.repository.RelationRepositoryImpl
import com.neqabty.healthcare.relation.domain.repository.RelationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class RelationModule {
    companion object {
        @Provides
        fun provideRelationApi(
            @Named("healthcare")
            retrofit: Retrofit
        ) = retrofit.create(RelationApi::class.java)
    }

    @Binds
    internal abstract fun bindsRelationRepository(relationRepositoryImpl: RelationRepositoryImpl): RelationRepository
}