package com.neqabty.recruitment.modules.home.domain.repository

import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun recruitment(): Flow<String>
}