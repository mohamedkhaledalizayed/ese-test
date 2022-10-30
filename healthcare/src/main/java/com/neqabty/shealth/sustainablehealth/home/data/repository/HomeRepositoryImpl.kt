package com.neqabty.shealth.sustainablehealth.home.data.repository



import com.neqabty.shealth.sustainablehealth.home.data.source.HomeDS
import com.neqabty.shealth.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.shealth.sustainablehealth.home.domain.mappers.toAboutEntity
import com.neqabty.shealth.sustainablehealth.home.domain.repository.HomeRepository
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

