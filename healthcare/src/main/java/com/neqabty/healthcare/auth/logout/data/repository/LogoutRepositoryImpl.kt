package com.neqabty.healthcare.auth.logout.data.repository


import com.neqabty.healthcare.auth.logout.data.datasource.LogoutDS
import com.neqabty.healthcare.auth.logout.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(private val logoutDS: LogoutDS) : LogoutRepository {

    override fun logout(): Flow<String> {
        return flow { emit(logoutDS.logout()) }
    }

}
