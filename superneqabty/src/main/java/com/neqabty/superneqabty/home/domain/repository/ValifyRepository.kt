package com.neqabty.superneqabty.home.domain.repository

import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface ValifyRepository {
    fun getToken(): Flow<GetToken>
}