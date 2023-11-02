package com.neqabty.healthcare.packages.packageslist.domain.repository

import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntityList
import kotlinx.coroutines.flow.Flow

interface PackagesRepository {
    fun getPackages(code: String): Flow<List<PackagesEntity>>
    fun getInsuranceDocs(packageId: String): Flow<InsuranceEntityList>
}