package com.neqabty.healthcare.packages.packageslist.domain.usecase


import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntityList
import com.neqabty.healthcare.packages.packageslist.domain.repository.PackagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInsuranceDocsUseCase @Inject constructor(private val packagesRepository: PackagesRepository) {

    fun build(packageId: String): Flow<InsuranceEntityList> {
        return packagesRepository.getInsuranceDocs(packageId)
    }

}