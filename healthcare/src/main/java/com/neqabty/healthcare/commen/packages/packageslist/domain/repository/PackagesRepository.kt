package com.neqabty.healthcare.commen.packages.packageslist.domain.repository

import com.neqabty.healthcare.commen.packages.packageslist.domain.entity.PackagesEntity
import kotlinx.coroutines.flow.Flow

interface PackagesRepository {
    fun getPackages(code: String): Flow<List<PackagesEntity>>
}