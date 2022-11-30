package com.neqabty.healthcare.auth.logout.domain.repository



import kotlinx.coroutines.flow.Flow


interface LogoutRepository {
    fun logout(): Flow<String>
}