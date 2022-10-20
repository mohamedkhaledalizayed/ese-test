package com.neqabty.chefaa.modules.home.domain.repository

import kotlinx.coroutines.flow.Flow


interface RegisterRepository {
    fun registerUser(phoneNumber: String, userId: String, countryCode: String): Flow<Boolean>
}