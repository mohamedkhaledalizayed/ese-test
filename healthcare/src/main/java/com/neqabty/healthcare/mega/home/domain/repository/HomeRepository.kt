package com.neqabty.healthcare.mega.home.domain.repository

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun addComplain(mobile: String, email: String, message: String): Flow<String>
}