package com.neqabty.healthcare.mega.home.data.repository



import com.neqabty.healthcare.mega.home.data.source.HomeDS
import com.neqabty.healthcare.mega.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS): HomeRepository {

    override fun addComplain(mobile: String, email: String, message: String): Flow<String> {
        return flow {
            emit(homeDS.addComplain(mobile, email, message))
        }
    }

}

