package com.neqabty.healthcare.sustainablehealth.home.data.repository


import com.neqabty.healthcare.sustainablehealth.home.data.source.HomeDS
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.mappers.toAboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS) : HomeRepository {
    override fun getAboutList(): Flow<List<AboutEntity>> {
        return flow {
            emit(homeDS.getAboutList().map { it.toAboutEntity() })
        }
    }
}


