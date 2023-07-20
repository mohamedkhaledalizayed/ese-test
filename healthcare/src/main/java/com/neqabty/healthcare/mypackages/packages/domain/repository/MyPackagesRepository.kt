package com.neqabty.healthcare.mypackages.packages.domain.repository


import com.neqabty.healthcare.mypackages.packages.domain.entity.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface MyPackagesRepository {
    fun getMyPackages(phone: String): Flow<ProfileEntity>
}