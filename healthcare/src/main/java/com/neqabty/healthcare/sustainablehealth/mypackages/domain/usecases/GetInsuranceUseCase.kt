package com.neqabty.healthcare.sustainablehealth.mypackages.domain.usecases



import com.neqabty.healthcare.sustainablehealth.mypackages.data.model.InsuranceBody
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.insurance.InsuranceEntity
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInsuranceUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(insuranceBody: InsuranceBody): Flow<InsuranceEntity>{
        return profileRepository.getInsurance(insuranceBody)
    }

}